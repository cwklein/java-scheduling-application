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
import klein.helper_controllers.interfaces.ComboInterface;
import klein.helper_controllers.interfaces.ResultInterface;

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
    private ObservableList<AppointmentObj> resultList;
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
        resultList = null;

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

    ComboInterface findIndexPlusOne = (ObservableList<String> monthList, String selectedMonth) -> monthList.indexOf(selectedMonth)+1;

    ResultInterface resultFill = (TableView<AppointmentObj> tableToSet, Label labelToSet, ObservableList<AppointmentObj> resultList) -> {
        tableToSet.setItems(resultList);
        labelToSet.setText("Result Count:  " + resultList.size());
    };

    public void generateMonthlyReport(ActionEvent actionEvent) throws SQLException {
        Integer month = findIndexPlusOne.givenListAndObject(monthList, monthField.getValue());
        resultList = AppointmentDB.getAppointmentsByMonth(typeField.getValue(), month);

        reportTypeLabel.setText("Monthly Report");
        reportDetailLabel.setText("Month: " + monthField.getValue() + " -- Type: " + typeField.getValue());
        resultFill.generateResultView(resultTable, resultCountField, resultList);
    }

    public void generateContactReport(ActionEvent actionEvent) throws SQLException {
        Integer contact = AppointmentDB.contactNameToContactID(contactNameField.getValue());
        resultList = AppointmentDB.getAppointmentsByContact(contact);

        reportTypeLabel.setText("Full Contact Schedule");
        reportDetailLabel.setText("Selected Contact: " + contactNameField.getValue());
        resultFill.generateResultView(resultTable, resultCountField, resultList);
    }

    public void generateCustomerReport(ActionEvent actionEvent) throws SQLException {
        Integer customer = AppointmentDB.customerNameToCustomerID(customerNameField.getValue());
        resultList = AppointmentDB.getAppointmentsByCustomer(customer);

        reportTypeLabel.setText("Full Customer Schedule");
        reportDetailLabel.setText("Selected Customer: " + customerNameField.getValue());
        resultFill.generateResultView(resultTable, resultCountField, resultList);
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
