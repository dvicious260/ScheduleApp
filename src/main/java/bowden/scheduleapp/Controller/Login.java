package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.LoginDaoImpl;
import bowden.scheduleapp.Helper.JDBC;
import bowden.scheduleapp.Main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private Label labelTimezone;

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordText;

    @FXML
    private Button quitButton;

    @FXML
    private TextField usernameText;

    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        LoginDaoImpl loginDao = new LoginDaoImpl();
        boolean loginSuccessful = loginDao.getLogin(username, password);

        if (loginSuccessful){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/mainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid login");
            alert.setContentText("The entered username or password is incorrect");
            alert.showAndWait();
        }

    }

    @FXML
    void quit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zoneId = ZoneId.systemDefault();
        String zone = String.valueOf(zoneId);
        labelTimezone.setText(zone);

    }
}
