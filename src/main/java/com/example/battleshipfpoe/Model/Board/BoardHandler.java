package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class BoardHandler extends BoardBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Color BACKGROUND_COLOR_1 = Color.rgb(127, 205, 255);
    private static final Color SHIP_COLOR = Color.GRAY;
    private static final Color HIT_COLOR = Color.RED;
    private static final Color MISS_COLOR = Color.BLUE;

    private ArrayList<ArrayList<Integer>> board; // Logical state of the board
    private transient AnchorPane anchorPane; // Transient field for UI, not serialized

    public BoardHandler() {
        this.board = new ArrayList<>();
    }

    // Constructor
    public BoardHandler(double planeWidth, double planeHeight, int gridSize, AnchorPane anchorPane) {
        super(planeWidth, planeHeight, gridSize, anchorPane);
        this.anchorPane = anchorPane; // Set the AnchorPane for your board
    }

    // Custom serialization logic
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

    // Set the AnchorPane dynamically
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        // Call updateGrid to ensure the UI reflects the current board state
        updateGrid(false);  // Pass `true` if you want to hide the board
    }

    // Update the visual representation of the board
    public void updateGrid(boolean isBoardHidden) {
        clearBoard(); // Clear existing grid
        drawGrid(isBoardHidden); // Redraw based on the board's state
    }

    private void clearBoard() {
        if (anchorPane != null) {
            anchorPane.getChildren().clear();
        }
    }

    private void drawGrid(boolean isBoardHidden) {
        // Ensure that we correctly draw the grid and apply the colors
        for (int row = 0; row < getGridSize(); row++) {
            for (int col = 0; col < getGridSize(); col++) {
                Pane cell = createCell(row, col, isBoardHidden);
                if (anchorPane != null) {
                    anchorPane.getChildren().add(cell);
                }
            }
        }
    }

    private Pane createCell(int row, int col, boolean isBoardHidden) {
        Pane cell = new Pane();
        cell.setPrefSize(getTilesAcross(), getTilesDown());
        cell.setLayoutX(col * getTilesAcross());
        cell.setLayoutY(row * getTilesDown());

        // Use the actual value from the board to determine the color
        int tileValue = getCell(row, col);
        Color tileColor = determineTileColor(tileValue, isBoardHidden);

        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
        cell.setUserData(new int[]{row, col});

        return cell;
    }

    private Color determineTileColor(int tileValue, boolean isBoardHidden) {
        // If the board is hidden, only show the ships as background color
        if (isBoardHidden && tileValue == 1) {
            return BACKGROUND_COLOR_1; // Ship is hidden, use background color
        }

        // Return the appropriate color based on the tile value
        switch (tileValue) {
            case 1: return SHIP_COLOR;   // Ship
            case 2: return HIT_COLOR;    // Hit
            case -1: return MISS_COLOR;  // Miss
            default: return BACKGROUND_COLOR_1; // Empty
        }
    }

    private String toRgbString(Color color) {
        return "rgb(" + (int) (color.getRed() * 255) + "," +
                (int) (color.getGreen() * 255) + "," +
                (int) (color.getBlue() * 255) + ")";
    }

    // Game logic
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

    public void placeShip(int startX, int startY, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int row = horizontal ? startX : startX + i;
            int col = horizontal ? startY + i : startY;
            setCell(row, col, 1); // 1 represents a ship
        }
    }

    public void registerHit(int row, int col) {
        setCell(row, col, 2); // 2 represents a hit
    }

    public void registerMiss(int row, int col) {
        setCell(row, col, -1); // -1 represents a miss
    }

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
        printBoard();
        this.anchorPane = anchorPane;
        // Ensure the grid is updated after setting the new board
        updateGrid(false);  // Pass `true` if you want to hide the board
    }

}

