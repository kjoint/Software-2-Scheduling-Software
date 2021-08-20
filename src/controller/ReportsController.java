package controller;

import DBAccess.DBAppointments;
import DBAccess.DBContacts;
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
import javafx.stage.Stage;
import model.Appointment;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**This class controls the reports controller of the application. */
public class ReportsController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Appointment> contactAppointment;

    @FXML
    private TableColumn<Appointment, Integer> appointmentID;

    @FXML
    private TableColumn<Appointment, String> title;

    @FXML
    private TableColumn<Appointment, String> type;

    @FXML
    private TableColumn<Appointment, String> description;

    @FXML
    private TableColumn<Appointment, LocalDateTime> start;

    @FXML
    private TableColumn<Appointment, LocalDateTime> end;

    @FXML
    private TableColumn<Appointment, Integer> customerID;

    @FXML
    private ComboBox<Contacts> contactCombo;

    /**This method populates the tableview with the selected contact's schedule. */

    @FXML
    void onActionGetSchedule(ActionEvent event) {
        Contacts c = contactCombo.getValue();
        contactAppointment.setItems(Appointment.getContactAppointments(c));
        appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

    }

    /**This method accesses the main screen of the application. */

    @FXML
    void onActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method initializes the report screen and populates the contact combo box. */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(DBContacts.getAllContacts());

    }
}