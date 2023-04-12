package bowden.scheduleapp.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bowden.scheduleapp.Helper.Methods.home;

public class AddAppointment implements Initializable {
    @FXML
    private TextField appointmentID;

    @FXML
    void cancel(ActionEvent event) throws IOException{
        home(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentID.setDisable(true);
        appointmentID.setText("Auto Generated");

    }
}