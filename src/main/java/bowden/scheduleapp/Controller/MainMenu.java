package bowden.scheduleapp.Controller;

import bowden.scheduleapp.Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu {
    @FXML
    private Button btnAddAppointment;

    @FXML
    private Button btnAddCustomer;

    @FXML
    private Button btnDeleteAppointment;

    @FXML
    private Button btnDeleteCustomer;

    @FXML
    private Button btnModifyAppointment;

    @FXML
    private Button btnModifyCustomer;

    @FXML
    private RadioButton rbuttonViewAll;

    @FXML
    private RadioButton rbuttonViewMonth;

    @FXML
    private RadioButton rbuttonViewWeek;

    @FXML
    void addAppointment(ActionEvent event) {

    }

    @FXML
    void addCustomer(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/addCustomer.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void deleteAppointment(ActionEvent event) {

    }

    @FXML
    void deleteCustomer(ActionEvent event) {

    }

    @FXML
    void modifyAppointment(ActionEvent event) {

    }

    @FXML
    void modifyCustomer(ActionEvent event) {

    }

    @FXML
    void viewAll(ActionEvent event) {

    }

    @FXML
    void viewMonth(ActionEvent event) {

    }

    @FXML
    void viewWeek(ActionEvent event) {

    }
}