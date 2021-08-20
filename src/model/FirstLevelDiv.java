package model;
/**This class creates a Division (region/state) object.*/
public class FirstLevelDiv {

    private int ID;
    private String Name;

    /**This is the first level division constructor.
     The method creates a first level division object.
     @param ID The first level division ID
     @param Name The fist level division name
     */

    public FirstLevelDiv(int ID, String Name){
        this.ID = ID;
        this.Name = Name;
    }

    /**This method retrieves the first level division's ID.
     The method gets the first level division's ID.
     @return Returns the first level division's ID
     */

    public int getID(){
        return ID;
    }

    /**This method retrieves the first level division's name.
     The method gets the first level division's name.
     @return Returns the first level division's name
     */

    public String getName(){

        return Name;
    }

    /**This Method takes a string and returns a string
     @return Returns a string
     */

    @Override
    public String toString(){

        return Name;
    }
}
