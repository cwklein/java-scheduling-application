package klein.helper_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.util.Date;

public class AppointmentObj {
    public static LocalDateTime openingDateTime = TimeConverter.getLocalOpenDateTime();
    public static LocalDateTime closingDateTime = TimeConverter.getLocalCloseDateTime();

    private int appointmentID;
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
    private int customerID;
    private int userID;
    private int contactID;

    public AppointmentObj(Integer appointmentID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, LocalDateTime createDate, String createdBy, LocalDateTime updateDate, String updatedBy, int customerID, int userID, int contactID) {
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

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

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

    public static ObservableList<Integer> getStartMinutes() {
        return FXCollections.observableArrayList(0, 15, 30, 45);
    }
}
