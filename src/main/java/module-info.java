module bowden.scheduleapp.scheduleapp {
    requires javafx.controls;
    requires javafx.fxml;


    /*opens bowden.scheduleapp to javafx.fxml;
    exports bowden.scheduleapp;*/
    /*exports bowden.scheduleapp.Model;
    opens bowden.scheduleapp.Model to javafx.fxml;*/
    exports bowden.scheduleapp.Controller;
    opens bowden.scheduleapp.Controller to javafx.fxml;
    exports bowden.scheduleapp.Main;
    opens bowden.scheduleapp.Main to javafx.fxml;
    /*exports bowden.scheduleapp.Main;
    opens bowden.scheduleapp.Main to javafx.fxml;*/
}