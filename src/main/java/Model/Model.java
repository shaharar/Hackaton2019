package Model;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<String> getRecordsFieldsValues(String sqlQuery, int numOfColumns){
        ArrayList<String> recordsFieldsValues = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            for (int i = 1; i < numOfColumns; i++){
                recordsFieldsValues.add(rs.getString(i));
            }
            return recordsFieldsValues;

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
        }
        return false;
    }
}
