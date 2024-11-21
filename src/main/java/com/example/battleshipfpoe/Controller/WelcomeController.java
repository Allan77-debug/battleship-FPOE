package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.SaveSystem.*;
import com.example.battleshipfpoe.View.MenuStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private String saveFilePath = "gameState.ser";

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

    @FXML
    private void onLoadGameButtonPressed(ActionEvent event) throws IOException {
        // Initialize Save System and GameStateManager
        SaveInterface<GameProgress> serializedHandler = new SerializedSaveHandler<>();
        SaveSystem<GameProgress> saveSystem = new SaveSystem<>(serializedHandler);
        GameStateManager gameStateManager = new GameStateManager(saveSystem);

        try {
            GameProgress loadedGame = gameStateManager.loadGame(saveFilePath);

            if (loadedGame != null) {
                // Pass the loaded game state to the GameController
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
                Parent root = loader.load();

                GameController gameController = loader.getController();
                gameController.loadGameState();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.out.println("No saved game found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }


    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }
}