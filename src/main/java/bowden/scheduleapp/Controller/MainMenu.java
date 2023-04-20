package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.Main.Main;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.beans.property.SimpleStringProperty;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {
    private static Customer customerSelection;
    private static Appointments appointmentSelection;
    @FXML
    private TableView<Appointments> appointmentTable;
    @FXML
    private TableColumn<Appointments, String> colAppContactID;

    @FXML
    private TableColumn<Appointments, Integer> colAppCustomerID;

    @FXML
    private TableColumn<Appointments, String> colAppDesc;

    @FXML
    private TableColumn<Appointments, LocalDateTime> colAppEnd;

    @FXML
    private TableColumn<Appointments, Integer> colAppID;

    @FXML
    private TableColumn<Appointments, String> colAppLoc;

    @FXML
    private TableColumn<Appointments, LocalDateTime> colAppStart;

    @FXML
    private TableColumn<Appointments, String> colAppTitle;

    @FXML
    private TableColumn<Appointments, String> colAppType;

    @FXML
    private TableColumn<Appointments, Integer> colAppUserID;
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
    private Button buttonReports;

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
    void viewAll(ActionEvent event) {
        loadAppointments("");
    }

    @FXML
    void viewMonth(ActionEvent event) {
        // Filter by current month
        int month = LocalDateTime.now().getMonthValue();
        String filter = "MONTH(start) = " + month;
        loadAppointments(filter);
    }

    @FXML
    void viewWeek(ActionEvent event) {
        // Filter by current week
        LocalDateTime now = LocalDateTime.now();
        String filter = "WEEK(start) = WEEK('" + now + "')";
        loadAppointments(filter);
    }

    private void loadAppointments(String filter) {
        AppointmentsDaoImpl appointmentsDao = new AppointmentsDaoImpl();
        appointmentTable.setItems(AppointmentsDaoImpl.getAllAppointments(filter));
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
    void reports(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/reports.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteAppointment(ActionEvent event) throws SQLException {
        appointmentSelection = appointmentTable.getSelectionModel().getSelectedItem();
        if (appointmentSelection == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("No appointment selected");
            noSelectionAlert.setContentText("Please select a appointment to delete");
            noSelectionAlert.showAndWait();
        } else {
            Alert confirmAppointmentDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAppointmentDelete.setTitle("Delete appointment");
            confirmAppointmentDelete.setContentText("You are about to permanently delete this appointment. Are you sure you want to continue?");
            Optional<ButtonType> result = confirmAppointmentDelete.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentsDaoImpl.deleteAppointment(appointmentSelection.getAppointmentID());
                rbuttonViewAll.setSelected(true);
                appointmentTable.setItems(AppointmentsDaoImpl.getAllAppointments(""));
            }
        }

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
    void deleteCustomer(ActionEvent event) throws SQLException {
        customerSelection = customerTable.getSelectionModel().getSelectedItem();
        if (customerSelection == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.ERROR);
            noSelectionAlert.setTitle("No customer selected");
            noSelectionAlert.setContentText("Please select a customer to delete");
            noSelectionAlert.showAndWait();
        } else {
            Alert confirmCustomerDelete = new Alert(Alert.AlertType.CONFIRMATION);
            confirmCustomerDelete.setTitle("Delete Customer");
            confirmCustomerDelete.setContentText("You are about to permanently delete this customer. Are you sure you want to continue?");
            Optional<ButtonType> result = confirmCustomerDelete.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomersDAOImpl.deleteCustomer(customerSelection.getId());
                customerTable.setItems(CustomersDAOImpl.getAllCustomers());
            }
        }

    }

    @FXML
    void modifyAppointment(ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/bowden/scheduleapp/View/modifyAppointment.fxml"));
        fxmlLoader.load();

        ModifyAppointment modifyAppointment = fxmlLoader.getController();
        modifyAppointment.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void modifyCustomer(ActionEvent event) throws IOException, SQLException {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomersDAOImpl dao = new CustomersDAOImpl(); // Create an instance of the class
        customerTable.setItems(CustomersDAOImpl.getAllCustomers());
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerPostal.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        // Use a lambda expression to set the cell value factory for the "State/Province" column
        colCustomerState.setCellValueFactory(cellData -> {
            SimpleStringProperty property = new SimpleStringProperty();
            FirstLevelDivisions division = null;
            try {
                division = cellData.getValue().getDivision();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (division != null) {
                property.setValue(division.getDivisionName());
            } else {
                property.setValue("");
            }
            return property;
        });
        //Initialize appointments table
        //AppointmentsDaoImpl appDAO = new AppointmentsDaoImpl(); // Create an instance of the class
        rbuttonViewAll.setSelected(true);
        //appointmentTable.setItems(appDAO.getAllAppointments());
        colAppID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        colAppTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAppDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAppLoc.setCellValueFactory(new PropertyValueFactory<>("location"));
        colAppType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAppStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        colAppEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        colAppCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colAppUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colAppContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        loadAppointments("");


    }
}