package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.ContactsDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.DAO.UserDaoImpl;
import bowden.scheduleapp.Helper.ContactsHelper;
import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Helper.UserHelper;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.Users;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

import static bowden.scheduleapp.Helper.Methods.home;

public class ModifyAppointment implements Initializable {
    @FXML
    private TextField appointmentIDTextField;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<LocalTime> endComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField locationTextField;


    @FXML
    private ComboBox<LocalTime> startComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private ComboBox<Users> userComboBox;
    @FXML
    void cancel(ActionEvent event) throws IOException{
        home(event);
    }

    @FXML
    public void sendAppointment(Appointments appointments) throws SQLException {
        appointmentIDTextField.setText(String.valueOf(appointments.getAppointmentID()));
        titleTextField.setText(appointments.getTitle());
        descriptionTextField.setText(appointments.getDescription());
        locationTextField.setText(appointments.getLocation());
        contactComboBox.setItems(ContactsHelper.getAllContacts());
        //Get Contact by ID
        Contacts contact = ContactsDaoImpl.getContactById(appointments.getContactID());
        contactComboBox.setValue(contact);

        typeTextField.setText(appointments.getType());
        startComboBox.setItems(DateTime.getBusinessHours());
        endComboBox.setItems(DateTime.getBusinessHours());
        Timestamp startTimestamp = appointments.getStart();
        LocalDateTime startLocalDateTime = startTimestamp.toLocalDateTime();
        Timestamp endTimestamp = appointments.getEnd();
        LocalDateTime endLocalDateTime = endTimestamp.toLocalDateTime();
        startDatePicker.setValue(startLocalDateTime.toLocalDate());
        startComboBox.setValue(startLocalDateTime.toLocalTime());
        endDatePicker.setValue(endLocalDateTime.toLocalDate());
        endComboBox.setValue(endLocalDateTime.toLocalTime());


        customerComboBox.setItems(CustomersDAOImpl.getAllCustomers());
        Customer customer = CustomersDAOImpl.getCustomer(appointments.getCustomerID());
        customerComboBox.setValue(customer);
        userComboBox.setItems(UserHelper.getAllUsers());
        Users user = UserDaoImpl.getUser(appointments.getUserID());
        System.out.println("User retrieved from database: " + user);

        userComboBox.setValue(user);



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDTextField.setDisable(true);
        appointmentIDTextField.setText("");
    }
}