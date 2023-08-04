package klein.view_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import klein.helper_controllers.DAO.LoginDB;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.UserObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
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

            UserObj.userID = LoginDB.ValidateUser(usernameAttempt, passwordAttempt);

            if (UserObj.userID != 0) {
                Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/appointments.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Appointment View");
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("errorTitle"));
                alert.setHeaderText(rb.getString("incorrectHeader"));
                alert.setContentText(rb.getString("incorrectText"));
                alert.showAndWait();
            }
        }
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