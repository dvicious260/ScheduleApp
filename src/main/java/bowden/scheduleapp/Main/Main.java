package bowden.scheduleapp.Main;

import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Model.Customer;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static bowden.scheduleapp.DAO.CustomersDAOImpl.updateCustomer;
import static bowden.scheduleapp.DAO.CustomersDAOImpl.insertCustomer;
import static bowden.scheduleapp.DAO.CustomersDAOImpl.deleteCustomer;
import static bowden.scheduleapp.DAO.CustomersDAOImpl.getCustomer;





public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        // Insert a new customer
        //Customer customer = new Customer(4, "John Doe", "123 Main St", "12345", "555-1234", 1, null);
        //boolean insertResult = CustomersDAOImpl.insertCustomer(customer);
        //System.out.println("Insert result: " + insertResult);
        // Update the customer
        //customer.setName("Jane Doe");
        //boolean updateResult = CustomersDAOImpl.updateCustomer(customer);
        //System.out.println("Update result: " + updateResult);

        //Customer retrievedCustomer = CustomersDAOImpl.getCustomer(4);
        //System.out.println("Retrieved customer: " + retrievedCustomer.getName());

        //boolean deleteResult = CustomersDAOImpl.deleteCustomer(4);
        //System.out.println("Delete result: " + deleteResult);

        // Insert customer 1
        Customer customer1 = new Customer(4, "John Smith", "123 Main St", "12345", "555-1234", 1, null);


        // Insert customer 2
        Customer customer2 = new Customer(5, "Jane Doe", "456 Elm St", "67890", "555-5678", 2, null);


        // Insert customer 3
        Customer customer3 = new Customer(6, "Bob Johnson", "789 Oak St", "23456", "555-9012", 3, null);


        launch();
        //JDBC.openConnection();
        //JDBC.closeConnection();
    }
}