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
import klein.helper_controllers.CustomerObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersController implements Initializable {
    private static CustomerObj selectedCustomer;
    public static ObservableList<CustomerObj> customerList = FXCollections.observableArrayList();
    public static ObservableList<CustomerObj> selectedCustomers = FXCollections.observableArrayList();
    public String searchBarText;
    public TextField searchBar;
    public TableColumn<CustomerObj, Integer> custIDColumn;
    public TableColumn<CustomerObj, String> custNameColumn;
    public TableColumn<CustomerObj, String> custAddressColumn;
    public TableColumn<CustomerObj, String> custPostalCodeColumn;
    public TableColumn<CustomerObj, String> custPhoneColumn;
    public TableColumn<CustomerObj, Integer> custDivisionIDColumn;
    public TableColumn<CustomerObj, String> custCountryColumn;
    public TableColumn<CustomerObj, String> custRegionColumn;
    public TableView<CustomerObj> custTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIDColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        custRegionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        custTableView.setItems(customerList);
        custIDColumn.setSortable(true);

        try {
            updateCustomerList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void selectCustomer(MouseEvent mouseEvent) {
        selectedCustomer = custTableView.getSelectionModel().getSelectedItem();
    }

    public void toAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/addCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyCustomer(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/modifyCustomer.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Update Customer #" + selectedCustomer.getCustomerID());
            stage.setScene(scene);
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
                    + "This will delete all " + CustomerDB.getAssocApptCount(selectedCustomer.getCustomerID()) + " appointment(s) associated with this customer as well.");

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
            customerList.forEach(customerObj -> {
                if (String.valueOf(customerObj.getCustomerID()).toLowerCase().contains(searchBarText)
                        || customerObj.getName().toLowerCase().contains(searchBarText)
                        || customerObj.getPhone().toLowerCase().contains(searchBarText)
                        || customerObj.getAddress().toLowerCase().contains(searchBarText)) {
                    selectedCustomers.add(customerObj);
                }
                custTableView.setItems(selectedCustomers);
            });
        }
    }

    public void updateCustomerList() throws SQLException {
        customerList = CustomerDB.getAllCustomers();
        custTableView.setItems(customerList);
    }
    public void toReports(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/reports.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Report View");
        stage.setScene(scene);
        stage.show();
    }

    public void toAppointments(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/appointments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointment View");
        stage.setScene(scene);
        stage.show();
    }

    public static CustomerObj getSelectedCustomer() {
        return selectedCustomer;
    }

    public static void setSelectedCustomer(CustomerObj selectedCustomer) {
        CustomersController.selectedCustomer = selectedCustomer;
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
