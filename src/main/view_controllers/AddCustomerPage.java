package main.view_controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerPage {
    public TextField customerID;
    public TextField name;
    public TextField address;
    public TextField postalCode;
    public ComboBox countryCB;
    public ComboBox regionCB;
    public TextField phone;

    public void addCustomer(ActionEvent actionEvent) {
        String customerIDText = customerID.getText();
        String nameText = name.getText();
        String addressText = address.getText();
        String postalCodeText = postalCode.getText();
        String countryText = countryCB.getAccessibleText();
        String regionText = regionCB.getAccessibleText();
        String phoneText = phone.getText();
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
