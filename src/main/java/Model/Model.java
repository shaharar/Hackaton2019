package Model;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import Controller.Controller;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Model {

    Controller controller;
    static int counterOfOrders = 1;
    ArrayList<String> currentUser;
    boolean isAdmin = false;


    //constructor
//    public Model(Controller controller) {
//        this.controller = controller;
   // }

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE user_name =\"" + username + "\"";
        ArrayList<String> userInfo = getRecordsFieldsValues(sql, 9);
        if (isUserExist(userInfo)) { //if user was found in db
            String realPass = getPassword(userInfo);
            if (realPass.equals(password)) {
                return true;
            }
        }
        return false;
    }

    private String getPassword(ArrayList<String> userInfo) {
        return userInfo.get(1);
    }


    private boolean isUserExist(ArrayList<String> userInfo) {
        if (userInfo != null) {
            return true;
        }
        return false;
    }

    private boolean isProductExist(ArrayList<String> productInfo) {
        if (productInfo != null) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOrderExist(ArrayList<String> orderInfo) {
        if (orderInfo != null) {
            return true;
        } else {
            return false;
        }
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
            if (rs.next()) {
                for (int i = 1; i < numOfColumns + 1; i++) {
                    recordsFieldsValues.add(rs.getString(i));
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsFieldsValues;
    }

    public boolean inventoryCheck(String productName) throws SQLException {
        String sqlQuery = "SELECT * FROM Products WHERE product_name =\"" + productName + "\"";
        ArrayList<String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        int supply = Integer.parseInt(recordValues.get(1));
        if (supply > 0) {
            return true;
        }
        return false;
    }

    public void lessCounter(String productName) throws SQLException {
        String sqlQuery = "SELECT * FROM Products WHERE product_name =\"" + productName + "\"";
        ArrayList<String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        int supply = Integer.parseInt(recordValues.get(1));
        supply = supply - 1;
        updateProduct("supply", String.valueOf(supply), productName);
    }

    public void upCounter(String productName) throws SQLException {
        String sqlQuery = "SELECT * FROM Products WHERE product_name =\"" + productName + "\"";
        ArrayList<String> recordValues = getRecordsFieldsValues(sqlQuery, 4);
        int supply = Integer.parseInt(recordValues.get(1));
        supply = supply + 1;
        updateProduct("supply", String.valueOf(supply), productName);
    }

    private boolean insertUser(String uName, String password, String first_name, String last_name, String email, String phone, String cardNumber, String dateCard, String cvv) throws SQLException {
        String sqlCheck = "SELECT * FROM Users WHERE user_name =\"" + uName + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 9);
        if (isUserExist(check))
            return false;

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
        return true;
    }

    public boolean deleteUser(String userName) {
        String sqlCheck = "SELECT * FROM Users WHERE user_name =\"" + userName + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 9);
        if (isUserExist(check) == false)
            return false;
        String sql = "DELETE FROM Users WHERE user_name = \"" + userName + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
        return true;
    }

    public boolean updateUser(String file, String theNewOne, String uName) throws SQLException {
        String sqlCheck = "SELECT * FROM Users WHERE user_name =\"" + uName + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 9);
        if (isUserExist(check) == false)
            return false;
        String sql = "SELECT * FROM Users WHERE user_name=\"" + uName + "\"";
        ArrayList<String> oldFiles = getRecordsFieldsValues(sql, 9);
        deleteUser(oldFiles.get(0));
        if (file.equals("uName")) {
            insertUser(theNewOne, oldFiles.get(1), oldFiles.get(2), oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("password")) {
            insertUser(oldFiles.get(0), theNewOne, oldFiles.get(2), oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("first_name")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), theNewOne, oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("last_name")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), theNewOne, oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("email")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3), theNewOne,
                    oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("phone")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), theNewOne, oldFiles.get(6), oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("cardNumber")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), oldFiles.get(5), theNewOne, oldFiles.get(7), oldFiles.get(8));
        } else if (file.equals("dateCard")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), oldFiles.get(5), oldFiles.get(6), theNewOne, oldFiles.get(8));
        } else if (file.equals("cvv")) {
            insertUser(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), oldFiles.get(5), oldFiles.get(6), oldFiles.get(7), theNewOne);
        }
        return true;
    }

    private boolean insertOrder(String orderId, String order_details, String user_name, String des_time, String order_time, String status, String price) throws SQLException {
        String sqlCheck = "SELECT * FROM Orders WHERE orderID =\"" + counterOfOrders + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 7);
        if (isOrderExist(check))
            return false;
        String sql = "INSERT INTO Orders (orderID, order_details, user_name, des_time, order_time,status,price) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(counterOfOrders));
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
        counterOfOrders++;
        return true;
    }

    public boolean deleteOrder(String orderId) {
        String sqlCheck = "SELECT * FROM Orders WHERE orderID =\"" + orderId + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 7);
        if (isOrderExist(check) == false)
            return false;
        String sql = "DELETE FROM Orders WHERE orderID = \"" + orderId + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("not bgoood");
        }
        counterOfOrders--;
        return true;
    }

    public boolean updateOrder(String file, String theNewOne, String oId) throws SQLException {
        String sqlCheck = "SELECT * FROM Orders WHERE orderID =\"" + oId + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 7);
        if (isOrderExist(check) == false)
            return false;
        if (oId.equals(OrderStatus.IN_PROCESS) && !file.equals("status"))
            return false;
        String sql = "SELECT * FROM Orders WHERE orderID=\"" + oId + "\"";
        ArrayList<String> oldFiles = getRecordsFieldsValues(sql, 7);
        deleteOrder(oldFiles.get(0));
        if (file.equals("productId")) {
            insertOrder(theNewOne, oldFiles.get(1), oldFiles.get(2), oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6));
        } else if (file.equals("order_details")) {
            if (theNewOne.contains(" ")) {
                String[] newProducts = theNewOne.split(" ");
                for (int i = 0; i < newProducts.length; i++) {
                    boolean bool = inventoryCheck(newProducts[i]);
                    if (bool == false)
                        return false;
                }
                for (int i = 0; i < newProducts.length; i++) {
                    lessCounter(newProducts[i]);
                }
            } else {
                boolean bool = inventoryCheck(theNewOne);
                if (bool == false)
                    return false;
                else
                    lessCounter(theNewOne);
            }
            if (oldFiles.get(1).contains(" ")) {
                String[] old = oldFiles.get(1).split(" ");
                for (int i = 0; i < old.length; i++) {
                    upCounter(old[i]);
                }
            } else
                upCounter(oldFiles.get(1));
            insertOrder(oldFiles.get(0), theNewOne, oldFiles.get(2), oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6));
        } else if (file.equals("user_name")) {
            insertOrder(oldFiles.get(0), oldFiles.get(1), theNewOne, oldFiles.get(3), oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6));
        } else if (file.equals("des_time")) {
            insertOrder(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), theNewOne, oldFiles.get(4),
                    oldFiles.get(5), oldFiles.get(6));
        } else if (file.equals("order_time")) {
            insertOrder(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3), theNewOne,
                    oldFiles.get(5), oldFiles.get(6));
        } else if (file.equals("status")) {
            insertOrder(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), theNewOne, oldFiles.get(6));
        } else if (file.equals("price")) {
            insertOrder(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), oldFiles.get(3),
                    oldFiles.get(4), oldFiles.get(5), theNewOne);
        }

        return true;
    }

    private boolean insertProduct(String name, String supply, String price, String category) {
        String sqlCheck = "SELECT * FROM Products WHERE product_name =\"" + name + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 4);
        if (isProductExist(check))
            return false;
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
        return true;
    }

    public boolean deleteProduct(String productName) {
        String sqlCheck = "SELECT * FROM Products WHERE product_name =\"" + productName + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 4);
        if (isProductExist(check) == false)
            return false;
        String sql = "DELETE FROM Products WHERE product_name = \"" + productName + "\"";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        return true;
    }


    public boolean updateProduct(String file, String theNewOne, String pName) throws SQLException {
        String sqlCheck = "SELECT * FROM Products WHERE product_name =\"" + pName + "\"";
        ArrayList<String> check = getRecordsFieldsValues(sqlCheck, 4);
        if (isProductExist(check) == false)
            return false;
        String sql = "SELECT * FROM Products WHERE product_name=\"" + pName + "\"";
        ArrayList<String> oldFiles = getRecordsFieldsValues(sql, 4);
        deleteProduct(oldFiles.get(0));
        if (file.equals("product_name")) {
            insertProduct(theNewOne, oldFiles.get(1), oldFiles.get(2), oldFiles.get(3));
        } else if (file.equals("supply")) {
            insertProduct(oldFiles.get(0), theNewOne, oldFiles.get(2), oldFiles.get(3));
        } else if (file.equals("price")) {
            insertProduct(oldFiles.get(0), oldFiles.get(1), theNewOne, oldFiles.get(3));
        } else if (file.equals("category")) {
            insertProduct(oldFiles.get(0), oldFiles.get(1), oldFiles.get(2), theNewOne);
        }

        return true;
    }

    public boolean addProductToOrder(ArrayList<String> products, String user_name, String des_time, String order_time, String status) throws SQLException {
        String myProducs = "";
        int sum = 0;
        ArrayList<String> info;
        for (int i = 0; i < products.size(); i++) {
            boolean bool = inventoryCheck(products.get(i));
            if (bool == false)
                return false;
            myProducs = myProducs + products.get(i) + " ";
            String sql = "SELECT * FROM Products WHERE product_name =\"" + products.get(i) + "\"";
            info = getRecordsFieldsValues(sql, 4);
            sum = sum + Integer.valueOf(info.get(2));
        }
        for (int i = 0; i < products.size(); i++) {
            lessCounter(products.get(i));
        }
//        myProducs=myProducs+products.get(products.size()-1);///for the split after
//        String sql = "SELECT * FROM Products WHERE product_name =\"" + products.get(products.size()-1) + "\"";
//        info=getRecordsFieldsValues(sql,4);
//        sum=sum+Integer.valueOf(info.get(2));

        insertOrder(String.valueOf(counterOfOrders), myProducs, user_name, des_time, order_time, status, String.valueOf(sum));
        return true;
    }

    public void signUp(String txt_username, String psw_password, String txt_firstName, String txt_lastName, String txt_email, String txt_phone, String txt_cardNo, String txt_expDate, String txt_cvv, String admin) {
        try {
            insertUser(txt_username, psw_password, txt_firstName, txt_lastName, txt_email, txt_phone, txt_cardNo, txt_expDate, txt_cvv);
            ArrayList<String> userInfo = new ArrayList<String>();
            userInfo.add(txt_username);
            userInfo.add(psw_password);
            userInfo.add(txt_firstName);
            userInfo.add(txt_lastName);
            userInfo.add(txt_email);
            userInfo.add(txt_phone);
            userInfo.add(txt_cardNo);
            userInfo.add(txt_expDate);
            userInfo.add(txt_cvv);
            currentUser = userInfo;
            if (!admin.equals("")) {
                isAdmin = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateStatus(String orderId) throws SQLException {
        boolean bool;
        String sqlCheck = "SELECT * FROM Orders WHERE orderID =\"" + orderId + "\"";
        ArrayList<String> array=getRecordsFieldsValues(sqlCheck,7);
        if(array.get(5).equals(OrderStatus.IN_PROCESS))
            bool = updateOrder("status", "DONE", orderId);
        else if(sqlCheck.equals(OrderStatus.APPROVED))
            bool=updateOrder("status","IN_PROCESS", orderId);
        else
            bool=false;
        return bool;
    }

    public boolean checkStatus(String orderid){
        String sqlCheck = "SELECT * FROM Orders WHERE orderID =\"" + orderid + "\"";
        ArrayList<String>array=getRecordsFieldsValues(sqlCheck,7);
        if(array.get(5).equals("DONE")) {
            deleteOrder(orderid);
            return true;
        }
        else
            return false;
    }

    public String checkOrderId(String userName){
        String toReturn="";
        String sql = "SELECT * FROM Orders WHERE user_name =\"" + userName + "\"";
        ArrayList<String>array=getRecordsFieldsValues(sql,7);
        String sqlCheck="";
        for(int i=0;i<array.size();i++)
            sqlCheck=sqlCheck+array.get(i);
        String[]myOrders=sqlCheck.split(" ");
        if(myOrders.length>7){
            for(int i=0;i<myOrders.length;i=i+7){
                if(i>myOrders.length)
                    break;
                toReturn=toReturn+myOrders[i];
            }
        }
        else
            toReturn=toReturn+myOrders[0];

        return toReturn;
    }

    public ArrayList <ArrayList<String>> getRecords (String sqlQuery, int numOfColumns){
        ArrayList<ArrayList<String>> recordsFieldsValues = new ArrayList<>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            while (rs.next()){
                ArrayList<String> record = new ArrayList<>();
                for (int i = 1; i < numOfColumns + 1; i++) {
                    record.add(rs.getString(i));
                }
                recordsFieldsValues.add(record);
            }
            if (recordsFieldsValues.size() == 0){
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordsFieldsValues;
    }

    public List<String> showOrders(){
        ArrayList<String> ordersList=new ArrayList<>();
        String sqlCheck = "SELECT * FROM Orders";
        String toAdd="";
       ArrayList<ArrayList<String>> allOrders=getRecords(sqlCheck,7);
       if(allOrders==null)
           System.out.println("nullllllll");
       for(int i=0;i<allOrders.size();i++){
           for(int j=0;j<allOrders.get(i).size();j++){
               toAdd=toAdd+allOrders.get(i).get(j)+" ";
           }
           ordersList.add(toAdd);
           System.out.println(toAdd);
           toAdd="";
        }
        return ordersList;
    }
    public  static void main(String [] args) throws SQLException {
        Model m= new Model();
        m.showOrders();
//
        ArrayList<String>list=new ArrayList<>();
        list.add("water");
        list.add("klikB");
        m.deleteOrder("0");
//        m.deleteProduct("yarden");
//        m.deleteUser("yarden");
//        m.deleteOrder("2");
//        m.updateProduct("supply","15","klikB");
       // m.addProductToOrder(list, "yarden", "7:00","7:20","ok");
        m.addProductToOrder(list, "tali", "7:00","7:20","ok");
        //m.insertProduct("yarden", "rm", "70","35");
        //m.insertUser("yarden", "rm","clskvjdv","sdvslkjs", "70","35","100","clwvkn","flwkjf");
       // m.updateUser("password","000000","yarden");
      //  m.updateOrder("price","000000","1");
       // m.updateProduct("price", "111111","yarden");

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
