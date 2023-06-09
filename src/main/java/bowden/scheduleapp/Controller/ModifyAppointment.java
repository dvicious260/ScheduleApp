package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.DAO.ContactsDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.DAO.UserDaoImpl;
import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Contacts;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    void cancel(ActionEvent event) throws IOException {
        home(event);
    }

    @FXML
    void save(ActionEvent event) throws IOException, SQLException {
        if (titleTextField.getText().isEmpty() || descriptionTextField.getText().isEmpty() || locationTextField.getText().isEmpty() || typeTextField.getText().isEmpty() || startDatePicker.getValue() == null || startComboBox.getValue() == null || endDatePicker.getValue() == null || endComboBox.getValue() == null || customerComboBox.getValue() == null || userComboBox.getValue() == null || contactComboBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("There are blank fields");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        int id = Integer.valueOf(appointmentIDTextField.getText());
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime startTime = startComboBox.getValue();
        LocalDateTime start = LocalDateTime.of(startDate, startTime);
        LocalDate endDate = endDatePicker.getValue();
        LocalTime endTime = endComboBox.getValue();
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        // Get selected contact and user
        Contacts contact = contactComboBox.getValue();
        Users user = userComboBox.getValue();

        // Get selected customer
        Customer customer = customerComboBox.getValue();


        // Create appointment object and call DAO method to save it
        Appointments existingAppointment = AppointmentsDaoImpl.getAppointment(id);
        existingAppointment.setTitle(title);
        existingAppointment.setDescription(description);
        existingAppointment.setLocation(location);
        existingAppointment.setStart(start);
        existingAppointment.setEnd(end);
        existingAppointment.setCustomerID(customer.getId());
        existingAppointment.setUserID(user.getUserID());
        existingAppointment.setContactID(contact.getContactID());
        try {
            AppointmentsDaoImpl.updateAppointment(existingAppointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        home(event);

    }

    @FXML
    public void sendAppointment(Appointments appointments) throws SQLException {
        appointmentIDTextField.setText(String.valueOf(appointments.getAppointmentID()));
        titleTextField.setText(appointments.getTitle());
        descriptionTextField.setText(appointments.getDescription());
        locationTextField.setText(appointments.getLocation());
        contactComboBox.setItems(ContactsDaoImpl.getAllContacts());
        //Get Contact by ID
        Contacts contact = ContactsDaoImpl.getContactById(appointments.getContactID());
        contactComboBox.setValue(contact);

        typeTextField.setText(appointments.getType());
        startComboBox.setItems(DateTime.getBusinessHours());
        endComboBox.setItems(DateTime.getBusinessHours());
        startDatePicker.setValue(appointments.getStart().toLocalDate());
        startComboBox.setValue(appointments.getStart().toLocalTime());
        endDatePicker.setValue(appointments.getEnd().toLocalDate());
        endComboBox.setValue(appointments.getEnd().toLocalTime());


        customerComboBox.setItems(CustomersDAOImpl.getAllCustomers());
        Customer customer = CustomersDAOImpl.getCustomer(appointments.getCustomerID());
        customerComboBox.setValue(customer);
        userComboBox.setItems(UserDaoImpl.getAllUsers());
        Users user = UserDaoImpl.getUser(appointments.getUserID());
        userComboBox.setValue(user);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDTextField.setDisable(true);
    }
}