package Model;

import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model {

     Controller controller;

    //constructor
    public Model(Controller controller) {
        this.controller = controller;
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:DB/DataBase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return conn;
    }

/*    public boolean login(String username, String password) {
        if(DBM.ReadProfile(username)){ //if found in db
            String realpass=DBM.getPassword(username);
            if( realpass.equals(password)){
                currentUser = DBM.getFields(username);
                InitID();
                return true;
            }
        }
        return false;
    }*/

}
