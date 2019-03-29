package View;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SignUpView extends AView{

    public TextField txt_username;
    public PasswordField psw_password;
    public TextField txt_firstName;
    public TextField txt_lastName;
    public TextField txt_email;
    public TextField txt_phone;
    public TextField txt_cardNo;
    public TextField txt_expDate;
    public TextField txt_cvv;
    public TextField txt_admin;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void submit() {
        controller.signUp(txt_username.getText(),psw_password.getText(),txt_firstName.getText(),txt_lastName.getText(),txt_email.getText(),txt_phone.getText(),txt_cardNo.getText(),txt_expDate.getText(),txt_cvv.getText(), txt_admin.getText());
//        closeWindow();
        showAlert("Sign up successful");
    }

}
