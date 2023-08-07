package klein.view_controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import klein.helper_controllers.CustomerObj;
import klein.helper_controllers.DAO.CustomerDB;
import klein.helper_controllers.UserObj;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public ComboBox<String> countryField;
    public ObservableList<String> countryList;
    public ComboBox<String> regionField;
    public ObservableList<String> regionList;
    public TextField phoneField;
    private Integer customerID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime updateDate;
    private String updatedBy;
    private String country;
    private String region;
    private Integer divisionID;

    private CustomerObj selectedCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomersController.getSelectedCustomer();

        customerIDField.setText(String.valueOf(selectedCustomer.getCustomerID()));
        nameField.setText(String.valueOf(selectedCustomer.getName()));
        addressField.setText(String.valueOf(selectedCustomer.getAddress()));
        postalCodeField.setText(String.valueOf(selectedCustomer.getPostalCode()));
        countryField.setValue(String.valueOf(selectedCustomer.getCountry()));
        regionField.setValue(String.valueOf(selectedCustomer.getRegion()));
        phoneField.setText(String.valueOf(selectedCustomer.getPhone()));

        try {
            countryField.setItems(CustomerDB.getCountries());
            regionField.setItems(CustomerDB.getRegionsFromCountryName(String.valueOf(countryField.getValue())));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void populateRegions(ActionEvent actionEvent) throws SQLException {
        String selectedCountry = countryField.getValue();

        regionList = CustomerDB.getRegionsFromCountryName(selectedCountry);

        regionField.setItems(regionList);
    }

    public void modifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        customerID = Integer.parseInt(customerIDField.getText());
        name = nameField.getText();
        address = addressField.getText();
        postalCode = postalCodeField.getText();
        phone = phoneField.getText();
        createDate = selectedCustomer.getDateCreated();
        createdBy = selectedCustomer.getCreatedBy();
        updateDate = LocalDateTime.now();
        updatedBy = UserObj.getUserName();
        country = countryField.getValue();
        region = regionField.getValue();
        divisionID = CustomerDB.getDivIDFromRegion(String.valueOf(region));

        CustomerObj newCustomer = new CustomerObj(customerID, name, address, postalCode, phone, createDate, createdBy, updateDate, updatedBy,divisionID, country, region);

        CustomerDB.updateCustomer(newCustomer);

        returnToCustomers(actionEvent);
        return;
    }

    public void returnToCustomers(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/klein/view/customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer View");
        stage.setScene(scene);
        stage.show();
    }
}
