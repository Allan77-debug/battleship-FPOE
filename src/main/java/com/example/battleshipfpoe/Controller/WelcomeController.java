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


/**
 * This controller handles the actions in the welcome screen of the game,
 * including starting a new game, continuing an existing game, exiting the game,
 * and managing window actions like minimizing or closing the application.
 */
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

    private WelcomeStage menuStage;

    /**
     * Switches the current scene to the game menu, resetting game state.
     *
     * @param actionEvent the event that triggered the switch to the menu
     * @throws IOException if there is an issue loading the menu scene
     */
    @FXML
    public void switchToMenu(ActionEvent actionEvent) throws IOException {
        // Reset the game state and remove the current menu stage
        PreparationStage.getInstance();
        WelcomeStage.deleteInstance();
    }

    /**
     * Minimizes the application window when the minimize button is pressed.
     *
     * @param event the event triggered by pressing the minimize button
     */
    public void handleMinWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Closes the application window when the close button is pressed.
     *
     * @param event the event triggered by pressing the close button
     */
    public void handleCloseWindow(ActionEvent event) {
        // Deletes the instance of the welcome stage and closes the window
        WelcomeStage.deleteInstance();
    }

    /**
     * Loads a previously saved game and switches to the game scene if a saved game is found.
     *
     * @param event the event triggered by pressing the "Load Game" button
     * @throws IOException if there is an issue loading the saved game or the game scene
     */
    @FXML
    private void onLoadGameButtonPressed(ActionEvent event) throws IOException {
        // Initialize Save System and GameStateManager for loading a serialized game state
        SaveInterface<GameProgress> serializedHandler = new SerializedSaveHandler<>();
        SaveSystem<GameProgress> saveSystem = new SaveSystem<>(serializedHandler);
        SerializedGameStateManager serializedGameStateManager = new SerializedGameStateManager(saveSystem);

        try {
            // Load the saved game progress
            GameProgress loadedGame = serializedGameStateManager.loadGame(SerializedSaveFilePath);

            if (loadedGame != null) {
                // If a saved game exists, load the game view and pass the loaded state to the game controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
                Parent root = loader.load();

                GameController gameController = loader.getController();
                gameController.loadGameState();

                // Create and show a new game stage with the loaded game state
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // If no saved game is found, print a message
                System.out.println("No saved game found.");
            }
        } catch (IOException e) {
            // Print an error message if loading the game fails
            e.printStackTrace();
            System.out.println("Failed to load the game.");
        }
    }
}



