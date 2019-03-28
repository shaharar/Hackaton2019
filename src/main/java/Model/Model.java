package Model;

import java.sql.*;
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


    public String getFieldValue(int index){
        return "";
    }

        public boolean inventoryCheck(String productName) {
        String[] fields = new String[4];
        String sql = "SELECT * FROM Products WHERE NAME =\"" + productName + "\"";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            int i = 0;
            // loop
            if (rs.next()) {
                for (int j = 0; j < 4; j++) {
                    fields[j] = rs.getString(j + 1);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return false;

        }
        return false;
    }
}
