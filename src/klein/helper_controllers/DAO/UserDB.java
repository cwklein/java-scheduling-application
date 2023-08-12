package klein.helper_controllers.DAO;

import klein.helper_controllers.JDBC;
import klein.helper_controllers.TimeConverter;
import klein.helper_controllers.UserObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class UserDB {
    public static int ValidateUser(String username, String password) throws SQLException {
        int userID = 0;

        String sqlQuery = "SELECT * FROM users WHERE User_Name = ? AND Password = ? ";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1,username);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()) {
            userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            UserObject.setUserID(userID);
            UserObject.setUserName(userName);
            System.out.println("User ID: " + userID + ", User Name: " + userName);
            System.out.println("Open at EST: " + TimeConverter.getEstOpenDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " + TimeZone.getTimeZone("America/New_York").getDisplayName());
            System.out.println("Open at Local: " + TimeConverter.getLocalOpenDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " + TimeZone.getDefault().getDisplayName());
            System.out.println("Close at EST: " + TimeConverter.getEstCloseDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " + TimeZone.getTimeZone("America/New_York").getDisplayName());
            System.out.println("Close at Local: " + TimeConverter.getLocalCloseDateTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " " + TimeZone.getDefault().getDisplayName());
        }
        return userID;
    }
}

