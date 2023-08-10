package klein.view_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.TimeConverter;
import klein.helper_controllers.UserObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker appointmentDateField;
    public ComboBox<Integer> startHrField;
    public ComboBox<Integer> startMinField;
    public ComboBox<Integer> durationHrField;
    public ComboBox<Integer> durationMinField;
    public ObservableList<String> customerList;
    public ComboBox<String> customerIDField;
    public ObservableList<String> userList;
    public ComboBox<String> userIDField;
    public ObservableList<String> contactList;
    public ComboBox<String> contactIDField;
    private AppointmentObj selectedAppointment;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentsController.getSelectedAppointment();

        LocalDateTime duration = selectedAppointment.getEnd()
                .minusHours(selectedAppointment.getStart().getHour())
                .minusMinutes(selectedAppointment.getStart().getMinute());
        Integer durationHr = duration.getHour();
        Integer durationMin = duration.getMinute();

        try {
            contactList = AppointmentDB.getContacts();
            userList = AppointmentDB.getUsers();
            customerList = AppointmentDB.getCustomers();
            contactIDField.setValue(AppointmentDB.contactIDToContactName(selectedAppointment.getContactID()));
            customerIDField.setValue(AppointmentDB.customerIDToCustomerName(selectedAppointment.getCustomerID()));
            userIDField.setValue(AppointmentDB.userIDToUserName(selectedAppointment.getUserID()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        appointmentIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleField.setText(String.valueOf(selectedAppointment.getTitle()));
        descriptionField.setText(String.valueOf(selectedAppointment.getDescription()));
        locationField.setText(String.valueOf(selectedAppointment.getLocation()));
        contactIDField.setItems(contactList);
        typeField.setText(String.valueOf(selectedAppointment.getType()));
        appointmentDateField.setValue(selectedAppointment.getStart().toLocalDate());
        startHrField.setItems(AppointmentObj.getStartHours());
        startHrField.setValue(selectedAppointment.getStart().getHour());
        findStartMinutes();
        startMinField.setValue(selectedAppointment.getStart().getMinute());
        findDurationHours();
        durationHrField.setValue(durationHr);
        findDurationMinutes();
        durationMinField.setValue(durationMin);
        customerIDField.setItems(customerList);
        userIDField.setItems(userList);
    }

    public void populateStartMinutes(ActionEvent actionEvent) {
        findStartMinutes();
    }

    private void findStartMinutes() {
        startMinField.setItems(null);
        startMinField.setValue(null);
        durationHrField.setItems(null);
        durationHrField.setValue(null);
        durationMinField.setItems(null);
        durationMinField.setValue(null);
        startMinField.setItems(AppointmentObj.getStartMinutes());
    }

    public void populateDurationHours(ActionEvent actionEvent) {
        findDurationHours();
    }

    private void findDurationHours() {
        durationHrField.setItems(null);
        durationHrField.setValue(null);
        durationHrField.getPromptText();
        durationMinField.setItems(null);
        durationMinField.setValue(null);

        if (startHrField.getValue() != null && startMinField.getValue() != null) {
            LocalTime timeLeft = TimeConverter.getLocalCloseTime().minusHours(startHrField.getValue()).minusMinutes(startMinField.getValue());

            ObservableList<Integer> hoursLeft = FXCollections.observableArrayList();
            for (int h = 0; h <= timeLeft.getHour(); h++) {
                hoursLeft.add(h);
            }
            durationHrField.setItems(hoursLeft);
        }
    }

    public void populateDurationMinutes(ActionEvent actionEvent) {
        findDurationMinutes();
    }

    private void findDurationMinutes() {
        durationMinField.setItems(null);
        durationMinField.setValue(null);

        if (startHrField.getValue() != null && durationHrField.getValue() != null && startMinField.getValue() != null) {
            LocalTime timeLeft = TimeConverter.getLocalCloseTime().minusHours(startHrField.getValue() + durationHrField.getValue()).minusMinutes(startMinField.getValue());

            ObservableList<Integer> minutesLeft = FXCollections.observableArrayList();
            if (durationHrField.getValue() != 0) {
                minutesLeft.add(0);
            }
            if (timeLeft.getHour() == 0) {
                for (int m = 1; m <= timeLeft.getMinute(); m++) {
                    if (m % 15 == 0) {
                        minutesLeft.add(m);
                    }
                }
            } else {
                minutesLeft.addAll(15, 30, 45);
            }
            durationMinField.setItems(minutesLeft);
        }
    }

    public void modifyAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        if (titleField.getText().isBlank() || descriptionField.getText().isBlank() ||
                locationField.getText().isBlank() || typeField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Missing Field");
            alert.setContentText("You are missing one or more required fields\nAll fields must be filled out to continue.");
            alert.showAndWait();
        } else {
            try {
                LocalTime startTime = LocalTime.of(startHrField.getValue(), startMinField.getValue());

                Integer appointmentID = Integer.parseInt(appointmentIDField.getText());
                String title = titleField.getText();
                String description = descriptionField.getText();
                String location = locationField.getText();
                String type = typeField.getText();
                LocalDateTime start = LocalDateTime.of(appointmentDateField.getValue(), startTime);
                LocalDateTime end = start.plusHours(durationHrField.getValue()).plusMinutes(durationMinField.getValue());
                LocalDateTime createDate = selectedAppointment.getCreateDate();
                String createUser = selectedAppointment.getCreatedBy();
                LocalDateTime updateDate = LocalDateTime.now();
                String updateUser = UserObj.getUserName();
                Integer customerID = AppointmentDB.customerNameToCustomerID(customerIDField.getValue());
                Integer userID = AppointmentDB.userNameToUserID(userIDField.getValue());
                Integer contactID = AppointmentDB.contactNameToContactID(contactIDField.getValue());

                AppointmentObj newAppt = new AppointmentObj(appointmentID, title, description, location, type, start, end, createDate, createUser, updateDate, updateUser, customerID, userID, contactID);

                AppointmentDB.deleteAppointment(appointmentID);
                AppointmentDB.addAppointment(newAppt);

                returnToAppointments(actionEvent);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error - Missing Field");
                alert.setContentText("You are missing one or more required fields\nAll fields must be filled out to continue.");
                alert.showAndWait();
            }
        }
    }

    public void returnToAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
