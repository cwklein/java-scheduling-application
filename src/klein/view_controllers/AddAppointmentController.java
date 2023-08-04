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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    public TextField appointmentID;
    public TextField title;
    public TextField description;
    public TextField locationInput;
    public TextField type;
    public TextField customerID;
    public TextField userID;
    public ComboBox contactCB;
    public DatePicker startDate;
    public ComboBox startTime;
    public DatePicker endDate;
    public ComboBox endTime;

    public void addAppointment(ActionEvent actionEvent) {
        String appointmentIDText = appointmentID.getText();
        String titleText = title.getText();
        String descriptionText = description.getText();
        String locationText = locationInput.getText();
        String typeText = type.getText();
        String customerIDText = customerID.getText();
        String userIDText = userID.getText();

    }

    public void returnToAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

