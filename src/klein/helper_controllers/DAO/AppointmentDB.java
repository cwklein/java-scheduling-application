package klein.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
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

    public static void addAppointment(AppointmentObj newAppt) throws SQLException {
        Integer appointmentID = newAppt.getAppointmentID();
        String title = newAppt.getTitle();
        String description = newAppt.getDescription();
        String location = newAppt.getLocation();
        String appointmentType = newAppt.getType();
        LocalDateTime startTime = newAppt.getStart();
        LocalDateTime endTime = newAppt.getEnd();
        LocalDateTime createDate = newAppt.getCreateDate();
        String createUser = newAppt.getCreateUser();
        LocalDateTime updateDate = newAppt.getUpdateDate();
        String updateUser = newAppt.getUpdateUser();
        Integer customerID = newAppt.getCustomerID();
        Integer userID = newAppt.getUserID();
        Integer contactID = newAppt.getContactID();

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
            System.out.println(i + " " + rs);
            if (!rs.next()) {
                System.out.println("Boop");
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
