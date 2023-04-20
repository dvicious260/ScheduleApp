package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.CountriesDaoImpl;
import bowden.scheduleapp.DAO.CustomersDAOImpl;
import bowden.scheduleapp.DAO.DivisionsDaoImpl;
import bowden.scheduleapp.Model.Countries;
import bowden.scheduleapp.Model.Customer;
import bowden.scheduleapp.Model.FirstLevelDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    void saveCustomer(ActionEvent event) throws IOException, SQLException {
        if (modifyCustomerName.getText().isEmpty() || modifyCustomerAddress.getText().isEmpty() || modifyCustomerPhone.getText().isEmpty() || modifyCustomerPostal.getText().isEmpty() || modifyCustomerState.getValue() == null || modifyCustomerCountry.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Fields");
            alert.setHeaderText("There are blank fields");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        String name = modifyCustomerName.getText();
        int id = Integer.parseInt(modifyCustomerID.getText());
        String address = modifyCustomerAddress.getText();
        String postal = modifyCustomerPostal.getText();
        String phone = modifyCustomerPhone.getText();
        int divisionId = modifyCustomerState.getSelectionModel().getSelectedItem().getDivisionID();

        // Get the existing customer record from the database
        Customer existingCustomer = CustomersDAOImpl.getCustomer(id);
        existingCustomer.setName(name);
        existingCustomer.setAddress(address);
        existingCustomer.setPostalCode(postal);
        existingCustomer.setPhone(phone);
        existingCustomer.setDivisionID(divisionId);

        // Update the existing customer record with the new information
        try {
            CustomersDAOImpl.updateCustomer(existingCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Navigate back to the home screen
        home(event);
    }


    @FXML
    public void sendCustomer(Customer customer) throws SQLException {
        modifyCustomerName.setText(customer.getName());
        modifyCustomerID.setText(String.valueOf(customer.getId()));
        modifyCustomerAddress.setText(customer.getAddress());
        modifyCustomerPhone.setText(customer.getPhone());
        modifyCustomerPostal.setText(customer.getPostalCode());

        // populate the country combo box
        ObservableList<Countries> countries = CountriesDaoImpl.getAllCountries();
        modifyCustomerCountry.setItems(countries);

        // set the selected country
        modifyCustomerCountry.setValue(customer.getCountry());

        // set the selected country using a lambda function to listen for changes in the combo box
        modifyCustomerCountry.getSelectionModel().selectedItemProperty().addListener((observableValue, oldCountry, newCountry) -> {
            // Populate the divisions combo box based on the selected country
            ObservableList<FirstLevelDivisions> divisions = DivisionsDaoImpl.getDivisionsByCountryId(newCountry.getCountryID());
            modifyCustomerState.setItems(divisions);

            // If the customer has a division in the new country, select it in the divisions combo box
            try {
                if (customer.getCountry().equals(newCountry) && customer.getDivision() != null && divisions.contains(customer.getDivision())) {
                    modifyCustomerState.setValue(customer.getDivision());
                } else {
                    modifyCustomerState.setValue(divisions.get(0));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        // populate the division combo box based on the selected country
        ObservableList<FirstLevelDivisions> divisions = DivisionsDaoImpl.getDivisionsByCountryId(customer.getCountry().getCountryID());
        modifyCustomerState.setItems(divisions);

        // set the selected division
        modifyCustomerState.getSelectionModel().select(customer.getDivision());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyCustomerID.setDisable(true);
    }
}