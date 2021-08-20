package DBAccess;

import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**This class accesses the customers in the database associated with the application. */
public class DBCountries {

    /**This method accesses the countries in the database using a SQL query.
     The method uses a sql query to access the database associated with the application and returns a list of countries.
     @return Returns an observable list of countries
     */

    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                Countries c = new Countries(countryID, countryName);
                countryList.add(c);

            }


        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countryList;
    }


}
