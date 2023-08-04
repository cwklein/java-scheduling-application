package klein.view_controllers;

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
import klein.helper_controllers.DAO.UserDB;
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
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactCB;
    private AppointmentObj selectedAppointment;
    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private DatePicker startDate;
    private ComboBox startTime;
    private DatePicker endDate;
    private ComboBox endTime;
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

        appointmentIDField.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        titleField.setText(String.valueOf(selectedAppointment.getTitle()));
        descriptionField.setText(String.valueOf(selectedAppointment.getDescription()));
        locationField.setText(String.valueOf(selectedAppointment.getLocation()));
        typeField.setText(String.valueOf(selectedAppointment.getType()));
        customerIDField.setText(String.valueOf(selectedAppointment.getCustomerID()));
        userIDField.setText(String.valueOf(selectedAppointment.getUserID()));
        //POPULATE CB'S AND DATE PICKER
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
        createUser =selectedAppointment.getCreateUser();
        updateDate = LocalDateTime.now();
        updateUser = UserObj.getUserName();
        customerID = Integer.parseInt(customerIDField.getText());
        userID = Integer.parseInt(userIDField.getText());
        contactID = userID; // TEMP -- W/ CB

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
