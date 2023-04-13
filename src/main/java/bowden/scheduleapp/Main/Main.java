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
        //JDBC.openConnection();


        launch();
        //JDBC.openConnection();
        //JDBC.closeConnection();
    }
}