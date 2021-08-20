package controller;

import DBAccess.DBAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ValueRange;
import java.util.ResourceBundle;

/**This class controls the type report screen for the application. */

public class TypeReportsController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private Label planningTxt;

    @FXML
    private Label debriefTxt;

    @FXML
    private Label testTxt;

    @FXML
    private Label test1Txt;

    @FXML
    private ComboBox<Integer> yearCombo;

    @FXML
    private ComboBox<String> monthCombo;

    /**This method accesses the main screen of the application. */
    @FXML
    void onActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**This method generates a report that returns the number of appointments for each type and populates the screen with the data. */

    @FXML
    void onActionGetReport(ActionEvent event) throws ParseException {

        int reportYear = yearCombo.getValue();
        String month = monthCombo.getValue();
        int reportMonth = ((Number) NumberFormat.getInstance().parse(month)).intValue();
        String plan = "Planning Session";
        String debrief = "De-Briefing";
        String test = "test";
        String test1 = "test1";
        int planningCount = 0;
        int debriefCount = 0;
        int testCount = 0;
        int test1Count = 0;

        for (Appointment a : DBAppointments.getAllAppointments()) {
            int aptYear = a.getStart().getYear();
            int aptMonth = a.getStart().getMonthValue();
            System.out.println(aptYear);
            System.out.println(reportYear);
            System.out.println(a.getType());

            if(aptYear == reportYear && aptMonth == reportMonth && a.getType().equals(plan)){
                planningCount += 1;
            }
            if(aptYear == reportYear && aptMonth == reportMonth && a.getType().equals(debrief)){
                debriefCount += 1;
            }
            if(aptYear == reportYear && aptMonth == reportMonth && a.getType().equals(test)){
                testCount += 1;
            }
            if(aptYear == reportYear && aptMonth == reportMonth && a.getType().equals(test1)){
                test1Count += 1;
            }
        }
        planningTxt.setText(Integer.toString(planningCount) + " Appointments");
        debriefTxt.setText(Integer.toString(debriefCount) + " Appointments");
        testTxt.setText(Integer.toString(testCount) + " Appointments");
        test1Txt.setText(Integer.toString(test1Count) + "Appointments");

         planningCount = 0;
         debriefCount = 0;
         testCount = 0;
         test1Count = 0;
    }


    /**This method initializes the type report screen and populates the combo boxes for user selection. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        monthCombo.setItems(Appointment.getMonths());
        yearCombo.setItems(Appointment.getYears());

    }




}
