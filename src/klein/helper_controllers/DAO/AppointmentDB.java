package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.AppointmentObj;
import klein.helper_controllers.UserObj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDB {

    public static ObservableList<AppointmentObj> getUserAppointments() throws SQLException {
        ObservableList<AppointmentObj> userAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, UserObj.getUserID());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            userAppointments.add(newAppt);
        }
        return userAppointments;
    }

    public static ObservableList<AppointmentObj> getAllAppointments() throws SQLException {
        ObservableList<AppointmentObj> allAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            allAppointments.add(newAppt);
        }
        return allAppointments;
    }

    public static ObservableList<AppointmentObj> getAppointmentsByContact(Integer contact) throws SQLException {
        ObservableList<AppointmentObj> contactAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Contact_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, contact);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            contactAppointments.add(newAppt);
        }
        return contactAppointments;
    }

    public static ObservableList<AppointmentObj> getAppointmentsByCustomer(Integer customer) throws SQLException {
        ObservableList<AppointmentObj> customerAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Customer_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customer);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            customerAppointments.add(newAppt);
        }
        return customerAppointments;
    }

    public static ObservableList<AppointmentObj> getSoonAppointments(Integer userID) throws SQLException {
        ObservableList<AppointmentObj> soonAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ? " +
                "AND Start BETWEEN localtimestamp() AND DATE_ADD(localtimestamp(), INTERVAL 15 minute) " +
                "ORDER BY appointments.Start ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, userID);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            soonAppointments.add(newAppt);
        }
        return soonAppointments;
    }

    public static ObservableList<String> getUsers() throws SQLException {
        ObservableList<String> userList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM users";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String newUser = rs.getString("User_Name");
            userList.add(newUser);
        }
        rs.close();
        return userList;
    }

    public static Integer userNameToUserID(String userName) throws SQLException {
        Integer userID;

        String sqlQuery = "SELECT * FROM users WHERE User_Name = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, userName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            userID = rs.getInt("User_ID");
        }
        else {
            userID = null;
        }
        rs.close();
        return userID;
    }

    public static String userIDToUserName(Integer userID) throws SQLException {
        String userName;

        String sqlQuery = "SELECT * FROM users WHERE User_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, userID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            userName = rs.getString("User_Name");
        }
        else {
            userName = null;
        }
        rs.close();
        return userName;
    }

    public static ObservableList<String> getContacts() throws SQLException {
        ObservableList<String> contactList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM contacts";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String newContact = rs.getString("Contact_Name");
            contactList.add(newContact);
        }
        rs.close();
        return contactList;
    }

    public static Integer contactNameToContactID(String contactName) throws SQLException {
        Integer contactID;

        String sqlQuery = "SELECT * FROM contacts WHERE Contact_Name = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, contactName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            contactID = rs.getInt("Contact_ID");
        }
        else {
            contactID = null;
        }
        rs.close();
        return contactID;
    }

    public static String contactIDToContactName(Integer contactID) throws SQLException {
        String contactName;

        String sqlQuery = "SELECT * FROM contacts WHERE Contact_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, contactID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            contactName = rs.getString("Contact_Name");
        }
        else {
            contactName = null;
        }
        rs.close();
        return contactName;
    }

    public static ObservableList<String> getCustomers() throws SQLException {
        ObservableList<String> customerList = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM customers";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String newCustomer = rs.getString("Customer_Name");
            customerList.add(newCustomer);
        }
        rs.close();
        return customerList;
    }

    public static Integer customerNameToCustomerID(String customerName) throws SQLException {
        Integer customerID;

        String sqlQuery = "SELECT * FROM customers WHERE Customer_Name = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, customerName);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            customerID = rs.getInt("Customer_ID");
        }
        else {
            customerID = null;
        }
        rs.close();
        return customerID;
    }

    public static String customerIDToCustomerName(Integer customerID) throws SQLException {
        String customerName;

        String sqlQuery = "SELECT * FROM customers WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customerID);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            customerName = rs.getString("Customer_Name");
        }
        else {
            customerName = null;
        }
        rs.close();
        return customerName;
    }

    public static void addAppointment(AppointmentObj newAppointment) throws SQLException {
        Integer appointmentID = newAppointment.getAppointmentID();
        String title = newAppointment.getTitle();
        String description = newAppointment.getDescription();
        String location = newAppointment.getLocation();
        String appointmentType = newAppointment.getType();
        LocalDateTime startTime = newAppointment.getStart();
        LocalDateTime endTime = newAppointment.getEnd();
        LocalDateTime createDate = newAppointment.getCreateDate();
        String createUser = newAppointment.getCreatedBy();
        LocalDateTime updateDate = newAppointment.getUpdateDate();
        String updateUser = newAppointment.getUpdatedBy();
        Integer customerID = newAppointment.getCustomerID();
        Integer userID = newAppointment.getUserID();
        Integer contactID = newAppointment.getContactID();

        String sqlQuery = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, " +
                "Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By,Customer_ID," +
                "User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, appointmentID);
        ps.setString(2, title);
        ps.setString(3, description);
        ps.setString(4, location);
        ps.setString(5, appointmentType);
        ps.setTimestamp(6, Timestamp.valueOf(startTime));
        ps.setTimestamp(7, Timestamp.valueOf(endTime));
        ps.setTimestamp(8, Timestamp.valueOf(createDate));
        ps.setString(9, createUser);
        ps.setTimestamp(10, Timestamp.valueOf(updateDate));
        ps.setString(11, updateUser);
        ps.setInt(12, customerID);
        ps.setInt(13, userID);
        ps.setInt(14, contactID);

        ps.executeUpdate();
        ps.close();
    }

    public static void deleteAppointment(Integer appointmentID) throws SQLException {
        String sqlQuery = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, appointmentID);

        ps.executeUpdate();
        ps.close();
    }

    public static Integer nextOpenAppointment() throws SQLException {
        Integer nextID = 0;
        for (int i = 1; i < 100; i++) {
            String sqlQuery = "SELECT * FROM appointments WHERE Appointment_ID = ?";
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
            alert.setTitle("Excessive Appointments");
            alert.setHeaderText("Too many appointments");
            alert.setContentText("You have exceeded " + 100 + " concurrent appointments, that's too many for a prototype");
            alert.showAndWait();
            return 0;
        }
        else {
            return nextID;
        }
    }

    public static ObservableList<AppointmentObj> getAppointmentsByMonth(String type, Integer month) throws SQLException {
        ObservableList<AppointmentObj> monthAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Type = ? " +
                "AND MONTH(Start) = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, type);
        ps.setInt(2, month);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObj newAppt = new AppointmentObj(
                    rs.getInt("Appointment_ID"),
                    rs.getString("Title"),
                    rs.getString("Description"),
                    rs.getString("Location"),
                    rs.getString("Type"),
                    rs.getTimestamp("Start").toLocalDateTime(),
                    rs.getTimestamp("End").toLocalDateTime(),
                    rs.getTimestamp("Create_Date").toLocalDateTime(),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update").toLocalDateTime(),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID"),
                    rs.getInt("Contact_ID")
            );
            monthAppointments.add(newAppt);
        }
        return monthAppointments;
    }

    public static ObservableList<String> getAllTypes() throws SQLException {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        for (AppointmentObj currentAppointment : getAllAppointments()) {
            if (!typeList.contains(currentAppointment.getType())) {
                typeList.add(currentAppointment.getType());
            }
        }

        return typeList;
    }
}
