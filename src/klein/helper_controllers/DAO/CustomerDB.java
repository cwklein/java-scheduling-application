package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import klein.helper_controllers.CustomerObject;
import klein.helper_controllers.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CustomerDB {

    /**
     * Returns an ObservableList of all CustomerObject's, including their associated country and region.
     *
     * @return an ObservableList of all customers.
     * */
    public static ObservableList<CustomerObject> getAllCustomers() throws SQLException {
        ObservableList<CustomerObject> customerList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT customers.*, first_level_divisions.Division, countries.Country " +
                "FROM customers " +
                "INNER JOIN first_level_divisions " +
                "ON customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN countries " +
                "ON first_level_divisions.Country_ID = countries.Country_ID " +
                "ORDER BY customers.Customer_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        customerList.clear();

        while(rs.next()) {
            CustomerObject newCustomer = new CustomerObject(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID"),
                    rs.getString("Country"),
                    rs.getString("Division")
            );
            customerList.add(newCustomer);
        }
        rs.close();
        return customerList;
    }

    /**
     * Returns an ObservableList of all countries as strings.
     * Used for generating the country options within the country comboBox on both the Add and Modify Customer Pages.
     *
     * @return an ObservableList of all countries as strings.
     * */
    public static ObservableList<String> getCountries() throws SQLException {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM countries";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        countryList.clear();

        while(rs.next()) {
            String newCountry = rs.getString("Country");
            countryList.add(newCountry);
        }
        rs.close();
        return countryList;
    }

    /**
     * Adds a new customer to the customers database.
     *
     * @param newCustomer the customerObject being added to the customers database.
     * */
    public static void addCustomer(CustomerObject newCustomer) throws SQLException {
        int customerID = newCustomer.getCustomerID();
        String name = newCustomer.getName();
        String address = newCustomer.getAddress();
        String postalCode = newCustomer.getPostalCode();
        String phone = newCustomer.getPhone();
        LocalDateTime dateCreated = newCustomer.getDateCreated();
        String createdBy = newCustomer.getCreatedBy();
        LocalDateTime dateUpdated = newCustomer.getDateUpdated();
        String updatedBy = newCustomer.getUpdatedBy();
        int divisionID = newCustomer.getDivisionID();

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

    /**
     * Updates the given customer within the customers database.
     *
     * @param updatedCustomer the customerObject being modified within the customers database.
     * */
    public static void updateCustomer(CustomerObject updatedCustomer) throws SQLException {
        int customerID = updatedCustomer.getCustomerID();
        String name = updatedCustomer.getName();
        String address = updatedCustomer.getAddress();
        String postalCode = updatedCustomer.getPostalCode();
        String phone = updatedCustomer.getPhone();
        LocalDateTime dateCreated = updatedCustomer.getDateCreated();
        String createdBy = updatedCustomer.getCreatedBy();
        LocalDateTime dateUpdated = updatedCustomer.getDateUpdated();
        String updatedBy = updatedCustomer.getUpdatedBy();
        int divisionID = updatedCustomer.getDivisionID();

        String sqlQuery = "UPDATE customers " +
                "SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, " +
                "Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";

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
        ps.setInt(11, customerID);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Deletes the selected customer from the customers database as well as any associated appointments from the appointments database.
     *
     * @param customerID the Customer_ID of the customer being deleted from the customers database.
     * */
    public static void deleteCustomer(Integer customerID) throws SQLException {
        String sqlQuery = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, customerID);
        ps.execute();

        sqlQuery = "DELETE FROM customers WHERE Customer_ID = ?";
        ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, customerID);
        ps.execute();

        ps.close();
    }

    /**
     * Returns the integer-type divisionID corresponding to the selected string-type regionName.
     * Used for storing the selected region option from the region comboBox on both the Add and Modify Customer Pages.
     *
     * @param regionName the string-type regionName obtained from the value of the region comboBox.
     * @return the integer-type divisionID corresponding to the given regionName.
     * */
    public static Integer getDivIDFromRegion(String regionName) throws SQLException {
        Integer divisionID;

        String sqlQuery = "SELECT * FROM first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, regionName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        else {
            divisionID = null;
        }
        rs.close();
        return divisionID;
    }

    /**
     * Returns an ObservableList of string-type regionNames associated with the selected countryName.
     * Used for generating the region options within the region comboBox on both the Add and Modify Customer Pages.
     * Limits the regionList to only include those regions that are associated with the selected country.
     *
     * @param countryName the string-type countryName obtained from the value of the country comboBox.
     * @return the string-type ObservableList of regions that are within the selected country.
     * */
    public static ObservableList<String> getRegionsFromCountryName(String countryName) throws SQLException {
        Integer countryID = CustomerDB.countryNameToCountryID(countryName);

        ObservableList<String> regionList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, countryID);

        ResultSet rs = ps.executeQuery();

        regionList.clear();

        while(rs.next()) {
            String newRegion = rs.getString("Division");
            regionList.add(newRegion);
        }
        rs.close();
        return regionList;
    }

    /**
     * Returns the integer-type countryID corresponding to the selected string-type countryName.
     * Used for storing the selected country option from the country comboBox on both the Add and Modify Customer Pages.
     *
     * @param countryName the string-type countryName obtained from the value of the country comboBox.
     * @return the integer-type countryID corresponding to the given countryName.
     * */
    private static Integer countryNameToCountryID(String countryName) throws SQLException {
        Integer countryID;

        String sqlQuery = "SELECT * FROM countries WHERE Country = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, countryName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            countryID = rs.getInt("Country_ID");
        }
        else {
            countryID = null;
        }
        rs.close();
        return countryID;
    }

    /**
     * Returns the smallest Customer_ID within the customers database that isn't already assigned.
     *
     * @return the smallest integer-type Customer_ID that isn't being used currently.
     * */
    public static Integer nextCustomerID() throws SQLException {
        int nextID = 0;
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

    /**
     * Returns the number of appointments that are associated with the given customerID.
     * Used in generating the confirmation alert when deleting a customer.
     *
     * @param customerID the integer-type customerID used to determine the number of associated appointments.
     * @return returns an integer for the number of appointments relating to the given customerID.
     * */
    public static Integer getAssocAppointmentCount(Integer customerID) throws SQLException {
        int appointmentCount;

        String sqlQuery = "SELECT COUNT(Appointment_ID) FROM appointments WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customerID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            appointmentCount = rs.getInt("COUNT(Appointment_ID)");
        }
        else {
            appointmentCount = 0;
        }
        rs.close();
        return appointmentCount;
    }
}
