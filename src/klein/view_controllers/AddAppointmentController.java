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
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.UserObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    public TextField appointmentIDField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public TextField typeField;
    public DatePicker startDateField;
    public ComboBox startTimeField;
    public DatePicker endDateField;
    public ComboBox endTimeField;
    public TextField customerIDField;
    public TextField userIDField;
    public ComboBox contactField;
    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appointmentIDField.setText(String.valueOf(AppointmentDB.nextOpenAppointment()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addAppointment(ActionEvent actionEvent) throws SQLException, IOException {
        appointmentID = Integer.parseInt(appointmentIDField.getText());
        title = titleField.getText();
        description = descriptionField.getText();
        location = locationField.getText();
        type = typeField.getText();
        start = LocalDateTime.now(); //TEMP -- W/ CB
        end = LocalDateTime.now(); //TEMP -- W/ CB
        createDate = LocalDateTime.now();
        createdBy = UserObj.getUserName();
        updateDate = LocalDateTime.now();
        updatedBy = UserObj.getUserName();
        customerID = Integer.parseInt(customerIDField.getText());
        userID = Integer.parseInt(userIDField.getText());
        contactID = userID; // TEMP -- W/ CB

        AppointmentObj newAppointment = new AppointmentObj(appointmentID, title, description, location, type, start, end, createDate, createdBy, updateDate, updatedBy, customerID, userID, contactID);

        AppointmentDB.addAppointment(newAppointment);

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

