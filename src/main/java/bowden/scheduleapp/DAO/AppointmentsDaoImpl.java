package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.DivisionsHelper;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsDaoImpl {
    public static ObservableList<Appointments> getAllAppointments(String filter) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        String sqlGetAppointments = "SELECT * FROM appointments";
        if (!filter.isEmpty()) {
            sqlGetAppointments += " WHERE " + filter;
        }
        try {
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sqlGetAppointments);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                //System.out.println(appointment.getStart());
                allAppointments.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allAppointments;
    }

        public static boolean insertAppointment(Appointments appointment) throws SQLException {
        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?, NOW(), USER(), NOW(), USER(), ?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointment.getAppointmentID());
        ps.setString(2, appointment.getTitle());
        ps.setString(3, appointment.getDescription());
        ps.setString(4, appointment.getLocation());
        ps.setString(5, appointment.getType());
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
        ps.setInt(8, appointment.getCustomerID());
        ps.setInt(9, appointment.getUserID());
        ps.setInt(10, appointment.getContactID());

        int rowsInserted = ps.executeUpdate();

        System.out.println("Appointment added");
        return rowsInserted > 0; // return true if the insert succeeded
    }
    public static boolean updateAppointment(Appointments appointment) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = USER(), Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        ps.setInt(7, appointment.getCustomerID());
        ps.setInt(8, appointment.getUserID());
        ps.setInt(9, appointment.getContactID());
        ps.setInt(10, appointment.getAppointmentID());




        int rowsUpdated = ps.executeUpdate();

        return rowsUpdated > 0;
    }
    public static Appointments getAppointment(int appointmentID) throws SQLException {
        Connection conn = JDBC.openConnection();
        String query = "SELECT * FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, appointmentID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");

            return new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
        }
        return null;
    }
}
