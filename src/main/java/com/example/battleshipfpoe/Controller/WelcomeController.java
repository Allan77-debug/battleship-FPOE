package com.example.battleshipfpoe.Controller;

import com.example.battleshipfpoe.Model.SaveSystem.*;
import com.example.battleshipfpoe.View.PreparationStage;
import com.example.battleshipfpoe.View.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomeController {

    private final String SerializedSaveFilePath = "SerializedGameState.ser";
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
    @FXML
    private void onLoadGameButtonPressed(ActionEvent event) throws IOException {
        // Initialize Save System and GameStateManager
        SaveInterface<GameProgress> serializedHandler = new SerializedSaveHandler<>();
        SaveSystem<GameProgress> saveSystem = new SaveSystem<>(serializedHandler);
        SerializedGameStateManager serializedGameStateManager = new SerializedGameStateManager(saveSystem);

        try {
            GameProgress loadedGame = serializedGameStateManager.loadGame(SerializedSaveFilePath);

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
}


