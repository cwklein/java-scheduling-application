package main.helper_controllers.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.helper_controllers.JDBC;
import main.helper_controllers.AppointmentObj;
import main.helper_controllers.UserObj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentDB {
    public static ObservableList<AppointmentObj> getAllAppointments() throws SQLException {
        ObservableList<AppointmentObj> userAppointments = FXCollections.observableArrayList();

        String sqlQuery = "SELECT * FROM appointments WHERE User_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setInt(1, UserObj.userID);

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
                    rs.getInt("Customer_ID"),
                    rs.getInt("User_ID")
            );
            userAppointments.add(newAppt);
        }
        return userAppointments;
    }
}
