package bowden.scheduleapp.Controller;

import bowden.scheduleapp.DAO.AppointmentsDaoImpl;
import bowden.scheduleapp.DAO.LoginDaoImpl;
import bowden.scheduleapp.Helper.UserActivityLogger;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {
    //private final static ResourceBundle LoginResourceBundle = ResourceBundle.getBundle("login", Locale.getDefault());


    @FXML
    private Label gettimezoneLabel;

    @FXML
    private Label labelTimezone;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordText;

    @FXML
    private Button quitButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    @FXML
    void login(ActionEvent event) throws SQLException, IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        LoginDaoImpl loginDao = new LoginDaoImpl();
        boolean loginSuccessful = LoginDaoImpl.getLogin(username, password);

        UserActivityLogger.logLoginAttempt(username, loginSuccessful); // log login attempt

        if (loginSuccessful) {
            AppointmentsDaoImpl.checkUpcomingAppointments();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/bowden/scheduleapp/View/mainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();

        } else {
            // Load the appropriate resource bundle for the alerts based on the user's locale
            ResourceBundle alertsBundle;
            if (Locale.getDefault().getLanguage().equals("fr")) {
                alertsBundle = ResourceBundle.getBundle("alerts_fr");
            } else {
                alertsBundle = ResourceBundle.getBundle("alerts_en");
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(alertsBundle.getString("alertTitle"));
            alert.setContentText(alertsBundle.getString("alertMessage"));
            alert.showAndWait();
        }

    }


    @FXML
    void quit(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Get the default resource bundle
        ResourceBundle defaultBundle = ResourceBundle.getBundle("login");

        // Get the user's locale
        Locale userLocale = Locale.getDefault();

        // Load the appropriate resource bundle for the user's locale, using the default bundle as a fallback
        ResourceBundle userBundle;
        if (userLocale.getLanguage().equals("fr")) {
            userBundle = ResourceBundle.getBundle("login_fr", userLocale);
        } else {
            userBundle = ResourceBundle.getBundle("login_en", userLocale);
        }

        // Set the translated text for each control
        loginLabel.setText(userBundle.getString("loginLabel"));
        passwordLabel.setText(userBundle.getString("passwordLabel"));
        usernameLabel.setText(userBundle.getString("usernameLabel"));
        gettimezoneLabel.setText(userBundle.getString("gettimezoneLabel"));
        loginButton.setText(userBundle.getString("loginButton"));
        quitButton.setText(userBundle.getString("quitButton"));
        ZoneId zoneId = ZoneId.systemDefault();
        String zone = String.valueOf(zoneId);
        labelTimezone.setText(zone);
    }

}
