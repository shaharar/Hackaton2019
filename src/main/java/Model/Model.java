package Model;

import java.sql.*;
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


    private void insertUser (String name, String supply, int price, String category){
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

    public void DeleteUser(String productName) {
        String sql = "DELETE FROM Products WHERE ProductName = \"" + productName+ "\"";

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
