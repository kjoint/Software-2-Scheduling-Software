package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomer;
import DBAccess.DBFirstLevelDiv;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Countries;
import model.Customer;
import model.FirstLevelDiv;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/**This class controls the add customer screen in the application. */

public class AddCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    /**Private field for the customer's country.*/

    @FXML
    private ComboBox<Countries> countryCombo;

    /**Private field for the customer's region/state. */

    @FXML
    private ComboBox<FirstLevelDiv> stateCombo;

    /**Private field for the customer's ID. */

    @FXML
    private TextField customerID;

    /**Private field for the customer's name. */

    @FXML
    private TextField customerName;

    /**Private field for the customer's address. */

    @FXML
    private TextField customerAddress;

    /**Private field for the customer's postal code. */

    @FXML
    private TextField customerPostalCode;

    /**Private field for the customer's phone number. */

    @FXML
    private TextField customerPhoneNumber;

    ObservableList<String> firstLevelDiv;
    private String errorMessage = new String();

    /**This method exits the add customer screen without saving an data.
     The method returns to the main screen without saving any data.*/

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes will not be saved. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**This method validates and saves the data entered in the add customer screen.
     The method validates the data entered, generates a message if there is an error and saves the customer if the data is valid.*/

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
            //get the data
            String customerName = this.customerName.getText();
            String customerAddress = this.customerAddress.getText();
            String postalCode = this.customerPostalCode.getText();
            String phoneNumber = this.customerPhoneNumber.getText();
            FirstLevelDiv firstLevelDiv = stateCombo.getValue();



            //validate the data
            try {
                errorMessage = Customer.customerCheck(customerName, customerAddress, postalCode, phoneNumber, firstLevelDiv.getID());
                if (errorMessage.length() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Error");
                    alert.setContentText(errorMessage);
                    alert.showAndWait();
                    errorMessage = "";
                } else {
                    DBCustomer.createCustomer(customerName, customerAddress, postalCode, phoneNumber, firstLevelDiv.getID());


                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("There was an error saving the customer");
                alert.setContentText("Cannot leave blank fields ");
                alert.showAndWait();
            }





    }

    /**This method is called first when the add customer screen is accessed.
     The method pre-populates data in the add customer screen.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(DBCountries.getAllCountries());
        countryCombo.getSelectionModel().selectFirst();
        setCountryCombo(null);

    }

    /**This method retrieves the first level divisions from a selected country and populates the combo box on the screen. */

    @FXML
    private void setCountryCombo(ActionEvent event){
        Countries c = countryCombo.getValue();
        System.out.println("Country selected is: " + c.getID() + " --> " + c.getName());


             stateCombo.setItems(DBFirstLevelDiv.getDivisions(c.getID()));
             stateCombo.getSelectionModel().selectFirst();




    }



}
