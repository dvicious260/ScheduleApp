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
             PreparedStatement statement = connection.prepareStatement("SELECT Division_ID, Division, Country_ID FROM first_level_divisions WHERE Country_ID = ?")) {
            statement.setInt(1, countryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Division_ID");
                    String name = resultSet.getString("Division");
                    int countryId1 = resultSet.getInt("Country_ID");

                    divisions.add(new FirstLevelDivisions(id, name, countryId1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return divisions;
    }
    public static FirstLevelDivisions getDivisionById(int id) throws SQLException {
        FirstLevelDivisions division = null;

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.openConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryId = rs.getInt("COUNTRY_ID");

            division = new FirstLevelDivisions(divisionId, divisionName, countryId);
        }

        return division;
    }
}
