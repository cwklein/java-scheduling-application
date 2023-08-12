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
import klein.helper_controllers.AppointmentObject;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.DAO.UserDB;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.UserObject;

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

    /**
     * Button function that attempts to establish the user as a particular userID and access the database.
     * Checks the username and password against the usernames and passwords within the users database and calls the function to append a login-attempt to the login_activity text file.
     * If the login information is correct, calls the functions needed to generate an alert of all appointments within the next 15 minutes and redirects the user to the appointments page.
     * If the login information is incorrect, generates and error alert telling the user that their username or password are incorrect.
     * */
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

            UserObject.setUserID(userID);

            if (UserObject.getUserID() != 0) {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/appointments.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointment View");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

                ObservableList<AppointmentObject> soonAppointments = AppointmentDB.getSoonAppointments(UserObject.getUserID());
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

    /**
     * Generates a login record and fills the login_attempt text file with the information.
     * @param usernameAttempt String used to document the attempted userName value.
     * @param passwordAttempt String used to document the attempted password value.
     * @param userID Integer used here to indicate whether the login attempt was successful.
     * */
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

    /**
     * Generates and returns a string outlining the basic details of any and all appointments coming up in the next 15 minutes.
     *
     * @param soonAppointments The appointmentObject-type ObservableList of all appointments coming up in the next 15 minutes.
     * @return String outlining the basic details of all upcoming appointments.
     * */
    private String getSoonAppointmentDetail(ObservableList<AppointmentObject> soonAppointments) throws SQLException {
        String appointmentDetail = "";
        for (AppointmentObject currAppointment : soonAppointments) {
            appointmentDetail = appointmentDetail + "    " + "Appointment " + currAppointment.getAppointmentID()
                    + " is at " + currAppointment.getStart().format(DateTimeFormatter.ofPattern("HH:mm 'on' MM-dd-yyyy")) + "\n" + "        "
                    + " Customer: " + AppointmentDB.customerIDToCustomerName(currAppointment.getCustomerID()) + "\n" + "        "
                    + " Contact: " + AppointmentDB.contactIDToContactName(currAppointment.getContactID()) + "\n";
        }
        return appointmentDetail;
    }

    /**
     * Button function that closes the application after a confirmation alert.
     * */
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