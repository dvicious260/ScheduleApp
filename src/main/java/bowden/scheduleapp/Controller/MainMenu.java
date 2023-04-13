package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.CustomersDAO;
import bowden.scheduleapp.Main.Main;
import bowden.scheduleapp.Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    @FXML
    private TableColumn<Customer, String> colCustomerAddress;
    @FXML
    private TableColumn<Customer, Integer> colCustomerID;

    @FXML
    private TableColumn<Customer, String> colCustomerName;

    @FXML
    private TableColumn<Customer, String> colCustomerPhone;

    @FXML
    private TableColumn<Customer, String> colCustomerPostal;

    @FXML
    private TableColumn<Customer, String> colCustomerState;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button btnLogout;
    @FXML
    private Button btnQuit;
    @FXML
    private Button btnAddAppointment;
    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnDeleteAppointment;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnModifyAppointment;

    @FXML
    private Button btnModifyCustomer;

    @FXML
    private RadioButton rbuttonViewAll;

    @FXML
    private RadioButton rbuttonViewMonth;

    @FXML
    private RadioButton rbuttonViewWeek;



    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/addAppointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteAppointment(ActionEvent event) {

    }
    @FXML
    void logout(ActionEvent event) {

    }
    @FXML
    void quit(ActionEvent event) {
        Alert quitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        quitAlert.setTitle("Quit Application");
        quitAlert.setContentText("Are you sure you want to quit?");
        Optional<ButtonType> result = quitAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void modifyAppointment(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/modifyAppointment.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void modifyCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/bowden/scheduleapp/View/modifyCustomer.fxml"));
        fxmlLoader.load();

        ModifyCustomer modifyCustomer = fxmlLoader.getController();
        modifyCustomer.sendCustomer(customerTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void viewAll(ActionEvent event) {

    }

    @FXML
    void viewMonth(ActionEvent event) {

    }

    @FXML
    void viewWeek(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rbuttonViewAll.setSelected(true);
        try {
            customerTable.setItems(CustomersDAO.getAllCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colCustomerState.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
    }
}