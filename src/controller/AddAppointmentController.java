package controller;

import DBAccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**This class controls the add appointment screen for the application. */

public class AddAppointmentController implements Initializable {

    Stage stage;
    Parent scene;
    /**Private field for appointment ID. */
    @FXML
    private TextField appointmentID;

    /**Private field for the appointment title. */

    @FXML
    private TextField titleTXT;

    /**Private field for the appointment description. */

    @FXML
    private TextArea descriptionTxt;

    /**Private field for the appointment location. */

    @FXML
    private TextField locationTxt;

    /**Private field for the appointment type. */

    @FXML
    private ComboBox<String> typeCombo;

    /**Private field for the appointment's customer ID.*/

    @FXML
    private ComboBox<Customer> customerIDCombo;

    /**Private field for the appointment's user ID. */

    @FXML
    private ComboBox<Users> userIDCombo;

    /**Private field for the appointment's start date. */

    @FXML
    private DatePicker startDateCal;

    /**Private field for the appointment's end date. */

    @FXML
    private DatePicker endDateCal;

    /**Private field for the appointment's end time. */

    @FXML
    private ComboBox<LocalTime> endTimeCombo;

    /**Private field for the appointment's start time. */

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    /**Private field for the appointment's contact ID. */

    @FXML
    private ComboBox<Contacts> contactCombo;

    private String errorMessage = new String();

    /**This method exits the add appointment screen without saving an data.
     The method returns to the main appointment screen without saving any data.*/

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**This method saves the data entered in the add appointment screen as an appointment object.
     The method validates the data entered and returns an error message if the data is not valid. If valid, the appointment is saved.
     */


    @FXML
    void onActionSave(ActionEvent event) {
        String title = this.titleTXT.getText();
        String description = this.descriptionTxt.getText();
        String location = this.locationTxt.getText();
        Contacts contactID = this.contactCombo.getValue();
        String type = this.typeCombo.getValue();
        LocalDate startDate = this.startDateCal.getValue();
        LocalTime startTime = this.startTimeCombo.getValue();
        LocalDate endDate = this.endDateCal.getValue();
        LocalTime endTime = this.endTimeCombo.getValue();
        Customer customerID = this.customerIDCombo.getValue();
        Users userID = this.userIDCombo.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);


        //validate the data
        try {
            errorMessage = Appointment.appointmentCheck(type, startDateTime, endDateTime, customerID, userID, contactID, 0 );
            if (errorMessage != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                DBAppointments.createAppointment(title, description, location, type, startDateTime, endDateTime, customerID.getID(), userID.getUserID(), contactID.getId() );


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error saving the appointment");
            alert.setContentText("Cannot leave blank fields ");
            alert.showAndWait();
            e.getCause();
            e.printStackTrace();

        }



    }

    /**This method initializes the prefilled data in the add appointment screen. */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCombo.setItems(Appointment.getTypeList());
        typeCombo.getSelectionModel().selectFirst();
        userIDCombo.setItems(DBUsers.getAllUsers());
        userIDCombo.getSelectionModel().selectFirst();
        contactCombo.setItems(DBContacts.getAllContacts());
        contactCombo.getSelectionModel().selectFirst();
        customerIDCombo.setItems(DBCustomer.getAllCustomers());
        customerIDCombo.getSelectionModel().selectFirst();

        LocalTime start = LocalTime.of(0, 15);
        LocalTime end = LocalTime.of(23,45);

        while(start.isBefore(end)){
            startTimeCombo.getItems().add(start);
            endTimeCombo.getItems().add(start);
            start = start.plusMinutes(15);
        }
        startTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));
        endTimeCombo.getSelectionModel().select(LocalTime.of(22, 0));
        startDateCal.setValue(LocalDate.now());
        endDateCal.setValue(LocalDate.now());

        LocalTime selectedStart = startTimeCombo.getSelectionModel().getSelectedItem();

        startDateCal.setValue(LocalDate.now());
        endDateCal.setValue(startDateCal.getValue());


    }
}
