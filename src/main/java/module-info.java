module com.example.battleshipfpoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.battleshipfpoe to javafx.fxml;
    exports com.example.battleshipfpoe;
    exports com.example.battleshipfpoe.Controller;
    opens com.example.battleshipfpoe.Controller to javafx.fxml;



}