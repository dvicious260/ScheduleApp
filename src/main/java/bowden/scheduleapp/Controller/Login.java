package bowden.scheduleapp.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordText;

    @FXML
    private Button quitButton;

    @FXML
    private TextField usernameText;

    @FXML
    void login(ActionEvent event) {
        String username = usernameText.getText();
        String password = passwordText.getText();

    }

    @FXML
    void quit(ActionEvent event) {

    }

}
