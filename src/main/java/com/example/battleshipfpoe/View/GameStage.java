package com.example.battleshipfpoe.View;

import com.example.battleshipfpoe.Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main stage for the "BattleShip" game.
 * This class is responsible for loading the game scene, initializing the game controller,
 * and managing the singleton instance of the game stage.
 */
public class GameStage extends Stage {

    private final GameController gameController;

    /**
     * Constructor to initialize the GameStage.
     * This method loads the FXML view for the game, initializes the game controller,
     * and configures the stage properties such as its title, icon, and resizability.
     *
     * @throws IOException if there is an error loading the FXML file.
     */
    public GameStage() throws IOException {
        // Load the FXML file and initialize the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();

        // Set up the scene and stage properties
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("BattleShip");
        setIcon("/com/example/battleshipfpoe/images/favicon.png");
        setResizable(false);

        // Show the stage
        show();
    }

    /**
     * Sets the window icon for the game stage.
     *
     * @param iconPath The relative path to the icon image.
     */
    private void setIcon(String iconPath) {
        getIcons().add(new Image(String.valueOf(getClass().getResource(iconPath))));
    }

    /**
     * Returns the GameController instance associated with this GameStage.
     *
     * @return The GameController instance managing the game's logic and interactions.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * A private static nested class used to manage the singleton instance of GameStage.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE;
    }

    /**
     * Retrieves the singleton instance of GameStage. If the instance doesn't exist, it initializes a new one.
     *
     * @return The singleton instance of GameStage.
     * @throws IOException if there is an error creating the GameStage instance.
     */
    public static GameStage getInstance() throws IOException {
        if (GameStageHolder.INSTANCE == null) {
            GameStageHolder.INSTANCE = new GameStage();
        }
        return GameStageHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of GameStage, if it exists.
     * This method closes the current stage and sets the instance to null,
     * effectively deleting the existing instance.
     */
    public static void deleteInstance() {
        if (GameStageHolder.INSTANCE != null) {
            GameStageHolder.INSTANCE.close();
            GameStageHolder.INSTANCE = null;
        }
    }
}
