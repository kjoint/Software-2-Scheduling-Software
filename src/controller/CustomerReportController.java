package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCountries;
import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Countries;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
/**This class controls the Customer report screen in the application. */
public class CustomerReportController implements Initializable {

    Stage stage;
    Parent scene;

    /**Private field for country selection. */
    @FXML
    private ComboBox<Countries> countryCombo;

    /**Private table column for customers by country. */

    @FXML
    private TableView<Customer> customerCountryTable;

    /**private column for customer ID. */

    @FXML
    private TableColumn<Customer, Integer> customerIDCol;

    /**private column for customer name. */

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    /**private column for customer country. */

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    /**private column for customer region/state. */

    @FXML
    private TableColumn<Customer, String> customerStateCol;

    /**private column for customer address. */

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    /**private column for customer postal code. */

    @FXML
    private TableColumn<Customer, String> customerCodeCol;

    /**private column for customer phone number. */

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;


    @FXML
    private Label canadaCountTxt;

    @FXML
    private Label ukCountTxt;

    @FXML
    private Label usCountTxt;

    /**This method gets a list of customers based on a combo box selection of country and populates the tableview based on that selection. */

    @FXML
    void onActionGetCountries(ActionEvent event) {
        customerCountryTable.setItems(Customer.getCountryCustomers(countryCombo.getValue()));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerCodeCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }

    /**This method generates a report with the number of customers for each country. */

    @FXML
    void onActionGetReport(ActionEvent event) {
        int usCount = 0;
        int ukCount = 0;
        int canadaCount = 0;
        String us = "U.S";
        String uk = "UK";
        String can = "Canada";

        for(Customer c : DBCustomer.getAllCustomers()){
            if(c.getCountry().equals(can)){
                canadaCount += 1;
            }
            if(c.getCountry().equals(uk)){
                ukCount += 1;
            }
            if(c.getCountry().equals(us)){
                usCount += 1;
            }
        }
        canadaCountTxt.setText(canadaCount + " Customers");
        ukCountTxt.setText(ukCount + " Customers");
        usCountTxt.setText(usCount + " Customers");
    }

    /**This method accesses the main screen of the application. */

    @FXML
    void onActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method is the first called when the customer report screen is accessed in the application.
    The method pre-populates the data in the customer report screen.
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(DBCountries.getAllCountries());
        customerCountryTable.setItems(DBCustomer.getAllCustomers());
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerStateCol.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerCodeCol.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("Phone"));
    }
}
