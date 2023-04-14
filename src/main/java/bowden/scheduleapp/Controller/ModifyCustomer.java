package bowden.scheduleapp.Controller;

import bowden.scheduleapp.Helper.CountryHelper;
import bowden.scheduleapp.Helper.DivisionsHelper;
import bowden.scheduleapp.Helper.Methods;
import bowden.scheduleapp.Model.Countries;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bowden.scheduleapp.Helper.Methods.home;
public class ModifyCustomer implements Initializable {
    @FXML
    private TextField modifyCustomerAddress;

    @FXML
    private Button modifyCustomerCancel;

    @FXML
    private ComboBox<Countries> modifyCustomerCountry;

    @FXML
    private TextField modifyCustomerID;

    @FXML
    private TextField modifyCustomerPostal;

    @FXML
    private TextField modifyCustomerName;

    @FXML
    private TextField modifyCustomerPhone;

    @FXML
    private Button modifyCustomerSave;

    @FXML
    private ComboBox<FirstLevelDivisions> modifyCustomerState;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        home(event);
    }

    @FXML
    void saveCustomer(ActionEvent event) {

    }
    public void sendCustomer(Customer customer) throws SQLException {
        modifyCustomerName.setText(customer.getName());
        modifyCustomerID.setText("Auto generated: " + customer.getId());
        modifyCustomerAddress.setText(customer.getAddress());
        modifyCustomerPhone.setText(customer.getPhone());
        modifyCustomerPostal.setText(customer.getPostalCode());
        // populate the country combo box
        ObservableList<Countries> countries = CountryHelper.getAllCountries();
        modifyCustomerCountry.setItems(countries);

        // set up a listener for the country combo box to populate the division combo box
        modifyCustomerCountry.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCountry, newCountry) -> {
            ObservableList<FirstLevelDivisions> divisions = DivisionsHelper.getDivisionsByCountryId(newCountry.getCountryID());
            modifyCustomerState.setItems(divisions);
        });
        modifyCustomerCountry.setValue(customer.getCountry());
        modifyCustomerState.setValue(customer.getDivision());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyCustomerID.setDisable(true);
        modifyCustomerID.setText("Auto Generated");
    }
}