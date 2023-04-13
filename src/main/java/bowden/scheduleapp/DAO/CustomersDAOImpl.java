package bowden.scheduleapp.DAO;

import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CustomersDAOImpl {
    /*public boolean insertCustomer(Customer customer){}
    public boolean updateCustomer(int customerID){}
    public boolean deleteCustomer(int customerID){}
    public boolean getCustomer(int customerID){}*/

    public static ObservableList<Customer> getAllCustomers() {
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

                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, customerDivisionID);
                allCustomers.add(customer);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }return allCustomers;
    }


    public static boolean insertCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?, NOW(), USER(), NOW(), USER(), ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getId());
        ps.setString(2, customer.getName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostalCode());
        ps.setString(5, customer.getPhone());
        ps.setInt(6, customer.getDivisionID());

        int rowsInserted = ps.executeUpdate();

        return rowsInserted > 0; // return true if the insert succeeded
    }


    public static boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = USER(), Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionID());
        ps.setInt(6, customer.getId());

        int rowsUpdated = ps.executeUpdate();

        return rowsUpdated > 0;
    }

    public static Customer getCustomer(int customerID) throws SQLException {
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divisionId = rs.getInt("Division_ID");
            return new Customer(id, name, address, postalCode, phone, divisionId);
        }

        return null;
    }

    public static boolean deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsDeleted = ps.executeUpdate();
        return rowsDeleted > 0;
    }


}
