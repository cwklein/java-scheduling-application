package klein.view_controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.DAO.UserDB;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.UserObj;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField usernameText;
    public TextField passwordText;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button loginButton;
    public Button exitButton;
    public Label locationLabel;
    public Label locationText;
    public Label languageLabel;
    public Label languageText;
    public Label loginLabel;


    ResourceBundle rb = ResourceBundle.getBundle("klein/language", Locale.getDefault());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();

        locationText.setText(zone.toString());
        locationLabel.setText(rb.getString("location"));
        languageLabel.setText(rb.getString("language"));
        languageText.setText(Locale.getDefault().toString());
        exitButton.setText(rb.getString("exit"));
        loginButton.setText(rb.getString("login"));
        loginLabel.setText(rb.getString("login"));
        usernameLabel.setText(rb.getString("username"));
        passwordLabel.setText(rb.getString("password"));
    }

    public void attemptLogin(ActionEvent actionEvent) throws IOException, SQLException {
        if (usernameText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("errorTitle"));
            alert.setHeaderText(rb.getString("missingHeader"));
            alert.setContentText(rb.getString("missingText"));
            alert.showAndWait();
        } else {
            String usernameAttempt = usernameText.getText();
            String passwordAttempt = passwordText.getText();

            int userID = UserDB.ValidateUser(usernameAttempt, passwordAttempt);

            populateLoginRecord(usernameAttempt, passwordAttempt, userID);

            UserObj.setUserID(userID);

            if (UserObj.getUserID() != 0) {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/appointments.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointment View");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                ObservableList<AppointmentObj> soonAppointments = AppointmentDB.getSoonAppointments(UserObj.getUserID());
                String upcomingAppointmentText = "You have " + soonAppointments.size() + " Appointment(s) in the next 15 minutes: \n"
                        + getSoonAppointmentDetail(soonAppointments);

                if (soonAppointments.size() != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("Upcoming Appointment");
                    alert.setContentText(upcomingAppointmentText);
                    alert.setResizable(true);
                    alert.getDialogPane().setPrefHeight(145 + soonAppointments.size() * 55);
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("No Upcoming Appointments");
                    alert.setContentText("You have no appointments starting in the next 15 minutes.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("errorTitle"));
                alert.setHeaderText(rb.getString("incorrectHeader"));
                alert.setContentText(rb.getString("incorrectText"));
                alert.showAndWait();
            }
        }
    }

    private void populateLoginRecord(String usernameAttempt, String passwordAttempt, int userID) {
        String result = "Unsuccessful Attempt: ";
        if (userID != 0) {
            result = "Successful Attempt: ";
        }
        try {
            FileWriter loginRecord = new FileWriter("login_activity.txt", true);
            loginRecord.write("\n " + result +
                    "\n  Date: " + ZonedDateTime.now(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd 'at' HH:mm")) + " UTC" +
                    ", Username Attempted: " + usernameAttempt.toUpperCase(Locale.ROOT) +
                    ", Password Attempted: " + passwordAttempt.toUpperCase(Locale.ROOT));
            loginRecord.close();
            System.out.println("Login Record updated");
        } catch (IOException e) {
            System.out.println("An error occurred when writing to login record.");
            e.printStackTrace();
        }
    }

    private String getSoonAppointmentDetail(ObservableList<AppointmentObj> soonAppointments) throws SQLException {
        String appointmentDetail = "";
        for (AppointmentObj currAppointment : soonAppointments) {
            appointmentDetail = appointmentDetail + "    " + "Appointment " + currAppointment.getAppointmentID()
                    + " is at " + currAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm 'on' MM-dd-yyyy")) + "\n" + "        "
                    + " Customer: " + AppointmentDB.customerIDToCustomerName(currAppointment.getCustomerID()) + "\n" + "        "
                    + " Contact: " + AppointmentDB.contactIDToContactName(currAppointment.getContactID()) + "\n";
        }
        return appointmentDetail;
    }

    public void closeApplication(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setContentText("Are you sure you want to exit the program?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            JDBC.closeConnection();
            System.exit(0);
        }
    }
}