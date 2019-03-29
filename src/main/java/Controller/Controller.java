package Controller;

import Model.Model;
import View.LoginView;
import View.MainWebsiteView;

public class Controller {
    Model model;
    View.AView view;

//    public Controller(MainWebsiteView website) {
//        this.model = new Model(this);
//        this.view = website;
//    }


    public boolean login(String username, String password) {
        return  (model.login(username, password));
    }
}
