package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**This class accesses and modifies appointments in the database associated with the application. */

public class DBAppointments {

    /**This method uses a SQL query and retrieves all appointments in the database.
     The method gets a list of all appointments in the database.
     @return returns an observable list of all appointments
     */

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int appointmentID = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime start = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userID = resultSet.getInt("User_ID");
                int contactID = resultSet.getInt("Contact_ID");

                Appointment a = new Appointment(appointmentID, title, description, location, type, start, end, customerID, contactID, userID);
                AppointmentList.add(a);


            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AppointmentList;

    }

        /**This method creates a customer record in the database associated with the application,
         The method takes usr input and inserts the data into the database using a SQL statement,
         @param title Title of the appointment.
         @param description Description of the appointment
         @param location The location of the appointment
         @param type The type of appointment
         @param start The start date and time of the appointment
         @param end The end date and time of the appointment
         @param customerID The customer ID of the appointment
         @param userID The userID of the appointment
         @param contact The contact of the appointment
         */
        public static void createAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contact) {

        String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), 'KJ', NOW(), 'KJ', ?, ?, ?)";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contact);


            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**This method modifies and existing appointment in the database.
     The method uses a SQL statement to modify an appointment whose appointment ID matches the appointment ID passed in
     @param appointmentID The appointment Id of the appointment to be modified
     @param title Title of the appointment.
     @param description Description of the appointment
     @param location The location of the appointment
     @param type The type of appointment
     @param start The start date and time of the appointment
     @param end The end date and time of the appointment
     @param customerID The customer ID of the appointment
     @param userID The userID of the appointment
     @param contact The contact of the appointment*/

    public static void modifyAppointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contact) {

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = 'KJ', Customer_ID = ?," +
                "User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contact);
            ps.setInt(10, appointmentID);


            ps.execute();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*public static String appointmentCheck(String userName, LocalDateTime time){

        String sql = "SELECT * FROM appointments WHERE User_Name = ? AND start < ";

        try{
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.execute();
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

     */


    /**This method uses a SQL statement to delete an appointment.
     The method deletes an appointment record in the database whose appointment ID matches the ID passed into the method.
     @param appointmentID the ID of the appointment to be deleted
     */
    
    public static void deleteAppointment(int appointmentID){
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);

            ps.execute();
        }
        catch (SQLException throwables) {

            throwables.printStackTrace();
        }
    }
}
