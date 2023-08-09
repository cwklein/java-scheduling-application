package klein.helper_controllers.DAO;

import klein.helper_controllers.JDBC;
import klein.helper_controllers.TimeConverter;
import klein.helper_controllers.UserObj;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {
    public static int ValidateUser(String username, String password) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE User_Name = ? AND Password = ? ";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1,username);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            UserObj.setUserID(userID);
            UserObj.setUserName(userName);
            System.out.println("User ID: " + userID + ", User Name: " + userName);
            System.out.println("Open at EST: " + TimeConverter.getEstOpenDateTime());
            System.out.println("Open at Local: " + TimeConverter.getLocalOpenDateTime());
            System.out.println("Close at EST: " + TimeConverter.getEstCloseDateTime());
            System.out.println("Close at Local: " + TimeConverter.getLocalCloseDateTime());
            return userID;
        }
        return 0;
    }
}

