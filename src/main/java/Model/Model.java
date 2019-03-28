package Model;

import java.sql.*;

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

    private void insert(String name, String supply, int price, String category){
        String sql = "INSERT INTO Products(product_name,supply,price,category) VALUES(?,?,?,?)";
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
    public  static void main(String [] args){
        Model m= new Model();
        m.insert("roni", "rm", 34,"35");

    }
}
