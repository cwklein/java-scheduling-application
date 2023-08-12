package klein.view_controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import klein.helper_controllers.CustomerObject;
import klein.helper_controllers.DAO.CustomerDB;
import klein.helper_controllers.UserObject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public TextField customerIDField;
    public TextField nameField;
    public TextField addressField;
    public TextField postalCodeField;
    public ComboBox<String> countryField;
    public ComboBox<String> regionField;
    public ObservableList<String> regionList;
    public TextField phoneField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerIDField.setText(String.valueOf(CustomerDB.nextCustomerID()));
            countryField.setItems(CustomerDB.getCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Fills the regionField comboBox with only those regions that are in the selected country.
     * Factors in the selected country so that only viable regions are populated.
     * */
    public void populateRegions(ActionEvent actionEvent) throws SQLException {
        String selectedCountry = countryField.getValue();

        regionList = CustomerDB.getRegionsFromCountryName(selectedCountry);

        regionField.setItems(regionList);
    }

    /**
     * Button function that checks that all input fields are non-null and valid, then calls function to add customer to database.
     * Will show corresponding alerts if any fields are null.
     * * */
    public void addCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        if (nameField.getText().isBlank() || addressField.getText().isBlank() ||
                postalCodeField.getText().isBlank() || phoneField.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error - Missing Field");
            alert.setContentText("You are missing one or more required fields\nAll fields must be filled out to continue.");
            alert.showAndWait();
        } else {
            try {
                Integer customerID = Integer.parseInt(customerIDField.getText());
                String name = nameField.getText();
                String address = addressField.getText();
                String postalCode = postalCodeField.getText();
                String phone = phoneField.getText();
                LocalDateTime createDate = LocalDateTime.now();
                String createdBy = UserObject.getUserName();
                LocalDateTime updateDate = LocalDateTime.now();
                String updatedBy = UserObject.getUserName();
                String country = countryField.getValue();
                String region = regionField.getValue();
                Integer divisionID = CustomerDB.getDivIDFromRegion(region);

                CustomerObject newCustomer = new CustomerObject(customerID, name, address, postalCode, phone, createDate, createdBy, updateDate, updatedBy, divisionID, country, region);

                CustomerDB.addCustomer(newCustomer);

                returnToCustomers(actionEvent);
            } catch (NullPointerException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error - Missing Field");
                alert.setContentText("You are missing one or more required fields\nAll fields must be filled out to continue.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Button function that redirects the user back to the 'customers' page.
     * */
    public void returnToCustomers(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/klein/view/customers.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer View");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}
