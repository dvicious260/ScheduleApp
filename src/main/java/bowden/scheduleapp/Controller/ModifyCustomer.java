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
public class ModifyCustomer implements Initializable {
    @FXML
    private TextField modifyCustomerAddress;

    @FXML
    private Button modifyCustomerCancel;

    @FXML
    private ComboBox<?> modifyCustomerCountry;

    @FXML
    private TextField modifyCustomerID;

    @FXML
    private TextField modifyCustomerName;

    @FXML
    private TextField modifyCustomerPhone;

    @FXML
    private Button modifyCustomerSave;

    @FXML
    private ComboBox<?> modifyCustomerState;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        home(event);
    }

    @FXML
    void saveCustomer(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyCustomerID.setDisable(true);
        modifyCustomerID.setText("Auto Generated");
    }
}