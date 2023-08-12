package klein.view_controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import klein.helper_controllers.AppointmentObject;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {
    private static AppointmentObject selectedAppointment;
    public static ObservableList<AppointmentObject> allUserAppointments = FXCollections.observableArrayList();
    public static ObservableList<AppointmentObject> selectedAppointments = FXCollections.observableArrayList();
    public String searchBarText;
    public TableView<AppointmentObject> appointmentTableView;
    public TableColumn<AppointmentObject, Integer> appointmentIDColumn;
    public TableColumn<AppointmentObject, String> titleColumn;
    public TableColumn<AppointmentObject, String> descriptionColumn;
    public TableColumn<AppointmentObject, String> locationColumn;
    public TableColumn<AppointmentObject, String> typeColumn;
    public TableColumn<AppointmentObject, Integer> contactIDColumn;
    public TableColumn<AppointmentObject, LocalDateTime> startTimeColumn;
    public TableColumn<AppointmentObject, LocalDateTime> endTimeColumn;
    public TableColumn<AppointmentObject, Integer> customerIDColumn;
    public TableColumn<AppointmentObject, Integer> userIDColumn;
    public RadioButton toViewAllButton;
    public TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            toViewAll(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Mouse function that labels the selected row within the appointments table as the 'selectedAppointment'.
     * */
    public void selectAppointment(MouseEvent mouseEvent) {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
    }

    /**
     * Button function that redirects the user to the 'addAppointment' page.
     * */
    public void toAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/addAppointment.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Button function that redirects the user to the 'modifyAppointment' page.
     * Will populate the modifyAppointment page with the data for the selectedAppointment, or if one isn't currently selected it will generate an error alert.
     * */
    public void toModifyAppointment(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/modifyAppointment.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Update Appointment #" + selectedAppointment.getAppointmentID());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No appointment selected");
            alert.setContentText("You must first select an appointment in order to modify it");
            alert.showAndWait();
        }
    }

    /**
     * Button function that deletes the selectedAppointment after confirming the selection with a confirmation alert.
     * Generates an error alert if no appointment is currently selected.
     * */
    public void deleteAppointment(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setContentText("Are you sure you want to delete: \n    Appointment ID: " + selectedAppointment.getAppointmentID() + "\n    Type: " + selectedAppointment.getType() + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDB.deleteAppointment(selectedAppointment.getAppointmentID());
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Appointment Deleted");
                confirmation.setHeaderText("Appointment Deleted");
                confirmation.setContentText("You have successfully deleted appointment: " + selectedAppointment.getAppointmentID());
                confirmation.showAndWait();
                updateAppointmentList();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("You must first select an appointment in order to delete it");
            alert.showAndWait();
        }
    }

    /**
     * Populates the appointments tableView with all appointments related to the currently logged in user.
     * Is the default view when the appointments screen is initially loaded.
     * */
    public void toViewAll(ActionEvent actionEvent) throws SQLException {
        allUserAppointments = AppointmentDB.getUserAppointments();
        appointmentTableView.setItems(allUserAppointments);
    }

    /**
     * Populates the appointments tableView with all appointments related to the currently logged in user that start during the coming month.
     * Is selected when the user selects the monthlyView radio button.
     * */
    public void toMonthlyView(ActionEvent actionEvent) throws SQLException {
        allUserAppointments = AppointmentDB.getUserAppointments();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1);

        selectedAppointments.clear();

        if (allUserAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("You don't have any appointments");
            alert.showAndWait();
        } else {
            allUserAppointments.forEach(appointmentObj -> {
                if (appointmentObj.getStart().isAfter(startDate) && appointmentObj.getEnd().isBefore(endDate)) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    /**
     * Populates the appointments tableView with all appointments related to the currently logged in user that start during the coming week.
     * Is selected when the user selects the weeklyView radio button.
     * */
    public void toWeeklyView(ActionEvent actionEvent) throws SQLException {
        allUserAppointments = AppointmentDB.getUserAppointments();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusWeeks(1);

        selectedAppointments.clear();

        if (allUserAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("You don't have any appointments");
            alert.showAndWait();
        } else {
            allUserAppointments.forEach(appointmentObj -> {
                if (appointmentObj.getStart().isAfter(startDate) && appointmentObj.getEnd().isBefore(endDate)) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    /**
     * Populates the appointments tableView with all appointments related to the currently logged in user that match the search criteria.
     * When the user selects the search button, the program parses through the database and returns all appointments that match the search criteria.
     * Appointments in the database must match by appointmentID, title, description, location or type, as indicated by the prompt text in the search bar.
     * */
    public void searchAppointments(ActionEvent actionEvent) throws SQLException {
        allUserAppointments = AppointmentDB.getUserAppointments();
        searchBarText = searchBar.getText().toLowerCase();

        selectedAppointments.clear();

        if (allUserAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("No appointments match your search");
            alert.showAndWait();
        } else {
            allUserAppointments.forEach(appointmentObj -> {
                if (String.valueOf(appointmentObj.getAppointmentID()).toLowerCase().contains(searchBarText)
                        || appointmentObj.getTitle().toLowerCase().contains(searchBarText)
                        || appointmentObj.getDescription().toLowerCase().contains(searchBarText)
                        || appointmentObj.getLocation().toLowerCase().contains(searchBarText)
                        || appointmentObj.getType().toLowerCase().contains(searchBarText) ) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    /**
     * Button function that redirects the user back to the 'reports' page.
     * */
    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/reports.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Report View");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Button function that redirects the user back to the 'customers' page.
     * */
    public void toCustomers(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/customers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer View");
        stage.setScene(scene);
        stage.show();
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

    /**
     * Basic getter function for the private attribute 'selectedAppointment'.
     * @return appointmentObject-type private attribute 'selectedAppointment'.
     * */
    public static AppointmentObject getSelectedAppointment() {
        return selectedAppointment;
    }

    /**
     * Button function that refreshes the appointments tableView after adding or modifying an appointment.
     * */
    public void updateAppointmentList() throws SQLException {
        allUserAppointments = AppointmentDB.getUserAppointments();
        appointmentTableView.setItems(allUserAppointments);
    }
}

