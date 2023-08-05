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
    public static ObservableList<AppointmentObj> getAllAppointments() throws SQLException {
        ObservableList<AppointmentObj> userAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, UserObj.getUserID());

        ResultSet rs = ps.executeQuery();

        userAppointments.clear();

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
}
