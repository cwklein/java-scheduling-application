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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
    private AppointmentObj selectedAppointment;
    public TextField appointmentID;
    public TextField title;
    public TextField description;
    public TextField location;
    public TextField type;
    public TextField customerID;
    public TextField userID;
    public ComboBox contactCB;
    public DatePicker startDate;
    public DatePicker endDate;
    public ComboBox startTime;
    public ComboBox endTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = AppointmentsController.getSelectedAppointment();

        appointmentID.setText(String.valueOf(selectedAppointment.getAppointmentID()));
        title.setText(String.valueOf(selectedAppointment.getTitle()));
        description.setText(String.valueOf(selectedAppointment.getDescription()));
        location.setText(String.valueOf(selectedAppointment.getLocation()));
        type.setText(String.valueOf(selectedAppointment.getType()));
        customerID.setText(String.valueOf(selectedAppointment.getCustomerID()));
        userID.setText(String.valueOf(selectedAppointment.getUserID()));
        //POPULATE CB'S AND DATE PICKER
    }

    public void modifyAppointment(ActionEvent actionEvent) {
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
