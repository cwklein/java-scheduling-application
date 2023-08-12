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
import klein.helper_controllers.DAO.CustomerDB;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.CustomerObject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    private static CustomerObject selectedCustomer;
    public static ObservableList<CustomerObject> customerList = FXCollections.observableArrayList();
    public static ObservableList<CustomerObject> selectedCustomers = FXCollections.observableArrayList();
    public String searchBarText;
    public TextField searchBar;
    public TableView<CustomerObject> customerTableView;
    public TableColumn<CustomerObject, Integer> customerIDColumn;
    public TableColumn<CustomerObject, String> nameColumn;
    public TableColumn<CustomerObject, String> addressColumn;
    public TableColumn<CustomerObject, String> postalCodeColumn;
    public TableColumn<CustomerObject, String> phoneColumn;
    public TableColumn<CustomerObject, Integer> divisionIDColumn;
    public TableColumn<CustomerObject, String> countryColumn;
    public TableColumn<CustomerObject, String> regionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        customerTableView.setItems(customerList);
        customerIDColumn.setSortable(true);

        try {
            updateCustomerList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectCustomer(MouseEvent mouseEvent) {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/addCustomer.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void toModifyCustomer(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/modifyCustomer.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Update Customer #" + selectedCustomer.getCustomerID());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("You must first select a customer in order to modify it");
            alert.showAndWait();
        }
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setContentText("Are you sure you want to delete customer: " + selectedCustomer.getName() + "? "
                    + "This will delete all " + CustomerDB.getAssocAppointmentCount(selectedCustomer.getCustomerID()) + " appointment(s) associated with this customer as well.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());
                updateCustomerList();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("You must first select a customer in order to delete it");
            alert.showAndWait();
        }
    }

    public void searchCustomers(ActionEvent actionEvent) throws SQLException {
        customerList = CustomerDB.getAllCustomers();
        searchBarText = searchBar.getText().toLowerCase();

        selectedCustomers.clear();

        if (customerList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Contact List");
            alert.setHeaderText("No Contacts");
            alert.setContentText("No contacts found");
            alert.showAndWait();
        } else {
            customerList.forEach(customerObject -> {
                if (String.valueOf(customerObject.getCustomerID()).toLowerCase().contains(searchBarText)
                        || customerObject.getName().toLowerCase().contains(searchBarText)
                        || customerObject.getPhone().toLowerCase().contains(searchBarText)
                        || customerObject.getAddress().toLowerCase().contains(searchBarText)) {
                    selectedCustomers.add(customerObject);
                }
                customerTableView.setItems(selectedCustomers);
            });
        }
    }

    public void updateCustomerList() throws SQLException {
        customerList = CustomerDB.getAllCustomers();
        customerTableView.setItems(customerList);
    }
    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/reports.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Report View");
        stage.setScene(scene);
        stage.show();
    }

    public void toAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/appointments.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }

    public static CustomerObject getSelectedCustomer() {
        return selectedCustomer;
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
