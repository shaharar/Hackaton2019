package View;

import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MainWebsiteView extends AView{
    public javafx.scene.control.Button login;
    public javafx.scene.control.Button signup;
    //public javafx.scene.control.ComboBox coffeeMenu;
    public SplitMenuButton coffeeMenu;
    public CheckBox finish;
    public SplitMenuButton saladMenu;
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

//    public MainWebsiteView() {
//        coffeeMenu = new SplitMenuButton();
//        List<String> coffeeItems = new ArrayList<>();
//        coffeeItems.add("Big cappuccino 9 ILS");
//        coffee_options(coffeeItems);
//    }

//    public void coffee_options(List<String> coffee_items){
//        coffeeOptions = new ArrayList<>();
//        for (String city : coffee_items )
//            coffeeOptions.add(new CheckMenuItem(city));
//        coffeeMenu.getItems().addAll(coffeeOptions);
//    }

    public void PickCoffee(){
        if(finish.isSelected()){
            for (int i = 0; i < coffeeOptions.size(); i++) {
               // if(coffeeOptions.get(i).isSelected())
                    //conection_layer.cityAfterFilter.add(citiesOptions.get(i).getText());
            }
        }
    }

    public TextField txt_username_login;
    public TextField psw_password_login;


    public void login() {
        if (controller.login(txt_username_login.getText(), psw_password_login.getText())) {
         //   ChangeScene("website.fxml");
        } else {
            showAlert("Incorrect Username/Password. Please try again");
        }
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