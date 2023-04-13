package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomersDAOImpl implements CustomersDAO {
    /*public boolean insertCustomer(Customer customer){}
    public boolean updateCustomer(int customerID){}
    public boolean deleteCustomer(int customerID){}
    public boolean getCustomer(int customerID){}*/

    public ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {

            String sqlGetCustomers = "SELECT Customer_ID, Customer_Name, Address,Postal_Code,Phone,customers.Division_ID, Division\n" +
                    "FROM customers\n" +
                    "JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID;";
            PreparedStatement ps = JDBC.openConnection().prepareStatement(sqlGetCustomers);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostal = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int customerDivisionID = rs.getInt("Division_ID");
                String customerDivisionName = rs.getString("Division");

                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, customerDivisionID, customerDivisionName);
                allCustomers.add(customer);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }return allCustomers;
    }


}
