package Model;

import java.sql.*;
import java.util.ArrayList;
import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

    public ArrayList<String> getRecordsFieldsValues(String sqlQuery, int numOfColumns){
        ArrayList<String> recordsFieldsValues = new ArrayList<>();
    private void insert(String name, String supply, int price, String category){
        String sql = "INSERT INTO Users(USERNAME,PASSWORD,FIRSTNAME,LASTNAME,BIRTHDATE,CITY) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            for (int i = 1; i < numOfColumns; i++){
                recordsFieldsValues.add(rs.getString(i));
            }
            return recordsFieldsValues;

             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, supply);
            pstmt.setInt(3, price);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return null;
        }
    }

        public boolean inventoryCheck(String productName) {
        String sqlQuery = "SELECT * FROM Products WHERE NAME =\"" + productName + "\"";
        ArrayList <String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        if (Integer.parseInt(recordValues.get(2)) > 0 ){
            return true;
            System.out.println("not goodddd");
        }
    }

}
