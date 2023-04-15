package bowden.scheduleapp.Controller;

import bowden.scheduleapp.Helper.ContactsHelper;
import bowden.scheduleapp.Helper.DateTime;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Contacts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
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
    private ComboBox<?> customerComboBox;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ComboBox<?> endComboBox;

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
    private ComboBox<?> userComboBox;
    @FXML
    void cancel(ActionEvent event) throws IOException{
        home(event);
    }

    @FXML
    public void sendAppointment(Appointments appointments){
        appointmentIDTextField.setText(String.valueOf(appointments.getAppointmentID()));
        titleTextField.setText(appointments.getTitle());
        descriptionTextField.setText(appointments.getDescription());
        locationTextField.setText(appointments.getLocation());
        contactComboBox.setItems(ContactsHelper.getAllContacts());
        typeTextField.setText(appointments.getType());
        startDatePicker.setValue(appointments.getStart().toLocalDate());
        startComboBox.setItems(DateTime.getBusinessHours());
        startComboBox.setValue(appointments.getStart().toLocalTime().minusHours(5));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentIDTextField.setDisable(true);
        appointmentIDTextField.setText("");
    }
}