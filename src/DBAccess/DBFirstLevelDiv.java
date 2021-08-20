package DBAccess;


import Database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDiv;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**This class accesses the first level divisions in the database. */

public class DBFirstLevelDiv {

    /**This method retrieves all first level divisions in the data base.
     The method uses a SQL query to get the first level divisions in the database and return a list.
     @return Returns an observable list of first level divisions
     */

    public static ObservableList<FirstLevelDiv> getAllDivisions(){
        ObservableList<FirstLevelDiv> divisionList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                FirstLevelDiv d = new FirstLevelDiv(divisionID, divisionName);
                divisionList.add(d);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }

    /**This method retrieves a list of first level divisions associated with a country in the database.
     the method uses the country ID passed in and returns a list of all first level divisions associated with that country.
     @param countryID The country ID of the desired country
     @return Returns an observable list of first level divisions
     */

    public static ObservableList<FirstLevelDiv> getDivisions(int countryID){
        ObservableList<FirstLevelDiv> divisionList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, countryID);
            ResultSet resultSet = ps.executeQuery();
            while(resultSet.next()){
                int divisionID = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                FirstLevelDiv d = new FirstLevelDiv(divisionID, divisionName);
                divisionList.add(d);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return divisionList;
    }

}
