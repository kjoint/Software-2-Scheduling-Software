package controller;

import DBAccess.DBAppointments;
import DBAccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Users;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;


/**This class Controls the login screen in the application. */


public class LoginScreenController implements Initializable {

    Stage stage;
    Parent scene;
    ZoneId zone = ZoneId.systemDefault();
    ResourceBundle rb = ResourceBundle.getBundle("Language_Files/language", Locale.getDefault());

    @FXML
    private Label userLabel;

    @FXML
    private TextField userIDTxt;

    @FXML
    private Label passwordLabel;


    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label zoneTxt;

    @FXML
    private Button loginButton;



    /**This method initiates a user login and generates a message of the login failed. If the login succeeds, an appointment reminder is generated and the login attempt is appended to a .txt file. */


    @FXML
    void onActionLogin(ActionEvent event) throws IOException {

        String userName = userIDTxt.getText();
        String password = passwordTxt.getText();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter convert = DateTimeFormatter.ISO_DATE_TIME;
        String loginDateTime = dateTime.format(convert);

        Users u = DBUsers.validateUser(userName, password);
        boolean loginValid = false;

        if (u == null) {
            loginValid = false;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(rb.getString("Wrong"));
            alert.setContentText(rb.getString("Message"));
            alert.showAndWait();

            userIDTxt.setText("");
            passwordTxt.setText("");

        }

        else {


            loginValid = true;
            ObservableList <Appointment> allAppointments =  DBAppointments.getAllAppointments();
            boolean found = false;
            String appt = "";

            for(Appointment a : allAppointments){
                if(u.getUserID() == a.getUserID()){
                    if(a.getStart().isAfter(dateTime) && a.getStart().isBefore(dateTime.plusMinutes(15))){
                        found = true;
                        appt += (rb.getString("Some") + a.getAppointmentID() + ", " + a.getStart().toLocalDate() + ", " + a.getStart().toLocalTime() + "\n");
                    }

                }
            }
            if(found){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("Header"));
                alert.setContentText(appt);
                alert.showAndWait();
            }


            if(!found) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(rb.getString("Header"));
                alert.setContentText(rb.getString("None"));
                alert.showAndWait();
            }



            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


        String loginSuccess = "";
        if (loginValid == false) {
            loginSuccess = "Login failed";
        } else {
            loginSuccess = "login successful";

        }
        try (PrintWriter login = new PrintWriter(new FileOutputStream("login_activity.txt", true))) {

            login.append("Username: " + userName + "Login timestamp " + loginDateTime + " " + loginSuccess + "\n");
            login.flush();
            login.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**This method is the first called when the login screen is accessed in the application.

     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



                userLabel.setText(rb.getString("User"));
                //userIDTxt.setPromptText(rb.getString("Name"));
                passwordLabel.setText(rb.getString("Password"));
                //passwordTxt.setPromptText("Prompt");
                loginButton.setText(rb.getString("Login"));
                zoneTxt.setText(String.valueOf(zone));
    }




}



