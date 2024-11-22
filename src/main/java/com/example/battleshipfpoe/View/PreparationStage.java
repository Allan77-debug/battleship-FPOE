package com.example.battleshipfpoe.View;

import com.example.battleshipfpoe.Controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Represents the preparation stage for the "BattleShip" game.
 * This class is responsible for setting up the menu scene, initializing the menu controller,
 * and managing the singleton instance of the preparation stage.
 */
public class PreparationStage extends Stage {

    private final MenuController menuController;  // Controller responsible for managing menu logic

    /**
     * Initializes a new instance of the PreparationStage.
     * This constructor sets up the menu scene by loading the FXML view, initializing the menu controller,
     * and configuring the stage properties such as its title, icon, and resizability.
     *
     * @throws IOException if there is an error loading the FXML file.
     */
    public PreparationStage() throws IOException {
        // Load the FXML file and initialize the menu controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/menu-view.fxml"));
        Parent root = loader.load();
        menuController = loader.getController();

        // Set up the scene and stage properties
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("BattleShip");
        setIcon("/com/example/battleshipfpoe/images/favicon.png");
        setResizable(false);
        initStyle(StageStyle.UNDECORATED);

        // Show the stage
        show();
    }

    /**
     * Initializes a new instance of the PreparationStage with a given MenuController.
     * This constructor can be used if a MenuController is already available or needs to be passed in.
     *
     * @param menuController The MenuController instance to be used.
     */
    public PreparationStage(MenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Sets the window icon for the preparation stage.
     *
     * @param iconPath The relative path to the icon image.
     */
    private void setIcon(String iconPath) {
        getIcons().add(new Image(String.valueOf(getClass().getResource(iconPath))));
    }

    /**
     * Retrieves the MenuController instance associated with the current preparation stage.
     *
     * @return The MenuController instance managing the menu's logic and interactions.
     */
    public MenuController getMenuController() {
        return menuController;
    }

    /**
     * A private static nested class used to manage the singleton instance of PreparationStage.
     */
    private static class PreparationStageHolder {
        private static PreparationStage INSTANCE;
    }

    /**
     * Retrieves the singleton instance of PreparationStage. If the instance doesn't exist, it initializes a new one.
     *
     * @return The singleton instance of PreparationStage.
     * @throws IOException if there is an error creating the PreparationStage instance.
     */
    public static PreparationStage getInstance() throws IOException {
        if (PreparationStage.PreparationStageHolder.INSTANCE == null) {
            PreparationStage.PreparationStageHolder.INSTANCE = new PreparationStage();
        }
        return PreparationStage.PreparationStageHolder.INSTANCE;
    }

    /**
     * Deletes the singleton instance of the PreparationStage, if it exists.
     * This method closes the current stage and sets the instance to null,
     * effectively deleting the existing instance.
     */
    public static void deleteInstance() {
        if (PreparationStage.PreparationStageHolder.INSTANCE != null) {
            PreparationStage.PreparationStageHolder.INSTANCE.close();
            PreparationStage.PreparationStageHolder.INSTANCE = null;
        }
    }
}
