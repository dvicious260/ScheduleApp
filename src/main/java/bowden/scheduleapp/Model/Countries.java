package bowden.scheduleapp.Model;

import bowden.scheduleapp.Helper.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Countries {
    int countryID;
    String countryName;

    public Countries(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return countryName;
    }

    public static Countries getCountry(Countries country) {
        Countries matchingCountry = null;

        try (Connection connection = JDBC.openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM countries WHERE Country = ?")) {
            statement.setString(1, country.getCountryName());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Country_ID");
                    String name = resultSet.getString("Country");

                    matchingCountry = new Countries(id, name);
                    break; // Stop searching after finding the first match
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return matchingCountry;
    }


}
