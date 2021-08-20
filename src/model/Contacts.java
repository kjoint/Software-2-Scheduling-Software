package model;
/**This class creates a contact object.*/
public class Contacts {

    private int contactID;
    private String contactName;
    private String contactEmail;


    /**This method constructs a Contact object.
     The method constructs a Contact object from the input parameters.

     @param contactID ID number of the contact
     @param contactName Name of the contact
     @param contactEmail of the contact

     */

    public Contacts(int contactID, String contactName, String contactEmail){

        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**This method retrieves the ID of the contact.
     This method gets the contact's ID number
     @return contact ID
     */

    public int getId(){
        return contactID;
    }
    /**This method retrieves the name of the contact.
     This method gets the contact's name
     @return contact name
     */

    public String getContactName(){
        return contactName;
    }

    /**This method retrieves the email of the contact.
     This method gets the contact's email
     @return contact email
     */

    public String getContactEmail(){
        return contactEmail;
    }

    /**Converts a hexadecimal string to a user friendly string.
     The method takes a hexadecimal string and returns a user readable string
     @return a string
     */

    @Override
    public String toString(){

        return ("ID# " + Integer.toString(contactID) + ", " + contactName);
    }

}
