package model;
/**This Class creates a Country object. */
public class Countries {
    private int ID;
    private String Name;

    /**This method is the constructor for a country object.
     The method is called when creating a Country object.
     @param ID The ID of the country
     @param Name The name of the customer

     */

    public Countries(int ID, String Name){
        this.ID = ID;
        this.Name = Name;
    }

    /**This method retrieves the ID of a customer.
     The method gets the country ID.
     @return This method returns the country ID
     */

    public int getID(){

        return ID;
    }

    /**This method retrieves the country name.
     The method gets the country name.
     @return This method returns the country name
     */

    public String getName(){
        return Name;
    }



    /**This Method takes a string and returns a string. 
     @return Returns a string
     */

    @Override
    public String toString(){

        return Name;
    }

}
