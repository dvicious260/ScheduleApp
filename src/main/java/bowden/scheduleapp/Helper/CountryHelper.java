package bowden.scheduleapp.Helper;

import bowden.scheduleapp.Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryHelper {

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

}
