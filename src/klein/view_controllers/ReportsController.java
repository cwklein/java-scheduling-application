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
import javafx.stage.Stage;
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.DAO.AppointmentDB;
import klein.helper_controllers.JDBC;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    public ComboBox<String> typeField;
    public ComboBox<String> monthField;
    public ComboBox<String> contactNameField;
    public ComboBox<String> customerNameField;
    public Label reportTypeLabel;
    public Label reportDetailLabel;
    public Label resultCountField;
    public TableView<AppointmentObj> resultTable;
    public TableColumn<AppointmentObj, Integer> appointmentIDColumn;
    public TableColumn<AppointmentObj, String> titleColumn;
    public TableColumn<AppointmentObj, String> descriptionColumn;
    public TableColumn<AppointmentObj, String> locationColumn;
    public TableColumn<AppointmentObj, String> typeColumn;
    public TableColumn<AppointmentObj, Integer> contactIDColumn;
    public TableColumn<AppointmentObj, LocalDateTime> startTimeColumn;
    public TableColumn<AppointmentObj, LocalDateTime> endTimeColumn;
    public TableColumn<AppointmentObj, Integer> customerIDColumn;
    public TableColumn<AppointmentObj, Integer> userIDColumn;
    private ObservableList<String> typeList;
    private ObservableList<String> monthList;
    private ObservableList<String> contactList;
    private ObservableList<String> customerList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactList = AppointmentDB.getContacts();
            customerList = AppointmentDB.getCustomers();
            typeList = AppointmentDB.getAllTypes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        monthList = FXCollections.observableArrayList("January","February","March","April","May","June","July","August", "September", "October", "November", "December");
        monthField.setItems(monthList);
        typeField.setItems(typeList);
        contactNameField.setItems(contactList);
        customerNameField.setItems(customerList);

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
    }

    public void generateMonthlyReport(ActionEvent actionEvent) throws SQLException {
        Integer month = getMonthInteger(monthField.getValue());
        ObservableList<AppointmentObj> resultList = AppointmentDB.getAppointmentsByMonth(typeField.getValue(), month);
        int resultCount = resultList.size();

        reportTypeLabel.setText("Monthly Report");
        reportDetailLabel.setText("Month: " + monthField.getValue() + " -- Type: " + typeField.getValue());
        resultTable.setItems(resultList);
        resultCountField.setText("Result Count:  " + resultCount);
    }

    private Integer getMonthInteger(String month) {

        return switch (month) {
            case "January" -> 1;
            case "February" -> 2;
            case "March" -> 3;
            case "April" -> 4;
            case "May" -> 5;
            case "June" -> 6;
            case "July" -> 7;
            case "August" -> 8;
            case "September" -> 9;
            case "October" -> 10;
            case "November" -> 11;
            case "December" -> 12;
            default -> 0;
        };
    }

    public void generateContactReport(ActionEvent actionEvent) throws SQLException {
        Integer contact = AppointmentDB.contactNameToContactID(contactNameField.getValue());
        ObservableList<AppointmentObj> resultList = AppointmentDB.getAppointmentsByContact(contact);
        int resultCount = resultList.size();

        reportTypeLabel.setText("Full Contact Schedule");
        reportDetailLabel.setText("Selected Contact: " + contactNameField.getValue());
        resultTable.setItems(resultList);
        resultCountField.setText("Result Count:  " + resultCount);
    }

    public void generateCustomerReport(ActionEvent actionEvent) throws SQLException {
        Integer customer = AppointmentDB.customerNameToCustomerID(customerNameField.getValue());
        ObservableList<AppointmentObj> resultList = AppointmentDB.getAppointmentsByCustomer(customer);
        int resultCount = resultList.size();

        reportTypeLabel.setText("Full Customer Schedule");
        reportDetailLabel.setText("Selected Customer: " + customerNameField.getValue());
        resultTable.setItems(resultList);
        resultCountField.setText("Result Count:  " + resultCount);
    }

    public void toAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }

    public void toCustomers(ActionEvent actionEvent) throws IOException{
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/customers.fxml")));
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
}
