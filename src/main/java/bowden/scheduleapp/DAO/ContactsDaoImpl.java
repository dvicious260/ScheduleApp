package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static ObservableList<Contacts> getAllContacts(){
        ObservableList<Contacts> contacts = FXCollections.observableArrayList();

        try (Connection connection = JDBC.openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM contacts");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Contact_ID");
                String name = resultSet.getString("Contact_Name");
                String email = resultSet.getString("Email");

                contacts.add(new Contacts(id, name, email));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return contacts;
    }
}
