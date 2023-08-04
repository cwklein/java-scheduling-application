package klein.helper_controllers.DAO;

import klein.helper_controllers.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDB {
    public static int ValidateUser(String username, String password) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE User_Name = ? AND Password = ? ";

        PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
        ps.setString(1,username);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int userid = rs.getInt("User_ID");
            System.out.println("User ID: " + userid);
            return userid;
        }
        return 0;
    }
}

