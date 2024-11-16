package com.example.battleshipfpoe.View;

import com.example.battleshipfpoe.Controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents a stage for the "El Sol Eclipsado" game. This class is responsible for setting up
 * the game scene, loading the FXML view, initializing the game controller, and managing a singleton
 * instance of the game stage.
 */
public class GameStage extends Stage {
    private final GameController gameController;

    /**
     * Initializes a new instance of the GameStage. This constructor sets up the game
     * scene by loading the FXML view, initializing the game controller, and configuring the
     * stage properties such as its title, icon, and resizability.
     *
     * @throws IOException If there is an error loading the FXML file.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/game-view.fxml"));
        Parent root = loader.load();
        gameController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("BattleShip");
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/battleshipfpoe/images/favicon.png"))));
        setResizable(false);
        show();

    }

    /**
     * Retrieves the GameController instance associated with the current game stage.
     *
     * @return The GameController instance managing the game's logic and interactions.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * A placeholder class to hold the singleton instance of GameStage.
     * This nested static class ensures that the GameStage instance is lazily initialized
     * and provides a thread-safe way to manage the singleton instance.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE;

    }

    /**
     * Retrieves the singleton instance of GameStage. If the instance does not exist, it initializes a new one.
     *
     * @return The singleton instance of GameStage.
     * @throws IOException if there is an error creating the GameStage instance.
     */
    public static GameStage getInstance() throws IOException {
        GameStage.GameStageHolder.INSTANCE =
                GameStage.GameStageHolder.INSTANCE != null ?
                        GameStage.GameStageHolder.INSTANCE : new GameStage();
        return GameStage.GameStageHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of the GameStage, if it exists.
     * <p>
     * This method checks if the singleton instance of GameStage is not null.
     * If it exists, it closes the GameStage and sets the instance to null,
     * effectively deleting the existing instance.
     */
    public static void deleteInstance() {
        if (GameStageHolder.INSTANCE != null) {
            GameStageHolder.INSTANCE.close();
            GameStageHolder.INSTANCE = null;
        }
    }
}
