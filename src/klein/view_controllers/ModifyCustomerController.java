package klein.view_controllers;

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
    public ComboBox localeCountryField;
    public ComboBox localeFirstLevelField;
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
        phoneField.setText(String.valueOf(selectedCustomer.getPhone()));
        //POPULATE CB'S
    }
    public void modifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {
        customerID = Integer.parseInt(customerIDField.getText());
        name = nameField.getText();
        address = addressField.getText();
        postalCode = postalCodeField.getText();
        phone = phoneField.getText();
        createDate = LocalDateTime.now();
        createdBy = UserObj.getUserName();
        updateDate = LocalDateTime.now();
        updatedBy = UserObj.getUserName();
        country = localeCountryField.getAccessibleText();
        region = localeFirstLevelField.getAccessibleText();
        divisionID = 1; // TEMP - W/ CB

        CustomerObj newCustomer = new CustomerObj(customerID, name, address, postalCode, phone, createDate, createdBy, updateDate, updatedBy,divisionID);

        CustomerDB.deleteCustomer(selectedCustomer.getCustomerID());
        CustomerDB.addCustomer(newCustomer);

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
