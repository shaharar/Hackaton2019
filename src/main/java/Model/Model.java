package Model;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Model {

     Controller controller;
     static int counterOfOrders=1;
    ArrayList<String> currentUser;

    //constructor
    public Model(Controller controller) {
        this.controller = controller;
    }

        public boolean login(String username, String password) {
            String sql = "SELECT * FROM Users WHERE user_name =\"" + username + "\"";
            ArrayList<String> userInfo = getRecordsFieldsValues(sql, 9);
            if(isUserExist(userInfo)){ //if user was found in db
            String realPass = getPassword(userInfo);
            if( realPass.equals(password)){
                return true;
            }
        }
        return false;
    }

        private String getPassword(ArrayList<String> userInfo) {
            return userInfo.get(1);
        }


        private boolean isUserExist(ArrayList<String> userInfo) {
            if(userInfo != null){
                return true;
            }
                return false;
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

    public ArrayList<String> getRecordsFieldsValues(String sqlQuery, int numOfColumns) {
        ArrayList<String> recordsFieldsValues = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()){
                for (int i = 1; i < numOfColumns + 1; i++) {
                    recordsFieldsValues.add(rs.getString(i));
                }
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsFieldsValues;
    }

    public boolean inventoryCheck(String productName) {
        String sqlQuery = "SELECT * FROM Products WHERE product_name =\"" + productName + "\"";
        ArrayList <String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        if (Integer.parseInt(recordValues.get(1)) > 0 ){
            return true;
        }
        return false;
    }

    private void insertUser (String uName, String password, String first_name,String last_name, String email,String phone,String cardNumber, String dateCard,String cvv) throws SQLException {
        String sql = "INSERT INTO Users (user_name,password,first_name,last_name,email,phone,CardNumber,ExpirationDate,SecurityCode) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, uName);
            pstmt.setString(2, password);
            pstmt.setString(3, first_name);
            pstmt.setString(4, last_name);
            pstmt.setString(5, email);
            pstmt.setString(6, phone);
            pstmt.setString(7, cardNumber);
            pstmt.setString(8, dateCard);
            pstmt.setString(9, cvv);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void deleteUser(String userName) {
        String sql = "DELETE FROM Users WHERE user_name = \"" + userName+ "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
    }

    public void updateUser(String file, String theNewOne,String uName) throws SQLException {
        String sql = "SELECT * FROM Users WHERE user_name=\"" + uName+ "\"";
        ArrayList<String> oldFiles=getRecordsFieldsValues(sql,9);

        if(file.equals("uName")){
            insertUser(theNewOne,oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("password")){
            insertUser(oldFiles.get(0),theNewOne,oldFiles.get(2),oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("first_name")){
            insertUser(oldFiles.get(0),oldFiles.get(1),theNewOne,oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("last_name")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),theNewOne,oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("email")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),theNewOne,
                    oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("phone")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),theNewOne,oldFiles.get(6),oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("cardNumber")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),oldFiles.get(5),theNewOne,oldFiles.get(7),oldFiles.get(8));
        }
        else if(file.equals("dateCard")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),oldFiles.get(5),oldFiles.get(6),theNewOne,oldFiles.get(8));
        }
        else if(file.equals("cvv")){
            insertUser(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),oldFiles.get(5),oldFiles.get(6),oldFiles.get(7),theNewOne);
        }
        deleteUser(oldFiles.get(0));
    }

    private void insertOrder (String productId, String order_details, String user_name,String des_time, String order_time,String status,String price) throws SQLException {
        String sql = "INSERT INTO Orders (productID, order_details, user_name, des_time, order_time,status,price) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productId);
            pstmt.setString(2, order_details);
            pstmt.setString(3, user_name);
            pstmt.setString(4, des_time);
            pstmt.setString(5, order_time);
            pstmt.setString(6, status);
            pstmt.setString(7, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());

        }
    }

    public void deleteOrder(String orderId) {
        String sql = "DELETE FROM Orders WHERE productID = \"" + orderId+ "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
    }

    public void updateOrder(String file, String theNewOne,String pId) throws SQLException {
        String sql = "SELECT * FROM Orders WHERE productId=\"" + pId+ "\"";
        ArrayList<String> oldFiles=getRecordsFieldsValues(sql,7);

        if(file.equals("productId")){
            insertOrder(theNewOne,oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6));
        }
        else if(file.equals("order_details")){
            insertOrder(oldFiles.get(0),theNewOne,oldFiles.get(2),oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6));
        }
        else if(file.equals("user_name")){
            insertOrder(oldFiles.get(0),oldFiles.get(1),theNewOne,oldFiles.get(3),oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6));
        }

        else if(file.equals("des_time")){
            insertOrder(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),theNewOne,oldFiles.get(4),
                    oldFiles.get(5),oldFiles.get(6));
        }
        else if(file.equals("order_time")){
            insertOrder(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),theNewOne,
                    oldFiles.get(5),oldFiles.get(6));
        }
        else if(file.equals("status")){
            insertOrder(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),theNewOne,oldFiles.get(6));
        }
        else if(file.equals("price")){
            insertOrder(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),oldFiles.get(3),
                    oldFiles.get(4),oldFiles.get(5),theNewOne);
        }

        deleteOrder(oldFiles.get(0));
    }

    private void insertProduct (String name, String supply, String price, String category){
        String sql = "INSERT INTO Products(product_name,supply,price,category) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, supply);
            pstmt.setString(3, price);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public void deleteProduct(String productName) {
        String sql = "DELETE FROM Products WHERE product_name = \"" + productName+ "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }


    public void updateProduct(String file, String theNewOne, String pName) throws SQLException {
        String sql = "SELECT * FROM Products WHERE product_name=\"" + pName+ "\"";
        ArrayList<String> oldFiles=getRecordsFieldsValues(sql,4);

        if(file.equals("product_name")){
            insertProduct(theNewOne,oldFiles.get(1),oldFiles.get(2),oldFiles.get(3));
        }
        else if(file.equals("supply")){
            insertProduct(oldFiles.get(0),theNewOne,oldFiles.get(2),oldFiles.get(3));
        }
        else if(file.equals("price")){
            insertProduct(oldFiles.get(0),oldFiles.get(1),theNewOne,oldFiles.get(3));
        }

        else if(file.equals("category")){
            insertProduct(oldFiles.get(0),oldFiles.get(1),oldFiles.get(2),theNewOne);
        }

        deleteProduct(oldFiles.get(0));
    }

    public void addProductToOrder(List<String> products,String user_name, String des_time, String order_time, String status) throws SQLException {
        String myProducs="";
        int sum=0;
        ArrayList<String> info;
        for(int i=0;i<products.size()-1;i++) {
            myProducs = myProducs + products.get(i) + " ";
            String sql = "INSERT INTO Products(name,supply,price,category) VALUES(?,?,?,?)";
            info=getRecordsFieldsValues(sql,4);
            sum=sum+Integer.valueOf(info.get(2));

        }
        myProducs=myProducs+products.get(products.size()-1);///for the split after
        String sql = "INSERT INTO Products(name,supply,price,category) VALUES(?,?,?,?)";
        info=getRecordsFieldsValues(sql,4);
        sum=sum+Integer.valueOf(info.get(2));

        insertOrder(String.valueOf(counterOfOrders),myProducs,user_name,des_time,order_time,status,String.valueOf(sum));
    }
    public  static void main(String [] args) throws SQLException {
//        Model m= new Model();
//        m.insertProduct("yarden", "rm", "70","35");
//        m.insertOrder("yarden", "rm", "70","35","100","clwvkn","flwkjf");
//        m.insertUser("yarden", "rm","clskvjdv","sdvslkjs", "70","35","100","clwvkn","flwkjf");
//        String sqlQuery = "SELECT * FROM Products WHERE product_name =\"" + "yarden" + "\"";
//        ArrayList<String> record = m.getRecordsFieldsValues(sqlQuery,4);
//        if (record == null){
//            System.out.println("record doesn't exist");
//        }
//        else{
//            for (int i = 0; i < record.size(); i++){
//                System.out.println(record.get(i));
//            }
//        }
//        m.deleteProduct("einat");
//        m.deleteUser("einat");
//        m.deleteOrder("einat");




    }
}
