package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.DAO.ContactsDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.DAO.UserDaoImpl;
import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Model.*;
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

public class AddAppointment implements Initializable {
    @FXML
    private TextField appointmentID;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;
    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<Customer> customerComboBox;
    @FXML
    private ComboBox<Users> userComboBox;
    @FXML
    private ComboBox<LocalTime> endComboBox;
    @FXML
    private ComboBox<LocalTime> startComboBox;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;


    @FXML
    void cancel(ActionEvent event) throws IOException{
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
        int id = AppointmentsDaoImpl.getMaxAppointmentID() + 1;
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

        Appointments newAppointment = new Appointments(id,title, description, location, type, start, end, customer.getId(), user.getUserID(), contact.getContactID());
        try {
            AppointmentsDaoImpl.insertAppointment(newAppointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        home(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentID.setDisable(true);
        try {
            appointmentID.setText(String.valueOf(AppointmentsDaoImpl.getMaxAppointmentID()+1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        contactComboBox.setItems(ContactsDaoImpl.getAllContacts());
        startComboBox.setItems(DateTime.getBusinessHours());
        endComboBox.setItems(DateTime.getBusinessHours());
        customerComboBox.setItems(CustomersDAOImpl.getAllCustomers());
        userComboBox.setItems(UserDaoImpl.getAllUsers());

    }
}