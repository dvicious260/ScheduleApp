package bowden.scheduleapp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ModifyAppointment {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}