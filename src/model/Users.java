package model;

/**This class creates a Users object*/

public class Users {

    private int userID;
    private String userName;
    private String password;

    /**This is the Users constructor.
     The method constructs a user.
     @param userID The user ID
     @param userName The user name
     @param password The user password
     */

    public Users(int userID, String userName, String password){
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    /**This method retrieves the user ID.
     The method gets the user ID.
     @return Returns the user ID
     */

    public int getUserID(){

        return userID;
    }

    /**This method retrieves the user name.
     The method gets the user name.
     @return Returns the user name
     */

    public String getUserName(){

        return userName;
    }

    /**This method retrieves the user password.
     The method gets the user password.
     @return Returns the user password
     */

    public String getPassword(){

        return password;
    }


    /**This Method takes a string and returns a string
     @return Returns a string
     */
    
    @Override
    public String toString(){

        return ("ID# " + Integer.toString(userID) + ", " + userName);
    }
}
