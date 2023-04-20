package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.DAO.ContactsDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAO;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.Helper.CountryStats;
import bowden.scheduleapp.Helper.MonthlySummary;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Countries;
import bowden.scheduleapp.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static bowden.scheduleapp.DAO.AppointmentsDaoImpl.getAppointmentsByCustomer;
import static bowden.scheduleapp.Helper.Methods.home;

public class Reports {

    @FXML
    private TableView<MonthlySummary> appointmentType;

    @FXML
    private TableView<Appointments> appointmentsContact;

    @FXML
    private Button buttonBack;

    @FXML
    private TableColumn<CountryStats, String> columnCountry;

    @FXML
    private TableColumn<Appointments, Integer> columnCustomerID;

    @FXML
    private TableColumn<CountryStats, Integer> columnCustomers;

    @FXML
    private TableColumn<Appointments, String> columnDescription;

    @FXML
    private TableColumn<Appointments, LocalDateTime> columnEnd;

    @FXML
    private TableColumn<Appointments, Integer> columnID;

    @FXML
    private TableColumn<Appointments, String> columnLocation;

    @FXML
    private TableColumn<MonthlySummary, String> columnMonth;

    @FXML
    private TableColumn<Appointments, LocalDateTime> columnStart;

    @FXML
    private TableColumn<MonthlySummary, String> columnTOA;

    @FXML
    private TableColumn<MonthlySummary, String> columnTitle;

    @FXML
    private TableColumn<MonthlySummary, Integer> columnTotal;

    @FXML
    private TableColumn<Appointments, String> columnType;

    @FXML
    private ComboBox<Contacts> comboContacts;

    @FXML
    private TableView<CountryStats> customerCountry;

    @FXML
    void back(ActionEvent event) throws IOException {
        home(event);

    }

    public void initialize() throws SQLException {
        try {
            AppointmentsDaoImpl getSummary = new AppointmentsDaoImpl();
            // get the monthly summaries from the database
            List<MonthlySummary> monthlySummaries = getSummary.getMonthlySummary();

            // set the items of the appointments table view with the monthly summaries
            appointmentType.getItems().setAll(monthlySummaries);

            // set the cell value factories for each column
            columnMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            columnTOA.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            // populate the combo box with contacts
            ContactsDaoImpl contactsDao = new ContactsDaoImpl();
            ObservableList<Contacts> contacts = FXCollections.observableArrayList(ContactsDaoImpl.getAllContacts());
            comboContacts.setItems(contacts);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        // set a default value for the combo box and load appointments for the selected contact
        if (comboContacts.getItems().size() > 0) {
            comboContacts.setValue(comboContacts.getItems().get(0));
            Contacts selectedContact = comboContacts.getValue();
            int contactID = selectedContact.getContactID();
            ObservableList<Appointments> appointments = getAppointmentsByCustomer(contactID);
            appointmentsList.setAll(appointments);
        }

        // set the cell value factories for each column
        columnID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        columnEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        // set the items of the appointments table view with the list of appointments
        appointmentsContact.setItems(appointmentsList);

        // Add an event handler to the combo box to update the table view
        comboContacts.setOnAction(event -> {
            // Get the selected customer
            Contacts selectedContact = comboContacts.getValue();
            int contactID = selectedContact.getContactID();

            ObservableList<Appointments> appointments = getAppointmentsByCustomer(contactID);

            // Set the items of the table view to the list of appointments
            appointmentsList.setAll(appointments);

            // set the items of the appointments table view with the list of appointments
            appointmentsContact.setItems(appointmentsList);
        });

        // populate the customer country table view with country statistics
        CustomersDAOImpl customersDAO = new CustomersDAOImpl();
        List<CountryStats> countryStatistics = customersDAO.getCountryStats();
        customerCountry.getItems().setAll(countryStatistics);

        // set the cell value factories for each column
        columnCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        columnCustomers.setCellValueFactory(new PropertyValueFactory<>("customerCount"));

    }

}

