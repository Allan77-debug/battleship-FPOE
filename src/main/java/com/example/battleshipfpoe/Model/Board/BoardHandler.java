package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.io.Serial;
import java.io.Serializable;

/**
 * Handles the logic and rendering of the game board in the Battleship game.
 * Includes methods for managing ship placement, rendering board visuals, and tracking cell states.
 */
public class BoardHandler extends BoardBase implements Serializable {

    private static final Color BACKGROUND_COLOR_1 = Color.rgb(127, 205, 255);
    private static final Color SHIP_COLOR = Color.GRAY;

    private ArrayList<ArrayList<Integer>> board; // Logical state of the board
    private transient AnchorPane anchorPane; // Transient field for UI, not serialized

    /**
     * Default constructor.
     * Initializes the board data structure.
     */
    public BoardHandler() {
        this.board = new ArrayList<>();
    }


    /**
     * Constructs a BoardHandler with specified dimensions and an associated AnchorPane for rendering.
     *
     * @param planeWidth  Width of the board.
     * @param planeHeight Height of the board.
     * @param gridSize    Number of rows and columns.
     * @param anchorPane  JavaFX AnchorPane for rendering.
     */
    public BoardHandler(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);
        this.anchorPane = anchorPane; // Set the AnchorPane for your board
    }

    /**
     * Updates the board grid's visual representation.
     *
     * @param isBoardHidden If true, ship positions are hidden.
     */
    public void updateGrid(boolean isBoardHidden) {
        clearBoard();
        drawGrid(isBoardHidden);
    }

    /**
     * Clears all elements from the AnchorPane.
     */
    private void clearBoard() {
        if (anchorPane != null) {
            anchorPane.getChildren().clear();
        }
    }

    /**
     * Draws the game board grid based on the logical state.
     *
     * @param isBoardHidden If true, ship positions are hidden.
     */
    private void drawGrid(boolean isBoardHidden) {
        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                Pane cell = createCell(row, col, isBoardHidden);
                if (anchorPane != null) {
                    anchorPane.getChildren().add(cell);
                }
            }
        }
    }

    /**
     * Creates a visual representation of a cell based on its state.
     *
     * @param row           Row of the cell.
     * @param col           Column of the cell.
     * @param isBoardHidden If true, ship positions are hidden.
     * @return Pane representing the cell.
     */
    private Pane createCell(int row, int col, boolean isBoardHidden) {
        Pane cell = new Pane();
        cell.setPrefSize(getTilesAcross(), getTilesDown());
        cell.setLayoutX(col * getTilesAcross());
        cell.setLayoutY(row * getTilesDown());

        int tileValue = getCell(row, col);
        if (tileValue == -1) {
            Pane xShape = createXShape(getTilesAcross(), getTilesDown());
            cell.getChildren().add(xShape);
        } else if (tileValue == 2) {
            Pane explosionShape = createExplosionShape(20); // Radius of explosion
            cell.getChildren().add(explosionShape);
        }
        else if (tileValue == 1 && !isBoardHidden) {
            cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(SHIP_COLOR) + ";");

        }
        else {
            cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(BACKGROUND_COLOR_1) + ";");
        }
        cell.setUserData(new int[]{row, col});
        return cell;
    }

    /**
     * Converts a JavaFX Color to an RGB string for CSS styling.
     *
     * @param color The color to convert.
     * @return The RGB string.
     */
    private String toRgbString(Color color) {
        return "rgb(" + (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) + ")";
    }


    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject(); // Serialize default fields
        out.writeObject(board);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject(); // Deserialize default fields
        // Ensure anchorPane is set
        if (this.anchorPane == null) {
            this.anchorPane = new AnchorPane();  // Or set it based on game context
        }

        updateGrid(false); // Rebuild the UI after deserialization
    }

    /**
     * Checks if a ship can be placed at a given position on the board.
     *
     * @param startX     Starting row.
     * @param startY     Starting column.
     * @param size       Length of the ship.
     * @param horizontal True if the ship is horizontal, false if vertical.
     * @return True if the ship can be placed, false otherwise.
     */
    public boolean canPlaceShip(int startX, int startY, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int row = horizontal ? startX : startX + i;
            int col = horizontal ? startY + i : startY;

            if (!isWithinBounds(row, col) || getCell(row, col) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places a ship on the board.
     *
     * @param startX     Starting row.
     * @param startY     Starting column.
     * @param size       Length of the ship.
     * @param horizontal True if the ship is horizontal, false if vertical.
     */
    public void placeShip(int startX, int startY, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int row = horizontal ? startX : startX + i;
            int col = horizontal ? startY + i : startY;
            setCell(row, col, 1); // 1 representa un barco
        }
    }

    /**
     * Marks a cell as hit.
     *
     * @param row Row of the cell.
     * @param col Column of the cell.
     */
    public void registerHit(int row, int col) {
        setCell(row, col, 2); // 2 representa un impacto
    }

    /**
     * Marks a cell as a missed shot.
     *
     * @param row Row of the cell.
     * @param col Column of the cell.
     */
    public void registerMiss(int row, int col) {
        setCell(row, col, -1); // -1 representa un fallo
    }

    /**
     * Checks if a cell has already been shot.
     *
     * @param row Row of the cell.
     * @param col Column of the cell.
     * @return True if the cell was already shot, false otherwise.
     */
    public boolean isCellAlreadyShot(int row, int col) {
        int cellValue = getCell(row, col);
        return cellValue == 2 || cellValue == -1;
    }

    public boolean isWithinBounds(int row, int col) {
        int gridSize = getGridSize();
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    public void setBoard(ArrayList<ArrayList<Integer>> newBoard, AnchorPane anchorPane) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                setCell(i, j, newBoard.get(i).get(j));
            }

        }
        this.anchorPane = anchorPane;
        // Ensure the grid is updated after setting the new board
        updateGrid(false);  // Pass true if you want to hide the board
    }

    /**
     * Creates a visual explosion shape for hit cells.
     *
     * @param radius Radius of the explosion.
     * @return Pane containing the explosion shape.
     */
    private Pane createExplosionShape(double radius) {
        Polygon explosion = new Polygon();

        // Generate points for the starburst
        int spikes = 8; // Number of spikes

        for (int i = 0; i < spikes * 2; i++) {
            double angle = Math.toRadians((360.0 / (spikes * 2)) * i);
            double r = (i % 2 == 0) ? radius : radius / 2; // Alternate between outer and inner radius
            double x = radius + r * Math.cos(angle);
            double y = radius + r * Math.sin(angle);
            explosion.getPoints().addAll(x, y);
        }

        // Customize appearance
        explosion.setFill(Color.ORANGE);
        explosion.setStroke(Color.RED);
        explosion.setStrokeWidth(3);

        // Add explosion to a Pane
        Pane pane = new Pane(explosion);
        pane.setPrefSize(radius * 2, radius * 2);
        pane.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(BACKGROUND_COLOR_1) + ";");// Ensure Pane size matches the explosion
        return pane;
    }

    /**
     * Creates a visual "X" shape for missed shots.
     *
     * @param width  Width of the cell.
     * @param height Height of the cell.
     * @return Pane containing the "X" shape.
     */
    private Pane createXShape(double width, double height) {
        double x1 = width * 0.2; // Start X for the first line
        double y1 = height * 0.2; // Start Y for the first line
        double x2 = width * 0.8; // End X for the first line
        double y2 = height * 0.8; // End Y for the first line
        double x3 = width * 0.2; // Start X for the second line
        double y3 = height * 0.8; // Start Y for the second line
        double x4 = width * 0.8; // End X for the second line
        double y4 = height * 0.2; // End Y for the second line

        Line line1 = new Line(x1, y1, x2, y2); // Diagonal from top-left to bottom-right
        Line line2 = new Line(x3, y3, x4, y4); // Diagonal from bottom-left to top-right

        // Customize the appearance of the lines
        line1.setStroke(Color.BLACK);
        line1.setStrokeWidth(5); // Thickness of the line
        line2.setStroke(Color.BLACK);
        line2.setStrokeWidth(5);

        // Add the lines to a Pane
        Pane pane = new Pane(line1, line2);
        pane.setPrefSize(width, height);
        pane.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(BACKGROUND_COLOR_1) + ";");// Ensure the Pane matches the size
        return pane;
    }



}
