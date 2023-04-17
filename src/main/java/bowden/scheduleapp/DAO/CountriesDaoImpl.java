package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesDaoImpl {

    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countries = FXCollections.observableArrayList();

        try (Connection connection = JDBC.openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Country_ID");
                String name = resultSet.getString("Country");

                countries.add(new Countries(id, name));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return countries;
    }

    public static Countries getCountryById(int countryID) throws SQLException {
        Connection conn = JDBC.openConnection();
        String query = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String countryName = rs.getString("Country");
            return new Countries(countryID, countryName);
        }
        return null;
    }

}
