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
//    public Model(Controller controller) {
//        this.controller = controller;
//    }

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

    public ArrayList<String> getRecordsFieldsValues(String sqlQuery, int numOfColumns) {
        ArrayList<String> recordsFieldsValues = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            for (int i = 1; i < numOfColumns; i++) {
                recordsFieldsValues.add(rs.getString(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsFieldsValues;
    }

    public boolean inventoryCheck(String productName) {
        String sqlQuery = "SELECT * FROM Products WHERE NAME =\"" + productName + "\"";
        ArrayList <String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        if (Integer.parseInt(recordValues.get(2)) > 0 ){
            return true;
        }
        return false;
    }

    private void insertUser (String uName, String password, String first_name,String last_name, String email,String phone) throws SQLException {
        String sql = "INSERT INTO Users (uName,password,first_name,last_name,email,phone) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uName);
            pstmt.setString(2, password);
            pstmt.setString(3, first_name);
            pstmt.setString(4, last_name);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("not goodddd");
        }
    }

    public void DeleteUser(String userName) {
        String sql = "DELETE FROM Users WHERE ProductName = \"" + userName+ "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
    }

    private void insertProduct (String name, String supply, int price, String category){
        String sql = "INSERT INTO Products(name,supply,price,category) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, supply);
            pstmt.setInt(3, price);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("not goodddd");
        }
    }

    public void DeleteProduct(String productName) {
        String sql = "DELETE FROM Products WHERE ProductName = \"" + productName+ "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
    }


    public  static void main(String [] args){
        Model m= new Model();
        m.insertProduct("roni", "rm", 34,"35");

    }
}