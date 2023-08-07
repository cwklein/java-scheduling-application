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
                    rs.getInt("Division_ID"),
                    rs.getString("Country"),
                    rs.getString("Division")
            );
            customerList.add(newCust);
        }
        rs.close();
        return customerList;
    }

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

    public static void updateCustomer(CustomerObj newCustomer) throws SQLException {
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

    public static Integer getDivIDFromRegion(String regionName) throws SQLException {
        Integer divisionID;

        String sqlQuery = "SELECT * FROM first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, regionName);

        System.out.println(regionName);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Boop");
            divisionID = rs.getInt("Division_ID");
        }
        else {
            divisionID = null;
        }
        rs.close();
        return divisionID;
    }

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

    public static Integer getAssocApptCount(Integer customerID) throws SQLException {
        Integer apptCount;

        String sqlQuery = "SELECT COUNT(Appointment_ID) FROM appointments WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customerID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            apptCount = rs.getInt("COUNT(Appointment_ID)");
        }
        else {
            apptCount = 0;
        }
        rs.close();
        return apptCount;
    }
}
