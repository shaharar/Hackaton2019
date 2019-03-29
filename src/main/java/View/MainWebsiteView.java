package View;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainWebsiteView extends AView{
    public javafx.scene.control.Button login;
    public javafx.scene.control.Button signup;
    public ComboBox coffeeMenu;
    public CheckBox finish;
    public ComboBox saladMenu;
    public ComboBox sandwichesMenu;
    public ComboBox coldMenu;
    public ComboBox snacksMenu;
    public ComboBox pastriesMenu;
    List<CheckMenuItem> coffeeOptions = new ArrayList<>();
    List<CheckMenuItem> sandwichesOptions = new ArrayList<>();
    List<CheckMenuItem> saladOptions = new ArrayList<>();
    List<CheckMenuItem> coldOptions = new ArrayList<>();
    List<CheckMenuItem> snacksOptions = new ArrayList<>();
    List<CheckMenuItem> pastriesOptions = new ArrayList<>();
    public TextField txt_username_login;
    public TextField psw_password_login;

    public MainWebsiteView() {
     //   chooseCoffee();
    }

    public void chooseCoffee(){
       coffeeOptions = new ArrayList<>();
//        for (String cofee : coffeeType )
//            coffeeOptions.add(new CheckMenuItem(cofee));
        coffeeOptions.add(new CheckMenuItem("Big Cap"));
        coffeeMenu.getItems().addAll(coffeeOptions);
    }

    public void login() {
        if (controller.login(txt_username_login.getText(), psw_password_login.getText())) {
         //   ChangeScene("website.fxml");
        } else {
            showAlert("Incorrect Username/Password. Please try again");
        }
    }

    public void signUp() {
        controller.openwindow("signUp.fxml",null);
    }
//    public void filterCities(){
//        if(finish.isSelected()){
//            for (int i = 0; i < coffeeOptions.size(); i++) {
//                if(coffeeOptions.get(i).isSelected())
//                    conection_layer.cityAfterFilter.add(coffeeOptions.get(i).getText());
//            }
//        }
//    }
}