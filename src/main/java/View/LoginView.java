package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class LoginView extends AView {

    public TextField txt_username_login;
    public TextField psw_password_login;


    public void login() {
        if (controller.login(txt_username_login.getText(), psw_password_login.getText())) {
            ChangeScene("website.fxml");
        } else {
            showAlert("Incorrect Username/Password. Please try again");
        }
    }

    public void signUp() {

    }
}
