package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import klein.helper_controllers.CustomerObj;
import klein.helper_controllers.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerDB {

    public static ObservableList<CustomerObj> getAllCustomers() throws SQLException {
        ObservableList<CustomerObj> customerList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM customers";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        customerList.clear();

        while(rs.next()) {
            CustomerObj newCust = new CustomerObj(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")
            );
            customerList.add(newCust);
        }
        return customerList;
    }

    public static void addCustomer(CustomerObj newCustomer) throws SQLException {
        Integer customerID = newCustomer.getCustomerID();
        String name = newCustomer.getName();
        String address = newCustomer.getAddress();
        String postalCode = newCustomer.getPostalCode();
        String phone = newCustomer.getPhone();
        LocalDateTime dateCreated = newCustomer.getDateCreated();
        String createdBy = newCustomer.getCreatedBy();
        LocalDateTime dateUpdated = newCustomer.getDateUpdated();
        String updatedBy = newCustomer.getUpdatedBy();
        Integer divisionID = newCustomer.getDivisionID();

        String sqlQuery = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, " +
                "Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, customerID);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ps.setTimestamp(6, Timestamp.valueOf(dateCreated));
        ps.setString(7, createdBy);
        ps.setTimestamp(8, Timestamp.valueOf(dateUpdated));
        ps.setString(9, updatedBy);
        ps.setInt(10, divisionID);

        ps.executeUpdate();
        ps.close();
    }

    public static void deleteCustomer(Integer customerID) throws SQLException {
        String sqlQuery = "DELETE FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, customerID);

        ps.executeUpdate();
        ps.close();
    }

    public static Integer nextCustomerID() throws SQLException {
        Integer nextID = 0;
        for (int i = 1; i < 100; i++) {
            String sqlQuery = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
            ps.setInt(1, i);

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                nextID = i;
                break;
            }
        }
        if (nextID == 0) {
            System.out.println("final: " + nextID);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Excessive Customers");
            alert.setHeaderText("Too many customers");
            alert.setContentText("You have exceeded " + 100 + " customers, that's too many for a prototype");
            alert.showAndWait();
            return 0;
        }
        else {
            return nextID;
        }
    }
}
