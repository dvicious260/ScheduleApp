package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Model.Customer;
import javafx.collections.ObservableList;

public interface CustomersDAO {
    ObservableList<Customer> getAllCustomers();

    boolean insertCustomer(Customer customer);

    boolean updateCustomer();

    void getCustomer();

    boolean deleteCustomer();

}
