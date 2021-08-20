package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**This class connectes to the database. */

public class DBConnection {


    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06KNz";


    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U06KNz";
    private static Connection conn = null;

    /**This method gets the initial connection to the database.
     the method gets a connection and returns the connection.
     @return Returns the connection
     */

    public static Connection startConnection(){

        try{
            Class.forName(MYSQLJBCDriver);
           conn = DriverManager.getConnection(jdbcURL, username, "53688789309");
            System.out.println("Connection successful");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch ( ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }

    /**This method gets the connection for use in the application.
     The method gets the connection and returns the connection.
     @return Returns the connection
     */

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection(){
        try{
            conn.close();

        }
        catch(Exception e){

        }
    }
}

