package model;

import DBAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**This class creates Customer objects*/
public class Customer {



    private int customerID;
    private String customerName;
    private String country;
    private int countryID;
    private String division;
    private int divisionID;
    private String address;
    private String postalCode;
    private String phone;

    /**This method is the constructor for a Customer object.
     The method is called when creating a Customer object.
     @param customerID The customer ID
     @param customerName The customer name
     @param country The customer's country
     @param countryID the ID of customer's country
     @param division The region/state of the customer
     @param divisionID The ID for the division(region/state)
     @param address The customer's address
     @param postalCode The customer's postal code
     @param phone The customer's phone nummber
     */

    public  Customer(int customerID, String customerName, String country, int countryID, String division, int divisionID, String address, String postalCode, String phone){

        this.customerID = customerID;
        this.customerName = customerName;
        this.country = country;
        this.countryID = countryID;
        this.division = division;
        this.divisionID = divisionID;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;


    }

    /**This method retrieves the customer's ID.
     The method gets the customer's ID.
     @return Returns the customer's ID
     */

    public int getID(){

        return customerID;
    }

    /**This method retrieves the customer's name.
     The method gets the customer's name.
     @return Returns the customer's name
     */

    public String getName(){

        return customerName;
    }

    /**This method retrieves the customer's country.
     The method gets the customer's country.
     @return Returns the customer's country
     */

    public String getCountry(){

        return country;
    }

    /**This method retrieves the customer's country ID.
     The method gets the customer's country ID.
     @return Returns the customer's country ID
     */

    public int getCountryID(){

        return countryID;
    }

    /**This method retrieves the customer's region/state.
     The method gets the customer's region/state.
     @return Returns the customer's region/state
     */

    public String getDivision(){

        return division;

    }

    /**This method retrieves the customer's division ID .
     The method gets the customer's division ID.
     @return Returns the customer's division ID
     */

    public int getDivisionID(){

        return divisionID;
    }

    /**This method retrieves the customer's address.
     The method gets the customer's address.
     @return Returns the customer's address
     */

    public String getAddress(){

        return address;

    }

    /**This method retrieves the customer's postal code.
     The method gets the customer's postal code.
     @return Returns the customer's postal code
     */

    public String getPostalCode(){

        return postalCode;

    }

    /**This method retrieves the customer's phone number.
     The method gets the customer's phone number.
     @return Returns the customer's phone number
     */

    public String getPhone(){

        return phone;

    }

    /**This method check customer data.
     The method tests customer data ensuring it is valid.
     @param name Customer name
     @param address Customer address
     @param postalCode Customer postal code
     @param phone Customer phone
     @param divisionID ID of customer's region/state
     @return String with list of errors
     */

    public static String customerCheck(String name, String address, String postalCode, String phone, int divisionID){
        String errorThrow = "";
        if(name.length() == 0){
            errorThrow += "A name must be entered.\n";
        }
        if(address.length() == 0){
            errorThrow += "An address must be entered\n";
        }
        if(postalCode.length() == 0){
            errorThrow += "A postal Code\n";
        }
        if(phone.length() == 0){
            errorThrow += "A phone number must be entered\n";
        }
        if(divisionID == 0 ){
            errorThrow += "A first level division must be selected";
        }
        return errorThrow;
    }

    /**This method retrieves a list of customers from the country passed in.
     The method takes a country parameter and builds a list of all customers from that country.
     @param country The country that will be used to build a list of all the customers from that country
     @return Returns a list of all customers from the country passed in the method
     */

    public static ObservableList<Customer> getCountryCustomers(Countries country){
        String sCountry = country.toString();
        ObservableList<Customer> countryCustomers = FXCollections.observableArrayList();
        for(Customer c : DBCustomer.getAllCustomers()){
            if(c.getCountry().equals(sCountry)){
                countryCustomers.add(c);
            }
        }
        return countryCustomers;
    }

    /**This method coverts a string to a string.
     @return Returns a string
     */

    @Override
    public String toString(){
        return("ID# " + Integer.toString(customerID) + ", " + customerName);
    }
}
