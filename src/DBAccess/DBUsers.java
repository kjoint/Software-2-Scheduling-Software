package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**This class accesses the users associated with the database for the application. */

public class DBUsers {

    /**This method retrieves a list of all user associated with the database in the application.
     The method accesses the database and gets a list of users with a SQL query.
     */

    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> UserList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                int userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Users u = new Users(userID, userName, password);
                UserList.add(u);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return UserList;

    }

    /**This method validates a users credential at login.
     The method queries the database and determines the validity of a user's credentials.
     @param userName The userName
     @param password The user's password
     @return returns user for valid login, returns null for invalid login
     */

    public static Users validateUser(String userName, String password){
        String sql = "SELECT * FROM users WHERE User_Name = ? and Password = ?";

        try{
            boolean loginValid = false;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()){
                int userID = resultSet.getInt("User_ID");
                String userNameS = resultSet.getString("User_Name");
                String passwordS = resultSet.getString("Password");
                Users u = new Users(userID, userNameS, passwordS);
                return u ;
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }


            return null;
    }


}
