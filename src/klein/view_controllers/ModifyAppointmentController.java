package klein.view_controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.UserObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startDate;
    public ComboBox startTime;
    public DatePicker endDate;
    public ComboBox endTime;
    public ObservableList<String> customerList;
    public ComboBox<String> customerIDField;
    public ObservableList<String> userList;
    public ComboBox<String> userIDField;
    public ObservableList<String> contactList;
    public ComboBox<String> contactIDField;
    private AppointmentObj selectedAppointment;
    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createUser;
    private LocalDateTime updateDate;
    private String updateUser;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentsController.getSelectedAppointment();

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
        customerIDField.setItems(customerList);
        userIDField.setItems(userList);
    }

    public void modifyAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        appointmentID = Integer.parseInt(appointmentIDField.getText());
        title = titleField.getText();
        description = descriptionField.getText();
        location = locationField.getText();
        type = typeField.getText();
        start = selectedAppointment.getStart(); //TEMP -- W/ CB
        end = selectedAppointment.getEnd(); //TEMP -- W/ CB
        createDate = selectedAppointment.getCreateDate();
        createUser =selectedAppointment.getCreatedBy();
        updateDate = LocalDateTime.now();
        updateUser = UserObj.getUserName();
        customerID = AppointmentDB.customerNameToCustomerID(customerIDField.getValue());
        userID = AppointmentDB.userNameToUserID(userIDField.getValue());
        contactID = AppointmentDB.contactNameToContactID(contactIDField.getValue());

        AppointmentObj newAppt = new AppointmentObj(appointmentID, title, description, location, type, start, end, createDate, createUser, updateDate, updateUser, customerID, userID, contactID);

        AppointmentDB.deleteAppointment(appointmentID);
        AppointmentDB.addAppointment(newAppt);

        returnToAppointments(actionEvent);
        return;
    }

    public void returnToAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }
}
