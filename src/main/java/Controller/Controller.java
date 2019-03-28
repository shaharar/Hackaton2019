package Controller;

import Model.Model;
import View.LoginView;

public class Controller {
    Model model;
    View.AView view;

    public Controller(LoginView loginView) {
        this.model = new Model(this);
        this.view = loginView;
    }


    public boolean login(String username, String password) {
        return  (model.login(username, password));
    }
}
