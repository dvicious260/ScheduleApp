package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Model.Customer;
import javafx.collections.ObservableList;

public interface AppointmentsDao {
    ObservableList<Customer> getAllAppointments();

    boolean insertAppointment(Customer customer);

    boolean updateAppointment();

    void getAppointment();

    boolean deleteAppointment();
}
