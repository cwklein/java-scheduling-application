package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import klein.helper_controllers.CustomerObj;
import klein.helper_controllers.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    rs.getTimestamp("Create_Date"),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")
            );
            customerList.add(newCust);
        }
        return customerList;
    }
}
