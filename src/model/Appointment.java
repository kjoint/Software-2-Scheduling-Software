package model;

import DBAccess.DBAppointments;
import DBAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

/**This class creates an Appointment object, checks for valid data and returns sub lists of Appointment objects */

public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerID;
    private int userID;
    private int contactID;



    /**This is the Appointment object constructor.
     This is the method that gets called when creating an Appointment object.
     @param appointmentID The ID number of the appointment
     @param title The title of the appointment
     @param description The description of the appointment
     @param location The location of the appointment
     @param type The type of appointment
     @param start The start date and time of the appointment
     @param end The end date and time of the appointment
     @param customerID The ID of the customer
     @param userID The user associated with the appointment
     @param contactID The contact associated with the appointment
     */

    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerID, int userID, int contactID){

        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;


    }

    /**This method gets the Appointment's ID.
     This method is called when retrieving the Appointment's ID.
     @return returns Appointment's ID
     */

    public int getAppointmentID(){
        return appointmentID;
    }

    /**This method gets the Appointment's title.
     This method is called when retrieving the Appointment's title.
     @return returns Appointment's title
     */

    public String getTitle(){
        return title;
    }

    /**This method gets the Appointment's description.
     This method is called when retrieving the Appointment's description.
     @return returns Appointment's description
     */

    public String getDescription(){
        return description;
    }

    /**This method gets the Appointment's location.
     This method is called when retrieving the Appointment's location.
     @return returns Appointment's location
     */

    public String getLocation(){

        return location;
    }

    /**This method gets the Appointment's type.
     This method is called when retrieving the Appointment's type.
     @return returns Appointment's type
     */

    public String getType(){

        return type;
    }

    /**This method gets the Appointment's start date and time.
     This method is called when retrieving the Appointment's start date and time.
     @return returns Appointment's start date-time
     */

    public LocalDateTime getStart(){

        return start;
    }

    /**This method gets the Appointment's end date and time.
     This method is called when retrieving the Appointment's end date and time.
     @return returns Appointment's end date-time
     */

    public LocalDateTime getEnd(){

        return end;
    }

    /**This method gets the Appointment's contact ID.
     This method is called when retrieving the Appointment's contact ID .
     @return returns Appointment's contact ID
     */

    public  int getContactID(){

        return contactID;
    }

    /**This method gets the Appointment's customer ID.
     This method is called when retrieving the Appointment's customer ID .
     @return returns Appointment's customer ID
     */

    public int getCustomerID(){

        return customerID;
    }

    /**This method gets the Appointment's user ID.
     This method is called when retrieving the Appointment's user ID .
     @return returns Appointment's user ID
     */

    public int getUserID(){

        return userID;
    }


    /**This method retrieves a list of appointment types.
     The method returns a list of each type of appointment available.
     @return returns list of appointment types

     */

  public static ObservableList<String> getTypeList(){
        ObservableList<String> appointmentType = FXCollections.observableArrayList("Planning Session", "De-Briefing", "test", "test1");

        return appointmentType;
  }

  /**This Method retrieves a list of appointments with the same contact ID using a lambda expression.
    The method compares the contact ID passed in and returns a list of appointments using a lambda expression to filter all appointments with the same contact ID. The lambda expression
   is used to reduce the amount of code inside the method body.
   @param contact Selected contact for report
   @return returns a list of all appointments with the same contact ID as the parameter passed in
   */


  public static ObservableList<Appointment> getContactAppointments(Contacts contact){
      ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();

      //Lambda expression 1
      DBAppointments.getAllAppointments().forEach((appointment) -> {
          if(appointment.getContactID() == contact.getId()) contactAppointments.add(appointment);
      });
        //ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        //for(Appointment a : DBAppointments.getAllAppointments()){
            //if(a.getContactID() == contact.getId()){
               // contactAppointments.add(a);
            //}
        //}
        return contactAppointments;
  }


    /**This method returns a list of appointments in the current month using a lambda expression.
     The Method determines the current month and filters a list, using a lambda expression, of all appointments in the current month. The lambda expression is used to reduce the
     amount of code inside the method body.
     @return returns a list of all appointments in the current month
     */

    public static ObservableList<Appointment> getAppointmentMonth(){
        ObservableList<Appointment> appointmentMonth = FXCollections.observableArrayList();
        //Lambda expression 2
        DBAppointments.getAllAppointments().forEach((appointment)->{
            if(appointment.getStart().getMonth() == LocalDateTime.now().getMonth() && appointment.getStart().getYear() == LocalDateTime.now().getYear())
                appointmentMonth.add(appointment);
        });
        //for(Appointment a : DBAppointments.getAllAppointments()){
            //if(a.getStart().getMonth() == LocalDateTime.now().getMonth() && a.getStart().getYear() == LocalDateTime.now().getYear()){
                //appointmentMonth.add(a);
            //}

        return appointmentMonth;
    }


    /**This method returns a list of appointments in the current week.
     The Method determines the current week and filters a list of all appointments in the current week.
     @return returns a list of all appointments in the current week
     */

    public static ObservableList<Appointment> getAppointmentWeek(){
        ObservableList<Appointment> appointmentWeek = FXCollections.observableArrayList();
        LocalDateTime  day = LocalDateTime.now();
        while(day.getDayOfWeek() != DayOfWeek.SUNDAY){
            day = day.minusDays(1);
        }
        for(Appointment a : DBAppointments.getAllAppointments()){
            if(a.getStart().isAfter(day.minusDays(1)) && a.getStart().isBefore(day.plusDays(7))){
                appointmentWeek.add(a);
            }


            /*
            if(a.getStart().isAfter(LocalDateTime.now().minusMinutes(1)) && a.getStart().isBefore(LocalDateTime.now().plusDays(7))){
                appointmentWeek.add(a);
            }
            */

        }
        return appointmentWeek;

    }

    /**This method gets a list of the months of the year.
     This method is called when retrieving a list of the months of the year.
     @return returns a list of the 12 months
     */

    public static ObservableList<String> getMonths(){
        ObservableList<String> months = FXCollections.observableArrayList("01: January", "02: February", "03: March", "04: April", "05: May", "06: June", "07: July", "08, August", "09: September", "10: October",
                "11: November", "12: December");

        return months;
    }

    /**This method gets a list of years .
     This method is called when retrieving window of years determined by the current year.
     @return returns a list of five years
     */

    public static ObservableList<Integer> getYears(){
        Year startYear = Year.now().minusYears(2);
        Year endYear = Year.now().plusYears(2);
        int startYearNum = startYear.getValue();
        int endYearNum = endYear.getValue();

        ObservableList<Integer> years = FXCollections.observableArrayList();
        while(startYearNum <= endYearNum){
            years.add(startYearNum);
            startYearNum = startYearNum + 1;
        }


        return years;
    }

    /**This method checks that an appointment is valid.
     The method takes inputs from an appointment and determines if the data is valid.
     @param type the type of appointment
     @param startDateTime the start date-time of the appointment
     @param endDateTime the end date-time of the appointment
     @param customerID the customer ID associated with the appointment
     @param userID the user ID associated with the appointment
     @param contactID the contact ID associated with the appointment
     @param appointmentID the appointments ID
     @return returns a string of errors, if no errors are found returns null


     */



    public static String appointmentCheck(String type, LocalDateTime startDateTime, LocalDateTime endDateTime, Customer customerID, Users userID, Contacts contactID, int appointmentID){

        if(contactID == null){
            return "A contact must be selected.";
        }
        if(type == null ){
            return "A type must be selected.";
        }
        if(startDateTime == null){
            return "Please select a start date and start time.";
        }
        if(endDateTime == null){
           return "Please select an end date and time";
        }

        if(endDateTime.isBefore(startDateTime) || endDateTime.isEqual(startDateTime)){
            return "Please select an end time that is after the selected start time";
        }
        if(customerID == null){
            return "Please select a customer\n";
        }
        if (userID == null){
            return "Please select an user\n";
        }
        LocalTime startEST = LocalTime.of(8,0);
        LocalTime endEST = LocalTime.of(22,0);
        //change start time from system default to est
        ZonedDateTime localStart = startDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime estStart = localStart.withZoneSameInstant(ZoneId.of("America/New_York"));
        if(estStart.toLocalTime().isBefore(startEST)){
            return "Appointment starts before business hours.";
        }
        ZonedDateTime localEnd = endDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime estEnd = localEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
        if(estEnd.toLocalTime().isAfter(endEST)){
            return "Appointment ends after business hours.";
        }

        ObservableList<Appointment> aList = DBAppointments.getAllAppointments();
        for(Appointment a : aList) {
            if (a.getAppointmentID() == appointmentID) {
                continue;
            }
            if (a.getCustomerID() != customerID.getID()) {
                continue;
            }
            if(startDateTime.toLocalDate().isEqual(a.getStart().toLocalDate())) {
                if ((startDateTime.toLocalTime().isAfter(a.getStart().toLocalTime()) || startDateTime.toLocalTime().equals(a.getStart().toLocalTime())) &&
                        startDateTime.toLocalTime().isBefore(a.getEnd().toLocalTime())) {
                    return "Appointment overlap error";
                }

                if (endDateTime.toLocalTime().isAfter(a.getStart().toLocalTime()) &&
                        (endDateTime.toLocalTime().isBefore(a.getEnd().toLocalTime())) || endDateTime.toLocalTime().equals(a.getEnd().toLocalTime())) {
                    return "Appointment overlap error";
                }
                if ((startDateTime.toLocalTime().isBefore(a.getStart().toLocalTime()) || startDateTime.toLocalTime().equals(a.getStart().toLocalTime()))
                        && (endDateTime.toLocalTime().isAfter(a.getEnd().toLocalTime())) || endDateTime.toLocalTime().equals(a.getEnd().toLocalTime())) {
                    return "Appointment overlap error";
                }
            }
        }
        return null;
    }
}
