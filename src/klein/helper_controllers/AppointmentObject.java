package klein.helper_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class AppointmentObject {
    public static LocalDateTime openingDateTime = TimeConverter.getLocalOpenDateTime();
    public static LocalDateTime closingDateTime = TimeConverter.getLocalCloseDateTime();

    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private final LocalDateTime createDate;
    private final String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    public AppointmentObject(Integer appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime updateDate, String updatedBy, Integer customerID, Integer userID, Integer contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.updateDate = updateDate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Basic getter function for the private attribute 'appointmentID'.
     * @return integer-type private attribute 'appointmentID'.
     * */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Basic getter function for the private attribute 'title'.
     * @return string-type private attribute 'title'.
     * */
    public String getTitle() {
        return title;
    }

    /**
     * Basic setter function for the private attribute 'title'.
     * @param title string to be assigned to private attribute 'title'.
     * */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Basic getter function for the private attribute 'description'.
     * @return string-type private attribute 'description'.
     * */
    public String getDescription() {
        return description;
    }

    /**
     * Basic setter function for the private attribute 'description'.
     * @param description string to be assigned to private attribute 'description'.
     * */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Basic getter function for the private attribute 'location'.
     * @return string-type private attribute 'location'.
     * */
    public String getLocation() {
        return location;
    }

    /**
     * Basic setter function for the private attribute 'location'.
     * @param location string to be assigned to private attribute 'location'.
     * */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Basic getter function for the private attribute 'type'.
     * @return string-type private attribute 'type'.
     * */
    public String getType() {
        return type;
    }

    /**
     * Basic setter function for the private attribute 'type'.
     * @param type string to be assigned to private attribute 'type'.
     * */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Basic getter function for the private attribute 'start'.
     * @return LocalDateTime-type private attribute 'start'.
     * */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Basic setter function for the private attribute 'start'.
     * @param start LocalDateTime to be assigned to private attribute 'start'.
     * */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Basic getter function for the private attribute 'end'.
     * @return LocalDateTime-type private attribute 'end'.
     * */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Basic setter function for the private attribute 'end'.
     * @param end LocalDateTime to be assigned to private attribute 'end'.
     * */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * Basic getter function for the private attribute 'createDate'.
     * @return LocalDateTime-type private attribute 'createDate'.
     * */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Basic getter function for the private attribute 'createdBy'.
     * @return String-type private attribute 'createdBy'.
     * */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Basic getter function for the private attribute 'updateDate'.
     * @return LocalDateTime-type private attribute 'updateDate'.
     * */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * Basic getter function for the private attribute 'updatedBy'.
     * @return String-type private attribute 'updatedBy'.
     * */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Basic getter function for the private attribute 'customerID'.
     * @return Integer-type private attribute 'customerID'.
     * */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Basic setter function for the private attribute 'customerID'.
     * @param customerID integer to be assigned to private attribute 'customerID'.
     * */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Basic getter function for the private attribute 'userID'.
     * @return Integer-type private attribute 'userID'.
     * */
    public int getUserID() {
        return userID;
    }

    /**
     * Basic setter function for the private attribute 'userID'.
     * @param userID integer to be assigned to private attribute 'userID'.
     * */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Basic getter function for the private attribute 'contactID'.
     * @return Integer-type private attribute 'contactID'.
     * */
    public int getContactID() {
        return contactID;
    }

    /**
     * Basic setter function for the private attribute 'contactID'.
     * @param contactID integer to be assigned to private attribute 'contactID'.
     * */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Returns an integer-type ObservableList of all hour-fields in which an appointment can be started, in local time.
     * Generates hours based on the opening-time in EST, converted to the local time. Has a built in functionality to accomodate hours if the user is accessing the database from somewhere with a time zone east of New York as well.
     *
     * @return an integer-type ObservableList of all hour-fields in which an appointment can be started, in local time.
     * */
    public static ObservableList<Integer> getStartHours() {
        ObservableList<Integer> startHours = FXCollections.observableArrayList();

        if (closingDateTime.getDayOfMonth() != openingDateTime.getDayOfMonth()) {
            for (int i = 0; i < closingDateTime.getHour(); i++){
                startHours.add(i);
            }
            for (int i = openingDateTime.getHour(); i < 24; i++) {
                startHours.add(i);
            }
        } else {
            for (int i = openingDateTime.getHour(); i < closingDateTime.getHour(); i++) {
                startHours.add(i);
            }
        }
        return startHours;
    }

    /**
     * Returns an integer-type ObservableList of all minute-fields in which an appointment can be started.
     * Limits users to setting appointments by increments of 15 minutes to ensure that the scheduling system is efficiently using time blocks.
     *
     * @return an integer-type ObservableList of all minute-fields in which an appointment can be started.
     * */
    public static ObservableList<Integer> getStartMinutes() {
        return FXCollections.observableArrayList(0, 15, 30, 45);
    }
}
