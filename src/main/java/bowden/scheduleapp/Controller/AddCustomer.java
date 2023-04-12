package bowden.scheduleapp.Controller;

import bowden.scheduleapp.Helper.Methods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static bowden.scheduleapp.Helper.Methods.home;

public class AddCustomer implements Initializable {


    @FXML
    private TextField newCustomerAddress;

    @FXML
    private ComboBox<?> newCustomerCountry;

    @FXML
    private TextField newCustomerID;

    @FXML
    private TextField newCustomerName;

    @FXML
    private TextField newCustomerPhone;

    @FXML
    private ComboBox<?> newCustomerState;

    @FXML
    private Button newCustomerCancel;

    @FXML
    private Button newCustomerSave;

    @FXML
    public static void cancelAdd(ActionEvent event) throws IOException {
        home(event);
    }

    @FXML
    void saveCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newCustomerID.setDisable(true);
        newCustomerID.setText("Auto Generated");

    }
}