package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;

public class Main extends Application {

    private MainWebsiteView website;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("website.fxml").openStream());
        primaryStage.setTitle("Welcome to CoffeePickUp");
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        website = fxmlLoader.getController();
        website.setMainStage(primaryStage);
        Controller con = new Controller(website);
        AView.setController(con);
        //  scene.getStylesheets().add(getClass().getClassLoader().getResource("ViewStyle.css").toExternalForm());
        primaryStage.show();
       // loginView.btn_Login.requestFocus();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
