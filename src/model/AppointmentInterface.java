package model;

import javafx.collections.ObservableList;

@FunctionalInterface
public interface AppointmentInterface {

    ObservableList<Appointment> getAppt(Appointment appointment);
}
