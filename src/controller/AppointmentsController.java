package controller;

import DBAccess.DBAppointments;
import DBAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**This class controls the appointment screen in the application. */

public class AppointmentsController implements Initializable {
    Stage stage;
    Parent scene;

    /**Private tableview for appointments. */

    @FXML
    private TableView<Appointment> appointmentsTable;

    /**Private column for appointment ID. */

    @FXML
    private TableColumn<Appointment, Integer> appointmentID;

    /**Private column for appointment title. */

    @FXML
    private TableColumn<Appointment, String> title;

    /**Private column for appointment description. */

    @FXML
    private TableColumn<Appointment, String> Description;

    /**Private column for appointment location. */

    @FXML
    private TableColumn<Appointment, String> location;

    /**Private column for appointment contact. */

    @FXML
    private TableColumn<Appointment, Integer> contact;

    /**Private column for appointment type. */

    @FXML
    private TableColumn<Appointment, String> type;

    /**Private column for appointment start date-time. */

    @FXML
    private TableColumn<Appointment, LocalDateTime> startDateTime;

    /**Private column for appointment end date-time. */

    @FXML
    private TableColumn<Appointment, LocalDateTime> endDateTime;

    /**Private column for appointment customer ID. */

    @FXML
    private TableColumn<Appointment, Integer> customerID;

    /**Private toggle group appointment. */

    @FXML
    private ToggleGroup appointment;

    /**Private radio button for appointment by month. */

    @FXML
    private RadioButton monthRadio;

    /**Private radio button for appointment by week. */

    @FXML
    private RadioButton weekRadio;

    /**Private radio button for all appointments. */

    @FXML
    private RadioButton allAppointmentRadio;

    /**This method populates the appointment tableview with all appointments. */

    @FXML
    void onActionAll(ActionEvent event) {

        appointmentListType();

    }

    /**This method populates the tableview with appointments or the current week. */

    @FXML
    void onActionWeek(ActionEvent event) {

        appointmentListType();

    }

    /**This method populates the tableview with appointments for the current month. */

    @FXML
    void onActionMonth(ActionEvent event) {
        appointmentListType();
    }



    int appointmentToUpdateIndex = -1;

    /**This method populates the tableview  with appointments based on the choice of all, by week, or by month radio button.*/


    void appointmentListType(){
        if(this.appointment.getSelectedToggle().equals(this.monthRadio)){

            appointmentsTable.setItems(Appointment.getAppointmentMonth());
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }

        else if(this.appointment.getSelectedToggle().equals(this.weekRadio)){

            appointmentsTable.setItems(Appointment.getAppointmentWeek());
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }

        else{
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            Description.setCellValueFactory(new PropertyValueFactory<>("description"));
            location.setCellValueFactory(new PropertyValueFactory<>("location"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        }



    }



    @FXML
    void onActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method accesses the add appointment screen in the application. */

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method accesses the update appointment screen and populates the screen with existing appointment data. */

    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {

        Appointment appointmentIndex = appointmentsTable.getSelectionModel().getSelectedItem();
        appointmentToUpdateIndex = DBCustomer.getAllCustomers().indexOf(appointmentIndex);

        if(appointmentsTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Appointment not selected");
            alert.setHeaderText("Please choose an appointment to update");
            alert.showAndWait();
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.FXML"));
            Parent scene = loader.load();
            UpdateAppointmentController UPController = loader.getController();
            UPController.sendAppointment(appointmentsTable.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            //scene = FXMLLoader.load(getClass().getResource("/view/UpdateAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }

    /**This method deletes a selected appointment from the appointment list. */

    @FXML
    void onActionDelete(ActionEvent event) throws IOException {

        Appointment toDelete = appointmentsTable.getSelectionModel().getSelectedItem();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete appointment ID# " + toDelete.getAppointmentID() + " Title: " + toDelete.getTitle()
                + " Type: " + toDelete.getType());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            DBAppointments.deleteAppointment(toDelete.getAppointmentID());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**This method is the first called when the appointments screen is accessed in the application.
     The method pre-populates the data in the appointment screen.
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentsTable.setItems(DBAppointments.getAllAppointments());
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTime.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endDateTime.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }
}
