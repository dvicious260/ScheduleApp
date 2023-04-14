package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Model.Customer;
import javafx.collections.ObservableList;

public interface AppointmentsDao {
    public ObservableList<Customer> getAllAppointments();

    boolean insertAppointment(Customer customer);

    public boolean updateAppointment();
    public void getAppointment();
    public boolean deleteAppointment();
}
