package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Countries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsDaoImpl {
    public static Contacts getContactById(int contactID) throws SQLException {
        Connection conn = JDBC.openConnection();
        String query = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String contactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");
            return new Contacts(contactID, contactName, email);
        }
        return null;
    }
}
