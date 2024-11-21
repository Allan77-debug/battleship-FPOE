package com.example.battleshipfpoe.View;

import com.example.battleshipfpoe.Controller.GameController;
import com.example.battleshipfpoe.Controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class PreparationStage extends Stage {  // Extendemos Stage para acceder a sus métodos

    private final MenuController menuController;  // Corregir la declaración del controlador

    /**
     * Initializes a new instance of the GameStage. This constructor sets up the game
     * scene by loading the FXML view, initializing the game controller, and configuring the
     * stage properties such as its title, icon, and resizability.
     *
     * @throws IOException If there is an error loading the FXML file.
     */
    public PreparationStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/battleshipfpoe/fxml/menu-view.fxml"));
        Parent root = loader.load();
        menuController = loader.getController();  // Asignar correctamente el controlador
        Scene scene = new Scene(root);
        setScene(scene);  // Establecer la escena
        setTitle("BattleShip");  // Establecer el título
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/battleshipfpoe/images/favicon.png"))));  // Agregar ícono
        setResizable(false);  // Hacer que la ventana no sea redimensionable
        show();  // Mostrar la ventana
    }

    public PreparationStage(MenuController menuController) {
        this.menuController = menuController;
    }

    /**
     * Retrieves the GameController instance associated with the current game stage.
     *
     * @return The GameController instance managing the game's logic and interactions.
     */
    public MenuController getMenuController() {
        return menuController;
    }

    /**
     * A placeholder class to hold the singleton instance of PreparationStage.
     * This nested static class ensures that the PreparationStage instance is lazily initialized
     * and provides a thread-safe way to manage the singleton instance.
     */
    private static class PreparationStageHolder {
        private static PreparationStage INSTANCE;
    }

    /**
     * Retrieves the singleton instance of PreparationStage. If the instance does not exist, it initializes a new one.
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
     * <p>
     * This method checks if the singleton instance of PreparationStage is not null.
     * If it exists, it closes the PreparationStage and sets the instance to null,
     * effectively deleting the existing instance.
     */
    public static void deleteInstance() {
        if (PreparationStageHolder.INSTANCE != null) {
            PreparationStageHolder.INSTANCE.close();
        }
    }
}



