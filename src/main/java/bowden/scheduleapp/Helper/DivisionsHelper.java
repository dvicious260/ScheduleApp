package bowden.scheduleapp.Helper;

import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DivisionsHelper {

    public static ObservableList<FirstLevelDivisions> getDivisionsByCountryId(int countryId) {
        ObservableList<FirstLevelDivisions> divisions = FXCollections.observableArrayList();

        try (Connection connection = JDBC.openConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Country_ID = ?")) {
            statement.setInt(1, countryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Division_ID");
                    String name = resultSet.getString("Division");

                    divisions.add(new FirstLevelDivisions(id, name));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return divisions;
    }
}
