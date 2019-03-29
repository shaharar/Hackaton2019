package Controller;

import Model.Model;
import View.AView;
import View.LoginView;
import View.MainWebsiteView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    Model model;
    View.AView view;

    public Controller(MainWebsiteView website) {
        this.model = new Model(this);
        this.view = website;
    }

    public void openwindow(String fxmlfile, Object Parameter) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getClassLoader().getResource(fxmlfile).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage newStage = new Stage();
        Scene scene = new Scene(root, 600, 457);
        newStage.setScene(scene);

        AView NewWindow = fxmlLoader.getController();
       // NewWindow.setStage(newStage);
        NewWindow.setController(this);
        newStage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
        newStage.show();
    }
    public boolean login(String username, String password) {
        return  (model.login(username, password));
    }

    public void signUp(String txt_username, String psw_password, String txt_firstName, String txt_lastName, String txt_email, String txt_phone, String txt_cardNo, String txt_expDate, String txt_cvv, String admin) {
        model.signUp(txt_username,psw_password,txt_firstName,txt_lastName,txt_email,txt_phone,txt_cardNo,txt_expDate,txt_cvv,admin);
    }
}
