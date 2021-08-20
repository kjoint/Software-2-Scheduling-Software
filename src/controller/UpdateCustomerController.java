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

/**This class controls the update customer screen for the application. */

public class UpdateCustomerController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField addCustomerID;

    @FXML
    private TextField addCustomerName;

    @FXML
    private TextField addCustomerAddress;

    @FXML
    private TextField addCustomerPostalCode;

    @FXML
    private TextField customerPhoneNumber;

    @FXML
    private ComboBox<Countries> countryCombo;

    @FXML
    private ComboBox<FirstLevelDiv> stateCombo;



    private String errorMessage = new String();

    /**This method retrieves the selected customers data and populates the update customer screen.
     The method gets the selected customer's record and populates the update customer screen in the application.
     @param customer the selected customer to be updated
     */

    public void sendCustomer(Customer customer) {
        addCustomerID.setText(String.valueOf(customer.getID()));
        addCustomerName.setText(customer.getName());
        addCustomerID.setText(String.valueOf(customer.getID()));
        addCustomerAddress.setText(customer.getAddress());
        addCustomerPostalCode.setText(customer.getPostalCode());
        customerPhoneNumber.setText(customer.getPhone());

        for(Countries c : countryCombo.getItems()){
            if(c.getID() == customer.getCountryID()){
                countryCombo.setValue(c);
                break;
            }
            //stateCombo.setItems(DBFirstLevelDiv.getDivisions(c.getID()));
            //stateCombo.getSelectionModel().select(customer.getDivision());
        }
        stateCombo.setItems(DBFirstLevelDiv.getDivisions(customer.getCountryID()));
        for(FirstLevelDiv f : stateCombo.getItems()){
            if(f.getID() == customer.getDivisionID()){
                stateCombo.setValue(f);
                break;
            }
        }
    }




    /**This method returns to the main screen without saving any data. */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes will not be saved. Do you wish to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
          stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
          scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
          stage.setScene(new Scene(scene));
          stage.show();

        }

    }

    /**This method saves the updated customer data and generates an error message if the data in invalid.
     @param event */

    @FXML
    void onActionSave(ActionEvent event) throws IOException {
        String customerName = this.addCustomerName.getText();
        String customerAddress = this.addCustomerAddress.getText();
        String postalCode = this.addCustomerPostalCode.getText();
        String phoneNumber = this.customerPhoneNumber.getText();
        FirstLevelDiv firstLevelDiv = stateCombo.getValue();
        int customerID = Integer.parseInt(this.addCustomerID.getText());



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
                DBCustomer.modifyCustomer(customerName, customerAddress, postalCode, phoneNumber, firstLevelDiv.getID(), customerID);


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

    /**This method initializes the update customer screen and populates the countries combo box. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(DBCountries.getAllCountries());

        // test
        //countryCombo.getSelectionModel().selectFirst();
        //Countries c = countryCombo.getValue();


    }

    /**This method sets the state combo based on the user's country combo selection.
     @param event
     */

    @FXML
    private void setCountryCombo(ActionEvent event){
        Countries c = countryCombo.getValue();
        System.out.println("Country selected is: " + c.getID() + " --> " + c.getName());


        stateCombo.setItems(DBFirstLevelDiv.getDivisions(c.getID()));
        stateCombo.getSelectionModel().selectFirst();

    }


}
