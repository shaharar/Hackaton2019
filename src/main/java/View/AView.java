package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class AView {

    Controller controller;
    Stage MainStage;

    public void ChangeScene(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getClassLoader().getResource(fxml).openStream());
            Scene scene = new Scene(root, 1024, 600);
            MainStage.setScene(scene);
            if(fxml.equals("website.fxml")){
             //   scene.getStylesheets().add(getClass().getClassLoader().getResource("Background.css").toExternalForm());
            }else if(fxml.equals("login.fxml")){
                // scene.getStylesheets().add(getClass().getClassLoader().getResource("ViewStyle.css").toExternalForm());
            }
            MainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }
}