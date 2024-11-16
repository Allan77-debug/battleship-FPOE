module com.example.battleshipfpoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.battleshipfpoe to javafx.fxml;
    opens com.example.battleshipfpoe.Controller to javafx.fxml;
    exports com.example.battleshipfpoe;
    exports com.example.battleshipfpoe.Controller;
}