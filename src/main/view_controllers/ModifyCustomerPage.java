package main.view_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.helper_controllers.CustomerObj;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerPage implements Initializable {
    public TextField customerID;
    public TextField name;
    public TextField address;
    public TextField postalCode;
    public ComboBox countryCB;
    public ComboBox regionCB;
    public TextField phone;
    private CustomerObj selectedCustomer;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = CustomersPage.getSelectedCustomer();

        customerID.setText(String.valueOf(selectedCustomer.getCustomerID()));
        name.setText(String.valueOf(selectedCustomer.getCustomerName()));
        address.setText(String.valueOf(selectedCustomer.getCustomerAddress()));
        postalCode.setText(String.valueOf(selectedCustomer.getCustomerPostalCode()));
        phone.setText(String.valueOf(selectedCustomer.getCustomerPhone()));
        //POPULATE CB'S
    }
    public void modifyCustomer(ActionEvent actionEvent) {

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
