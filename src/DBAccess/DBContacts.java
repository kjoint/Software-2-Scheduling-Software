package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class accesses contacts in the database associated with the application. */

public class DBContacts {

    /**This method gets a list of all the contacts in the database.
     The method access the database using a SQL query and returns a list of contacts.
     @return Returns an observable list of contacts
     */

    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> ContactList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int contactID = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                String contactEmail = resultSet.getString("Email");
                Contacts con = new Contacts(contactID, contactName, contactEmail);
                ContactList.add(con);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ContactList;

    }
}
