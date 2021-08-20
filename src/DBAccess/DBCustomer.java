package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class accesses the customers in the database associated with the application. */

public class DBCustomer {

    /**This method accesses the database and retrieves all customers.
     The database uses a SQL query to access the database and return a list of customers.
     @return Returns an observable list of customers
     */

    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT Customer_ID, Customer_Name, Country, countries.Country_ID, Division, first_level_divisions.Division_ID, Address, Postal_Code, Phone\n" +
                         "FROM customers, countries, first_level_divisions\n" +
                         "WHERE countries.Country_ID = first_level_divisions.Country_ID AND first_level_divisions.Division_ID = customers.Division_ID;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int customerID = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String country = resultSet.getString("Country");
                int countryID = resultSet.getInt("countries.Country_ID");
                String address = resultSet.getString("Address");
                String division = resultSet.getString("Division");
                int divisionID = resultSet.getInt("first_level_divisions.Division_ID");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");

                Customer cust = new Customer(customerID, customerName, country, countryID, division, divisionID, address, postalCode, phone);
                customerList.add(cust);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return customerList;
    }

    /**This method modifies a customer in the database associated with the application.
     The method modifies a customer record in the database.
     @param customerName Name of the customer
     @param customerAddress the address of the customer
     @param postalCode The postal code of the customer
     @param phoneNumber The customer's phone number
     @param id The division ID of the customer
     @param customerID the customer ID

     */

    public static void modifyCustomer(String customerName, String customerAddress, String postalCode, String phoneNumber, int id, int customerID) {

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = 'KJ', Division_ID = ? WHERE Customer_ID = ?";


        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, id);
            ps.setInt(6, customerID);

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**This method creates a customer in the database.
     The method accesses the database and modifies the customer record whose ID matches the customer ID passed in.
     @param customerName Name of the customer
     @param customerAddress the address of the customer
     @param postalCode The postal code of the customer
     @param phoneNumber The customer's phone number
     @param id The customer ID
     */

    public static void createCustomer(String customerName, String customerAddress, String postalCode, String phoneNumber, int id) {

        String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?, NOW(), 'KJ', NOW(), 'KJ', ?)";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, customerAddress);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setInt(5, id);

            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**This method deletes a customer from the database.
     The method deletes a customers appointments first, then deletes the customer record.
     @param customerID the ID of the customer to be deleted
     */

    public static void deleteCustomer(int customerID){
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        String sqlC = "DELETE FROM customers WHERE Customer_ID = ?";





        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, customerID);

            ps.execute();
        }
        catch (SQLException throwables) {

            throwables.printStackTrace();
        }

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlC);
            ps.setInt(1, customerID);

            ps.execute();
        }
        catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }
}
