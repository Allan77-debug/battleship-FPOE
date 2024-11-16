package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.View.MenuStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;


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

    @FXML
    public void switchToMenu(ActionEvent actionEvent) throws IOException {
        // Reset the game state
        MenuStage.deleteInstance();

        MenuStage newMenuStage = MenuStage.getInstance();
        MenuController menuController = newMenuStage.getMenuController();
    }
}