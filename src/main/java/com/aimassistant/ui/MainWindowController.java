package com.aimassistant.ui;

import com.aimassistant.model.BallisticData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.function.Consumer;

public class MainWindowController {

    @FXML
    private Canvas reticleCanvas;

    private static final double PIXELS_PER_MRAD = 50.0;

    private static final int MAX_MRAD = 10; 

    private Consumer<BallisticData> onCalculateListener;

    private Double currentImpactMradX = null;
    private Double currentImpactMradY = null;

    @FXML
    public void initialize() {
        drawGrid();
    }

    public void setOnCalculateListener(Consumer<BallisticData> listener) {
        this.onCalculateListener = listener;
    }

    @FXML
    private void openInputWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DataInputWindow.fxml"));
            Parent root = loader.load();

            DataInputWindowController inputController = loader.getController();

            inputController.setOnCalculateListener(data -> {
                if (onCalculateListener != null) {
                    onCalculateListener.accept(data);
                }
            });

            Stage stage = new Stage();
            stage.setTitle("Input Target Data");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawImpactPoint(double mradX, double mradY) {
        this.currentImpactMradX = mradX;
        this.currentImpactMradY = mradY;

        redraw();
    }
    
    private void redraw() {
        drawGrid();
        if (currentImpactMradX != null && currentImpactMradY != null) {
            drawDot(currentImpactMradX, currentImpactMradY);
        }
    }

    private void drawGrid() {
        GraphicsContext gc = reticleCanvas.getGraphicsContext2D();
        double width = reticleCanvas.getWidth();
        double height = reticleCanvas.getHeight();

        double cx = width / 2.0;
        double cy = height / 2.0;

        gc.clearRect(0, 0, width, height);
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);
        gc.setFont(new Font(10));

        double centerGap = 5.0;
        gc.strokeLine(cx, 0, cx, cy - centerGap);
        gc.strokeLine(cx, cy + centerGap, cx, height);
        gc.strokeLine(0, cy, cx - centerGap, cy);
        gc.strokeLine(cx + centerGap, cy, width, cy);

        for (double mrad = -MAX_MRAD; mrad <= MAX_MRAD; mrad += 0.5) {
            if (mrad == 0.0) continue; // Skip center skip

            double pixelOffset = mrad * PIXELS_PER_MRAD;

            double tickSize = (mrad % 1.0 == 0) ? 10.0 : 5.0; 

            double xPos = cx + pixelOffset;
            gc.strokeLine(xPos, cy - tickSize, xPos, cy + tickSize);

            if (mrad % 1.0 == 0) {
                gc.fillText(String.valueOf(Math.abs((int)mrad)), xPos + 2, cy + 12);
            }

            double yPos = cy + pixelOffset;
            gc.strokeLine(cx - tickSize, yPos, cx + tickSize, yPos);
            
            if (mrad % 1.0 == 0) {
                gc.fillText(String.valueOf(Math.abs((int)mrad)), cx + 2, yPos + 12);
            }
        }
    }

    private void drawDot(double mradX, double mradY) {
        GraphicsContext gc = reticleCanvas.getGraphicsContext2D();
        double width = reticleCanvas.getWidth();
        double height = reticleCanvas.getHeight();
        
        double cx = width / 2.0;
        double cy = height / 2.0;
        

        double pxX = cx + (mradX * PIXELS_PER_MRAD);
        double pxY = cy + (mradY * PIXELS_PER_MRAD);

        gc.setFill(Color.RED);

        double radius = 3.0;
        gc.fillOval(pxX - radius, pxY - radius, radius * 2, radius * 2);
    }
}
