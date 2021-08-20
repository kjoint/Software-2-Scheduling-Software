package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
import DBAccess.DBCustomer;
import DBAccess.DBUsers;
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

/**This class controls the update appointment controller*/

public class UpdateAppointmentController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField appointmentID;

    @FXML
    private TextField titleTXT;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private ComboBox<Customer> customerIDCombo;

    @FXML
    private ComboBox<Users> userIDCombo;

    @FXML
    private DatePicker startDateCal;

    @FXML
    private DatePicker endDateCal;

    @FXML
    private ComboBox<LocalTime> endTimeCombo;

    @FXML
    private ComboBox<LocalTime> startTimeCombo;

    @FXML
    private ComboBox<Contacts> contactCombo;

    private String errorMessage = new String();

    /**This method returns to the main appointment screen without saving any updates to the appointment. */

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**This method saves an appointment from the user input and generates a message if there is an error in the user input. */

    @FXML
    void onActionSave(ActionEvent event) {
        int appointmentID = Integer.parseInt(this.appointmentID.getText());
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
            errorMessage = Appointment.appointmentCheck(type, startDateTime, endDateTime, customerID, userID, contactID, appointmentID);
            if (errorMessage != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Error");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {
                DBAppointments.modifyAppointment(appointmentID, title, description, location, type, startDateTime, endDateTime, customerID.getID(), userID.getUserID(), contactID.getId() );


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
        }



    }

    /**This message retrieves the existing appointment data and populates the update screen,
     The method takes an appointment as a parameter and populates the record in the update appointment screen.
     @param appointment The appointment selected in the tableview
     */
    public void sendAppointment(Appointment appointment) {

        appointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        titleTXT.setText(appointment.getTitle());
        descriptionTxt.setText(appointment.getDescription());
        locationTxt.setText(appointment.getLocation());
        for(Contacts c : contactCombo.getItems()){
            if(c.getId() == appointment.getContactID()){
                contactCombo.setValue(c);
                break;
            }
        }
        typeCombo.setValue(appointment.getType());
        LocalDate startDate = appointment.getStart().toLocalDate();
        LocalTime startTime = appointment.getStart().toLocalTime();
        startDateCal.setValue(startDate);
        startTimeCombo.getSelectionModel().select(startTime);
        LocalDate endDate = appointment.getEnd().toLocalDate();
        LocalTime endTime = appointment.getEnd().toLocalTime();
        endDateCal.setValue(endDate);
        endTimeCombo.getSelectionModel().select(endTime);
        for(Customer c : customerIDCombo.getItems()){
            if(c.getID() == appointment.getCustomerID()){
                customerIDCombo.setValue(c);
                break;
            }
        }
        for(Users u : userIDCombo.getItems()){
            if(u.getUserID() == appointment.getUserID()){
                userIDCombo.setValue(u);
                break;
            }
        }

    }

    /**This method initializes the update appointment screen and populates the screen with data. */
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
        //startTimeCombo.getSelectionModel().select(LocalTime.of(8, 0));

        LocalTime selectedStart = startTimeCombo.getSelectionModel().getSelectedItem();

        /*
        while(end.isAfter(selectedStart) && end.isBefore(end.plusSeconds(1))){
            endTimeCombo.getItems().add(selectedStart);
            selectedStart = selectedStart.plusMinutes(15);
        }

         */




        startDateCal.setValue(LocalDate.now());
        endDateCal.setValue(startDateCal.getValue());


    }
}
