package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Model.Customer;
import javafx.collections.ObservableList;

public interface CustomersDAO {
    public ObservableList<Customer> getAllCustomers();
    
}
