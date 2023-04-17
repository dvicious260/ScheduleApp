package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.DAO.CountriesDaoImpl;
import bowden.scheduleapp.DAO.DivisionsDaoImpl;

import bowden.scheduleapp.Model.Countries;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static bowden.scheduleapp.Helper.Methods.home;


public class AddCustomer implements Initializable {


    @FXML
    private TextField newCustomerAddress;

    @FXML
    private TextField newCustomerPostal;

    @FXML
    private ComboBox<Countries> newCustomerCountry;

    @FXML
    private TextField newCustomerID;

    @FXML
    private TextField newCustomerName;

    @FXML
    private TextField newCustomerPhone;

    @FXML
    private ComboBox<FirstLevelDivisions> newCustomerState;

    @FXML
    private Button newCustomerCancel;

    @FXML
    private Button newCustomerSave;

    @FXML
    void cancelAdd(ActionEvent event) throws IOException {
        home(event);
    }

    @FXML
    void saveCustomer(ActionEvent event) throws SQLException, IOException {
        int id = CustomersDAOImpl.getMaxCustomerId() + 1;
        String customerName = newCustomerName.getText();
        String customerPhone = newCustomerPhone.getText();
        String customerAddress = newCustomerAddress.getText();
        String customerPostal = newCustomerPostal.getText();
        FirstLevelDivisions division = newCustomerState.getSelectionModel().getSelectedItem();
        int divisionID = division.getDivisionID();

        Customer newCustomer = new Customer(id,customerName,customerAddress,customerPostal,customerPhone,divisionID,division);
        try {
            CustomersDAOImpl.insertCustomer(newCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        home(event);


    }
    @FXML
    void deleteCustomer(ActionEvent event) throws IOException{

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newCustomerID.setDisable(true);
        try {
            newCustomerID.setText(String.valueOf(CustomersDAOImpl.getMaxCustomerId()+1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
// populate the country combo box
        ObservableList<Countries> countries = CountriesDaoImpl.getAllCountries();
        newCustomerCountry.setItems(countries);

        // set up a listener for the country combo box to populate the division combo box
        newCustomerCountry.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCountry, newCountry) -> {
            ObservableList<FirstLevelDivisions> divisions = DivisionsDaoImpl.getDivisionsByCountryId(newCountry.getCountryID());
            newCustomerState.setItems(divisions);
        });
    }
}