package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.DivisionsHelper;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentsDaoImpl {
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        try {

            String sqlGetAppointments = "SELECT Appointment_ID, Title, Description,Location,Type,Start,End,Customer_ID, User_ID, Contact_ID\n" +
                    "FROM appointments";
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sqlGetAppointments);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointments appointment = new Appointments(appointmentID, title, description, location, type, start, end, customerID, userID, contactID);
                //System.out.println(appointment.getStart());
                allAppointments.add(appointment);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }return allAppointments;
    }
}
