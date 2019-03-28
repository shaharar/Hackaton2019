package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Model {
    //constructor
    public Model() {
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

    private void insert(String name, String supply, int price, String category){
        String sql = "INSERT INTO Users(USERNAME,PASSWORD,FIRSTNAME,LASTNAME,BIRTHDATE,CITY) VALUES(?,?,?,?,?,?)";
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
    
}
