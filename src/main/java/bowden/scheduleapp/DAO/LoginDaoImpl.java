package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.JDBC;

import java.sql.*;

public class LoginDaoImpl {

    public static boolean getLogin(String username, String password) throws SQLException {
        Connection conn = JDBC.openConnection();

        // Retrieve the stored username and password for the entered username
        String query = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String storedPassword = rs.getString("Password");

            // Compare the retrieved password with the user's entered password
            if (password.equals(storedPassword)) {
                conn.close();
                return true;
            }
        }
        conn.close();
        return false;
    }

}
