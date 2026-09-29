package com.aimassistant;

import com.aimassistant.model.BallisticData;
import com.aimassistant.ui.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();
        
        MainWindowController controller = loader.getController();

        // ----------------------------------------------------------------------------------
        // BACKEND INJECTION KISMI
        //  ----------------------------------------------------------------------------------
        controller.setOnCalculateListener((BallisticData data) -> {
            System.out.println("Processing data in backend: " + data);
            
            // TODO: koordinat bulma işlemlerini burada yapacağım

            
            //calculatedMradX ve calculatedMradY değerleri dürbündeki koordinatlar. (4. Bölge pozitif kabul edilecek)
            double calculatedMradX = 1;
            double calculatedMradY = -+1;
            
            //Merminin düşeceği kısmı işaretler
            controller.drawImpactPoint(calculatedMradX, calculatedMradY);
        });

        primaryStage.setTitle("Holo Sight MRAD Simulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
