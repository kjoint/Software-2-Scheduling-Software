package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Countries;
import model.Customer;
import model.FirstLevelDiv;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class controls the main screen of the application. */

public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, String> customerCountry;

    @FXML
    private TableColumn<Customer, String> customerState;

    @FXML
    private TableColumn<Customer, String> customerAddress;

    @FXML
    private TableColumn<Customer, String> customerPostalCode;

    @FXML
    private TableColumn<Customer, String> customerPhoneNumber;

    int customerToUpdateIndex = -1;






    /**This method accesses the add customer screen. */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();


    }

    /**This method deletes a selected customer from the customer list. */

    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        Customer customerToDelete = customerTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer ID# " + customerToDelete.getID() + ", Name: " + customerToDelete.getName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            DBCustomer.deleteCustomer(customerToDelete.getID());

            //Alert alert1 = new Alert(Alert.AlertType.WARNING, "You have deleted customer ID# " + customerToDelete.getID() + ", Name: " + customerToDelete.getName());
            //Optional<ButtonType> result1 = alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**This method accesses the main appointment screen of the application. */

    @FXML
    void onActionSchedule(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method accesses the customer appointment's by contact. */

    @FXML
    void onActionContactReport(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method accesses the appointment by type report screen in the application. */

    @FXML
    void onActionTypeReport(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/TypeReports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method accesses the customer report screen of the application. */

    @FXML
    void onActionCustomerReport(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomerReport.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method accesses the update customer screen in the application. */

    @FXML
    void onActionUpdate(ActionEvent event) throws IOException {

        Customer customerIndex = customerTable.getSelectionModel().getSelectedItem();
        customerToUpdateIndex = DBCustomer.getAllCustomers().indexOf(customerIndex);

        if(customerTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Customer not selected");
            alert.setHeaderText("Please choose a customer to update");
            alert.showAndWait();
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.FXML"));
            Parent scene = loader.load();
            UpdateCustomerController UPController = loader.getController();
            UPController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            //scene = FXMLLoader.load(getClass().getResource("/view/UpdateCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }




    }




    /**This method initializes the main screen and populates the screen with customer data. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTable.setItems(DBCustomer.getAllCustomers());
        customerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        customerCountry.setCellValueFactory(new PropertyValueFactory<>("Country"));
        customerState.setCellValueFactory(new PropertyValueFactory<>("Division"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("PostalCode"));
        customerPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("Phone"));

    }
}
