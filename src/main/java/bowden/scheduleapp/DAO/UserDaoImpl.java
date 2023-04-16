package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.DivisionsHelper;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import bowden.scheduleapp.Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl {
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        try {

            String sqlGetCustomers = "SELECT User_ID, User_Name\n" +
                    "FROM users\n";
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sqlGetCustomers);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                Users user = new Users(userID, userName,password);
                allUsers.add(user);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }return allUsers;
    }


    public static boolean insertUser(Users user) throws SQLException {
        String sql = "INSERT INTO users (User_ID, User_Name, Password) VALUES (?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, user.getUserID());
        ps.setString(2, user.getUserName());
        ps.setString(3, user.getPassword());

        int rowsInserted = ps.executeUpdate();

        return rowsInserted > 0; // return true if the insert succeeded
    }


    public static boolean updateUser(Users user) throws SQLException {
        String sql = "UPDATE users SET User_Name = ?, Password = ?, Last_Update = NOW(), Last_Updated_By = USER() WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassword());
        ps.setInt(3, user.getUserID());

        int rowsUpdated = ps.executeUpdate();

        return rowsUpdated > 0;
    }


    public static Users getUser(int userID) throws SQLException {
        Connection conn = JDBC.openConnection();
        String query = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            return new Users(userID, userName, password);
        }
        return null;
    }


    public static boolean deleteUser(int userID) throws SQLException {
        String sql = "DELETE FROM users WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        int rowsDeleted = ps.executeUpdate();
        return rowsDeleted > 0;
    }
}
