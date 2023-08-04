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
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.AppointmentObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentsPage implements Initializable {
    private static AppointmentObj selectedAppointment;
    public static ObservableList<AppointmentObj> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<AppointmentObj> selectedAppointments = FXCollections.observableArrayList();
    public String searchBarText;
    public TableView<AppointmentObj> appointmentTableView;
    public TableColumn<AppointmentObj, Integer> appointmentIDColumn;
    public TableColumn<AppointmentObj, String> titleColumn;
    public TableColumn<AppointmentObj, String> descriptionColumn;
    public TableColumn<AppointmentObj, String> locationColumn;
    public TableColumn<AppointmentObj, String> typeColumn;
    public TableColumn<AppointmentObj, Timestamp> startTimeColumn;
    public TableColumn<AppointmentObj, Timestamp> endTimeColumn;
    public TableColumn<AppointmentObj, Integer> customerIDColumn;
    public TableColumn<AppointmentObj, Integer> userIDColumn;
    public RadioButton toViewAllButton;
    public TextField searchBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            toViewAll(new ActionEvent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectAppointment(MouseEvent mouseEvent) {
        selectedAppointment = appointmentTableView.getSelectionModel().getSelectedItem();
    }

    public void toAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/addAppointment.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyAppointment(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/modifyAppointment.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Update Appointment #" + selectedAppointment.getAppointmentID());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No appointment selected");
            alert.setContentText("You must first select an appointment in order to modify it");
            alert.showAndWait();
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setContentText("Are you sure you want to delete appointment: " + selectedAppointment.getAppointmentID() + "?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //DELETE FROM DB
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("You must first select an appointment in order to delete it");
            alert.showAndWait();
        }
    }

    public void toViewAll(ActionEvent actionEvent) throws SQLException {
        allAppointments = AppointmentDB.getAllAppointments();
        appointmentTableView.setItems(allAppointments);
    }

    public void toMonthlyView(ActionEvent actionEvent) throws SQLException {
        allAppointments = AppointmentDB.getAllAppointments();

        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
        LocalDateTime endDate = LocalDateTime.now().plusMonths(1);

        selectedAppointments.clear();

        if (allAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("You don't have any appointments");
            alert.showAndWait();
        } else {
            allAppointments.forEach(appointmentObj -> {
                if (appointmentObj.getStartTime().isAfter(startDate) && appointmentObj.getEndTime().isBefore(endDate)) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    public void toWeeklyView(ActionEvent actionEvent) throws SQLException {
        allAppointments = AppointmentDB.getAllAppointments();

        LocalDateTime startDate = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endDate = LocalDateTime.now().plusWeeks(1);

        selectedAppointments.clear();

        if (allAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("You don't have any appointments");
            alert.showAndWait();
        } else {
            allAppointments.forEach(appointmentObj -> {
                if (appointmentObj.getStartTime().isAfter(startDate) && appointmentObj.getEndTime().isBefore(endDate)) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    public void searchAppointments(ActionEvent actionEvent) throws SQLException {
        allAppointments = AppointmentDB.getAllAppointments();
        searchBarText = searchBar.getText();

        selectedAppointments.clear();

        if (allAppointments.isEmpty()) {
            toViewAllButton.selectedProperty().setValue(true);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Schedule");
            alert.setHeaderText("No Appointments");
            alert.setContentText("No appointments match your search");
            alert.showAndWait();
        } else {
            allAppointments.forEach(appointmentObj -> {
                if (appointmentObj.getTitle().contains(searchBarText)
                        || appointmentObj.getDescription().contains(searchBarText)
                        || appointmentObj.getLocation().contains(searchBarText)
                        || appointmentObj.getType().contains(searchBarText) ) {
                    selectedAppointments.add(appointmentObj);
                }
                appointmentTableView.setItems(selectedAppointments);
            });
        }
    }

    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Report View");
        stage.setScene(scene);
        stage.show();
    }

    public void toCustomers(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer View");
        stage.setScene(scene);
        stage.show();
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

    public static AppointmentObj getSelectedAppointment() {
        return selectedAppointment;
    }

    public static void setSelectedAppointment(AppointmentObj selectedAppointment) {
        AppointmentsPage.selectedAppointment = selectedAppointment;
    }
}
