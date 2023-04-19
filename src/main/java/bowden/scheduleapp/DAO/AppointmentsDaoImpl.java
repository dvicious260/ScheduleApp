package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

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
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                Timestamp startTimestamp = rs.getTimestamp("Start", cal);
                Timestamp endTimestamp = rs.getTimestamp("End", cal);
                LocalDateTime start = DateTime.convertFromUTCtoLocal(startTimestamp);
                LocalDateTime end = DateTime.convertFromUTCtoLocal(endTimestamp);
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
        Connection conn = JDBC.openConnection();

        // check if the appointment overlaps with any existing appointments for the same customer
        String checkOverlapSql = "SELECT * FROM appointments WHERE Customer_ID = ? AND (? BETWEEN Start AND End OR ? BETWEEN Start AND End)";
        PreparedStatement checkOverlapPs = conn.prepareStatement(checkOverlapSql);
        checkOverlapPs.setInt(1, appointment.getCustomerID());
        checkOverlapPs.setString(2, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        checkOverlapPs.setString(3, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
        ResultSet overlapRs = checkOverlapPs.executeQuery();
        if (overlapRs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment overlaps with an existing appointment for the same customer.", ButtonType.OK);
            alert.showAndWait();

            return false;
        }
        // get the business hours in the user's local time zone
        ObservableList<LocalTime> businessHours = DateTime.getBusinessHoursInTimeZone(ZoneId.systemDefault());
        LocalTime startBusinessHours = businessHours.get(0);
        LocalTime endBusinessHours = businessHours.get(businessHours.size() - 1);

        // convert the appointment start and end times to the user's local time zone
        ZonedDateTime startAppointmentLocal = appointment.getStart().atZone(ZoneId.systemDefault());
        ZonedDateTime endAppointmentLocal = appointment.getEnd().atZone(ZoneId.systemDefault());

        // check if the appointment start and end times fall within the business hours
        if (startAppointmentLocal.toLocalTime().isBefore(startBusinessHours) || endAppointmentLocal.toLocalTime().isAfter(endBusinessHours)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside of business hours");
            alert.setHeaderText("Can't Schedule Appointment");
            alert.setContentText("The time you entered is outside of business hours.\nThe business hours are 8AM-10PM EST");
            alert.showAndWait();

            return false;
        }

        // if there are no overlaps, insert the new appointment
        String insertSql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?, NOW(), USER(), NOW(), USER(), ?,?,?)";
        PreparedStatement insertPs = conn.prepareStatement(insertSql);
        insertPs.setInt(1, appointment.getAppointmentID());
        insertPs.setString(2, appointment.getTitle());
        insertPs.setString(3, appointment.getDescription());
        insertPs.setString(4, appointment.getLocation());
        insertPs.setString(5, appointment.getType());
        insertPs.setString(6, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        insertPs.setString(7, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
        insertPs.setInt(8, appointment.getCustomerID());
        insertPs.setInt(8, appointment.getCustomerID());
        insertPs.setInt(9, appointment.getUserID());
        insertPs.setInt(10, appointment.getContactID());

        int rowsInserted = insertPs.executeUpdate();

        System.out.println("Appointment added");
        return rowsInserted > 0; // return true if the insert succeeded
    }

    /*public static boolean updateAppointment(Appointments appointment) throws SQLException {
        Connection conn = JDBC.openConnection();

        // check if the appointment overlaps with any existing appointments for the same customer
        String checkOverlapSql = "SELECT * FROM appointments WHERE Customer_ID = ? AND (? BETWEEN Start AND End OR ? BETWEEN Start AND End)";
        PreparedStatement checkOverlapPs = conn.prepareStatement(checkOverlapSql);
        checkOverlapPs.setInt(1, appointment.getCustomerID());
        checkOverlapPs.setString(2, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        checkOverlapPs.setString(3, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
        ResultSet overlapRs = checkOverlapPs.executeQuery();
        if (overlapRs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment overlaps with an existing appointment for the same customer.", ButtonType.OK);
            alert.showAndWait();

            return false;
        }
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = USER(), Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setString(5, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        ps.setString(6, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
        ps.setInt(7, appointment.getCustomerID());
        ps.setInt(8, appointment.getUserID());
        ps.setInt(9, appointment.getContactID());
        ps.setInt(10, appointment.getAppointmentID());




        int rowsUpdated = ps.executeUpdate();

        return rowsUpdated > 0;
    }*/
    public static boolean updateAppointment(Appointments appointment) throws SQLException {
        Connection conn = JDBC.openConnection();

        // check if the appointment overlaps with any existing appointments for the same customer
        String checkOverlapSql = "SELECT * FROM appointments WHERE Customer_ID = ? AND (? BETWEEN Start AND End OR ? BETWEEN Start AND End)";
        PreparedStatement checkOverlapPs = conn.prepareStatement(checkOverlapSql);
        checkOverlapPs.setInt(1, appointment.getCustomerID());
        checkOverlapPs.setString(2, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        checkOverlapPs.setString(3, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
        ResultSet overlapRs = checkOverlapPs.executeQuery();
        if (overlapRs.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment overlaps with an existing appointment for the same customer.", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        // get the business hours in the user's local time zone
        ObservableList<LocalTime> businessHours = DateTime.getBusinessHoursInTimeZone(ZoneId.systemDefault());
        LocalTime startBusinessHours = businessHours.get(0);
        LocalTime endBusinessHours = businessHours.get(businessHours.size() - 1);

        // convert the appointment start and end times to the user's local time zone
        ZonedDateTime startAppointmentLocal = appointment.getStart().atZone(ZoneId.systemDefault());
        ZonedDateTime endAppointmentLocal = appointment.getEnd().atZone(ZoneId.systemDefault());

        // check if the appointment start and end times fall within the business hours
        if (startAppointmentLocal.toLocalTime().isBefore(startBusinessHours) || endAppointmentLocal.toLocalTime().isAfter(endBusinessHours)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Outside of business hours");
            alert.setHeaderText("Can't Schedule Appointment");
            alert.setContentText("The time you entered is outside of business hours.\nThe business hours are 8AM-10PM EST");
            alert.showAndWait();

            return false;
        }

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = USER(), Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setString(5, DateTime.convertLocalToUTC(appointment.getStart(), ZoneId.systemDefault()).toString());
        ps.setString(6, DateTime.convertLocalToUTC(appointment.getEnd(), ZoneId.systemDefault()).toString());
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

    public static boolean deleteAppointment(int appointmentID) throws SQLException {
        Appointments appointmentToDelete = getAppointment(appointmentID);
        String appointmentIDString = String.valueOf(appointmentID);
        String appointmentType = appointmentToDelete.getType();
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsDeleted = ps.executeUpdate();

        Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION);
        deletedAlert.setTitle("Appointment Canceled");
        deletedAlert.setHeaderText("Appointment canceled successfully");
        deletedAlert.setContentText(appointmentType + " appointment with ID " + appointmentIDString + " has been canceled");
        deletedAlert.showAndWait();

        return rowsDeleted > 0;

    }
    public static int getMaxAppointmentID() throws SQLException {
        int maxId = 0;
        try (Connection connection = JDBC.openConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT MAX(Appointment_ID) AS Max_ID FROM appointments";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                maxId = resultSet.getInt("Max_ID");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            throw ex;
        }
        return maxId;
    }
    public static void checkUpcomingAppointments() {
        ObservableList<Appointments> appointments = getAllAppointments("");
        for (Appointments appointment : appointments) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime fifteenMinutesFromNow = now.plusMinutes(15);
            if (appointment.getStart().isBefore(fifteenMinutesFromNow) && appointment.getStart().isAfter(now)) {
                // Display an alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Upcoming appointment");
                alert.setHeaderText("You have an appointment coming up!");
                alert.setContentText("You have an appointment with id " + appointment.getAppointmentID() +" titled "  + appointment.getTitle() + " in less than 15 minutes at " + appointment.getStart().toLocalTime() + " on " + appointment.getStart().toLocalDate() + ".");
                Optional<ButtonType> result = alert.showAndWait();
            }else {
                // Display an alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No upcoming appointments");
                alert.setHeaderText("No upcoming appointments");
                alert.setContentText("You have no appointments within the next 15 minutes");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }
}
