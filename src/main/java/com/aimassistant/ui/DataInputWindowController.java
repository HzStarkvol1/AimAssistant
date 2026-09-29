package com.aimassistant.ui;

import com.aimassistant.model.BallisticData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class DataInputWindowController {

    @FXML private TextField tfTargetDistance;
    @FXML private TextField tfWindSpeed;
    @FXML private TextField tfWindDirection;
    @FXML private TextField tfBulletWeight;
    @FXML private TextField tfBC;
    @FXML private TextField tfMuzzleVelocity;
    @FXML private TextField tfZeroingDistance;

    private Consumer<BallisticData> onCalculateListener;

    public void setOnCalculateListener(Consumer<BallisticData> listener) {
        this.onCalculateListener = listener;
    }

    @FXML
    private void onCalculateClicked() {
        try {
            double targetDist = Double.parseDouble(tfTargetDistance.getText());
            double windSpeed = Double.parseDouble(tfWindSpeed.getText());
            double windDir = Double.parseDouble(tfWindDirection.getText());
            double bulletWeight = Double.parseDouble(tfBulletWeight.getText());
            double bc = Double.parseDouble(tfBC.getText());
            double muzzleVel = Double.parseDouble(tfMuzzleVelocity.getText());
            double zeroDist = Double.parseDouble(tfZeroingDistance.getText());

            BallisticData data = new BallisticData(
                    targetDist, windSpeed, windDir, bulletWeight, bc, muzzleVel, zeroDist
            );

            if (onCalculateListener != null) {
                onCalculateListener.accept(data);
            }

            Stage stage = (Stage) tfTargetDistance.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please check your numbers");
            alert.setContentText("All fields must be valid numeric values.");
            alert.showAndWait();
        }
    }
}
