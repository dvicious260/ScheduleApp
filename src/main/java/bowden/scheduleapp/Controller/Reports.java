package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.Helper.MonthlySummary;
import bowden.scheduleapp.Model.Appointments;
import bowden.scheduleapp.Model.Countries;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;

public class Reports {

    @FXML
    private TableView<MonthlySummary> appointmentType;

    @FXML
    private TableView<MonthlySummary> appointments;

    @FXML
    private Button buttonBack;

    @FXML
    private TableColumn<Countries, String> columnCountry;

    @FXML
    private TableColumn<?, ?> columnCustomerID;

    @FXML
    private TableColumn<?, ?> columnCustomers;

    @FXML
    private TableColumn<?, ?> columnDescription;

    @FXML
    private TableColumn<?, ?> columnEnd;

    @FXML
    private TableColumn<?, ?> columnID;

    @FXML
    private TableColumn<?, ?> columnLocation;

    @FXML
    private TableColumn<MonthlySummary, String> columnMonth;

    @FXML
    private TableColumn<?, ?> columnStart;

    @FXML
    private TableColumn<MonthlySummary, String> columnTOA;

    @FXML
    private TableColumn<MonthlySummary, String> columnTitle;

    @FXML
    private TableColumn<MonthlySummary, Integer> columnTotal;

    @FXML
    private TableColumn<?, ?> columnType;

    @FXML
    private ComboBox<?> comboContacts;

    @FXML
    private TableView<?> customerCountry;

    @FXML
    void back(ActionEvent event) {

    }
    public void initialize() {
        try {
            AppointmentsDaoImpl getSummary = new AppointmentsDaoImpl();
            // get the monthly summaries from the database
            List<MonthlySummary> monthlySummaries = getSummary.getMonthlySummary();

            // set the items of the appointments table view with the monthly summaries
            appointmentType.getItems().setAll(monthlySummaries);

            // set the cell value factories for each column
            columnMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
            columnTOA.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            columnTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

