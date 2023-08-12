package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import klein.helper_controllers.AppointmentObject;
import klein.helper_controllers.JDBC;
import klein.helper_controllers.UserObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentDB {

    /**
     * Returns an ObservableList of all AppointmentObject's that have the current user as the User_ID.
     * Takes the userID  for the Prepared Statement from the getUserID() method within UserObject class.
     *
     * @return an ObservableList of appointments related to the current user.
     * */
    public static ObservableList<AppointmentObject> getUserAppointments() throws SQLException {
        ObservableList<AppointmentObject> userAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, UserObject.getUserID());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            userAppointments.add(newAppointment);
        }
        return userAppointments;
    }

    /**
     * Returns an ObservableList of all AppointmentObject's, regardless of any appointment attributes.
     * Used on the reports page so that the generated reports aren't limited to the current user.
     *
     * @return an ObservableList of all appointments.
     * */
    public static ObservableList<AppointmentObject> getAllAppointments() throws SQLException {
        ObservableList<AppointmentObject> allAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            allAppointments.add(newAppointment);
        }
        return allAppointments;
    }

    /**
     * Returns an ObservableList of all AppointmentObject's related to the given contactID.
     * Used on the reports page so that the generated report shows all appointments related to a given contactID.
     *
     * @param contactID the Contact_ID used to qualify which appointments should be added to the returned list.
     * @return an ObservableList of all appointments related to a specific contactID.
     * */
    public static ObservableList<AppointmentObject> getAppointmentsByContactID(Integer contactID) throws SQLException {
        ObservableList<AppointmentObject> contactAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Contact_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, contactID);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            contactAppointments.add(newAppointment);
        }
        return contactAppointments;
    }

    /**
     * Returns an ObservableList of all AppointmentObject's related to the given customerID.
     * Used on the reports page so that the generated report shows all appointments related to a given customerID.
     *
     * @param customerID the Customer_ID used to qualify which appointments should be added to the returned list.
     * @return an ObservableList of all appointments related to a specific customerID.
     * */
    public static ObservableList<AppointmentObject> getAppointmentsByCustomerID(Integer customerID) throws SQLException {
        ObservableList<AppointmentObject> customerAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Customer_ID = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, customerID);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            customerAppointments.add(newAppointment);
        }
        return customerAppointments;
    }

    /**
     * Returns an ObservableList of all AppointmentObject's related to the given userID and starting in the next 15 minutes.
     * Used for the alert after a successful login that informs the user whether or not they have any upcoming appointments that start in the next 15 minutes.
     * Due to restrictions from the evaluators, the start and end time of the range to check are given from the system rather than done locally in the SQL database.
     *
     * @param userID the User_ID used to qualify which appointments should be added to the returned list.
     * @return an ObservableList of all appointments related to the current user that are starting in the next 15 minutes..
     * */
    public static ObservableList<AppointmentObject> getSoonAppointments(Integer userID) throws SQLException {
        ObservableList<AppointmentObject> soonAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ? " +
                "AND Start BETWEEN ? AND DATE_ADD(?, INTERVAL 15 minute) " +
                "ORDER BY appointments.Start ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, userID);
        ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
        ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            soonAppointments.add(newAppointment);
        }
        return soonAppointments;
    }

    /**
     * Returns an ObservableList of all AppointmentObject's related to the given type and month.
     * Used for populating the results of the monthlyReport on the reports page.
     *
     * @param type the selected string-type Type of appointment, used to limit the results.
     * @param month the integer corresponding to the selected string-type Month of appointment, used to limit the results.
     * @return an ObservableList of all appointments related to the given type and month.
     * */
    public static ObservableList<AppointmentObject> getAppointmentsByMonth(String type, Integer month) throws SQLException {
        ObservableList<AppointmentObject> monthAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Type = ? " +
                "AND MONTH(Start) = ? " +
                "ORDER BY appointments.Appointment_ID ASC";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1, type);
        ps.setInt(2, month);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            monthAppointments.add(newAppointment);
        }
        return monthAppointments;
    }

    /**
     * Returns an ObservableList of all usernames within the users database.
     * Used for generating the user options within the user comboBox on both the Add and Modify Appointment Pages.
     *
     * @return an ObservableList of all user names as strings.
     * */
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

    /**
     * Returns an ObservableList of all contact names within the contacts database.
     * Used for generating the contact options within the contact comboBox on both the Add and Modify Appointment Pages.
     *
     * @return an ObservableList of all contact names as strings.
     * */
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

    /**
     * Returns an ObservableList of all customer names within the customers database.
     * Used for generating the customer options within the customer comboBox on both the Add and Modify Appointment Pages.
     *
     * @return an ObservableList of all customer names as strings.
     * */
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

    /**
     * Returns an ObservableList of all types within the appointments database.
     * Used for generating the type options within the type comboBox on the Reports Page.
     *
     * @return an ObservableList of all types as strings.
     * */
    public static ObservableList<String> getTypes() throws SQLException {
        ObservableList<String> typeList = FXCollections.observableArrayList();

        for (AppointmentObject currentAppointment : getAllAppointments()) {
            if (!typeList.contains(currentAppointment.getType())) {
                typeList.add(currentAppointment.getType());
            }
        }

        return typeList;
    }

    /**
     * Returns the integer-type userID corresponding to the selected string-type username.
     * Used for storing the selected user option from the user comboBox on both the Add and Modify Appointment Pages.
     * Opposite effect of userIDToUserName.
     *
     * @param userName the string-type userName obtained from the value of the user comboBox.
     * @return the integer-type userID corresponding to the given userName.
     * */
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

    /**
     * Returns the string-type userName corresponding to the selected integer-type userID.
     * Used for translating user ID into something more understandable for the user.
     * Opposite effect of userNameToUserID.
     *
     * @param userID the integer-type userID obtained from the User_ID property of an appointment.
     * @return the string-type userName corresponding to the given userID.
     * */
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
    /**
     * Returns the integer-type contactID corresponding to the selected string-type contactName.
     * Used for storing the selected contact option from the contact comboBox on both the Add and Modify Appointment Pages.
     * Opposite effect of contactIDToContactName.
     *
     * @param contactName the string-type contactName obtained from the value of the contact comboBox.
     * @return the integer-type contactID corresponding to the given contactName.
     * */
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

    /**
     * Returns the string-type contactName corresponding to the selected integer-type contactID.
     * Used for translating contact ID into something more understandable for the user.
     * Opposite effect of contactNameToContactID.
     *
     * @param contactID the integer-type contactID obtained from the Contact_ID property of an appointment.
     * @return the string-type contactName corresponding to the given contactID.
     * */
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

    /**
     * Returns the integer-type customerID corresponding to the selected string-type customerName.
     * Used for storing the selected customer option from the customer comboBox on both the Add and Modify Appointment Pages.
     * Opposite effect of customerIDToCustomerName.
     *
     * @param customerName the string-type customerName obtained from the value of the customer comboBox.
     * @return the integer-type customerID corresponding to the given customerName.
     * */
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

    /**
     * Returns the string-type customerName corresponding to the selected integer-type customerID.
     * Used for translating customer ID into something more understandable for the user.
     * Opposite effect of customerNameToCustomerID.
     *
     * @param customerID the integer-type customerID obtained from the Customer_ID property of an appointment.
     * @return the string-type customerName corresponding to the given customerID.
     * */
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

    /**
     * Adds a new appointment to the appointments database.
     *
     * @param newAppointment the appointmentObject being added to the appointments database.
     * */
    public static void addAppointment(AppointmentObject newAppointment) throws SQLException {
        int appointmentID = newAppointment.getAppointmentID();
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
        int customerID = newAppointment.getCustomerID();
        int userID = newAppointment.getUserID();
        int contactID = newAppointment.getContactID();

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

    /**
     * Updates the given appointment within the appointments database.
     *
     * @param updatedAppointment the appointmentObject being modified within the appointments database.
     * */
    public static void updateAppointment(AppointmentObject updatedAppointment) throws SQLException {
        int appointmentID = updatedAppointment.getAppointmentID();
        String title = updatedAppointment.getTitle();
        String description = updatedAppointment.getDescription();
        String location = updatedAppointment.getLocation();
        String appointmentType = updatedAppointment.getType();
        LocalDateTime startTime = updatedAppointment.getStart();
        LocalDateTime endTime = updatedAppointment.getEnd();
        LocalDateTime createDate = updatedAppointment.getCreateDate();
        String createUser = updatedAppointment.getCreatedBy();
        LocalDateTime updateDate = updatedAppointment.getUpdateDate();
        String updateUser = updatedAppointment.getUpdatedBy();
        int customerID = updatedAppointment.getCustomerID();
        int userID = updatedAppointment.getUserID();
        int contactID = updatedAppointment.getContactID();

        String sqlQuery = "UPDATE appointments " +
                "SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, " +
                "Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, " +
                "Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";

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
        ps.setInt(15, appointmentID);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Deletes the selected appointment from the appointments database.
     *
     * @param appointmentID the Appointment_ID of the appointment being deleted from the appointments database.
     * */
    public static void deleteAppointment(Integer appointmentID) throws SQLException {
        String sqlQuery = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.accessDB().prepareStatement(sqlQuery);
        ps.setInt(1, appointmentID);

        ps.executeUpdate();
        ps.close();
    }

    /**
     * Returns the smallest Appointment_ID within the appointments database that isn't already assigned.
     *
     * @return the smallest integer-type Appointment_ID that isn't being used currently.
     * */
    public static Integer nextOpenAppointment() throws SQLException {
        int nextID = 0;
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

    /**
     * Returns a boolean about whether there are any scheduling conflicts present when adding or modifying an appointment.
     * First generates a list of appointments that share the contactID or customerID of the appointment passed in the parameter.
     * Each appointment is then checked to see if there are scheduling conflicts. If there is one or more conflict then the boolean is changed to false.
     * A True value indicates that both the customer and contact associated with the appointment are un-occupied at the given appointment time.
     * A False value indicates that there is a scheduling conflict and triggers an alert that prevents the user from adding the appointment.
     *
     * @param potentialAppointment the appointment that the user is attempting to add to the database.
     * @return a boolean indicating that the appointment is able to be added to the database.
     * */
    public static boolean checkIfOpen(AppointmentObject potentialAppointment) throws SQLException {
        boolean isOpen = true;

        ObservableList<AppointmentObject> matchingAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE Appointment_ID != ? AND (Contact_ID = ? " +
                "OR Customer_ID = ?)";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, potentialAppointment.getAppointmentID());
        ps.setInt(2, potentialAppointment.getContactID());
        ps.setInt(3, potentialAppointment.getCustomerID());

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            AppointmentObject newAppointment = new AppointmentObject(
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
            matchingAppointments.add(newAppointment);
        }

        for (AppointmentObject matchingAppointment : matchingAppointments) {
            if ((potentialAppointment.getStart().isBefore(matchingAppointment.getEnd()) && potentialAppointment.getEnd().isAfter(matchingAppointment.getStart()))
                || (potentialAppointment.getStart().isBefore(matchingAppointment.getStart()) && potentialAppointment.getEnd().isAfter(matchingAppointment.getStart()))) {
                isOpen = false;
            }
        }

        return isOpen;
    }
}
