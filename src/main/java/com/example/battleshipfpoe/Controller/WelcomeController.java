package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.View.PreparationStage;
import com.example.battleshipfpoe.View.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button continueGame;

    @FXML
    private Button exitGame;

    @FXML
    private Button startGame;
    WelcomeStage menuStage;

    @FXML
    public void switchToMenu(ActionEvent actionEvent) throws IOException {
        // Reset the game state
        PreparationStage.getInstance();
        WelcomeStage.deleteInstance();
    }

    public void handleMinWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void handleCloseWindow(ActionEvent event) {
        WelcomeStage.deleteInstance();
    }
}