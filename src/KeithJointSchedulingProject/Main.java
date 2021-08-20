package KeithJointSchedulingProject;

import DBAccess.DBAppointments;
import Database.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {

        DBConnection.startConnection();
        int x = DBAppointments.getAllAppointments().size();
        System.out.println("Appointment count equals " + x);
        launch(args);
        DBConnection.closeConnection();
    }
}
