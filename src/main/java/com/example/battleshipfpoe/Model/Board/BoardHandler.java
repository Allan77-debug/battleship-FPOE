package com.example.battleshipfpoe.Model.Board;

import com.example.battleshipfpoe.Model.List.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serial;
import java.io.Serializable;

public class BoardHandler extends BoardBase implements Serializable {

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

    public void updateGrid(boolean isBoardHidden) {
        clearBoard();
        drawGrid(isBoardHidden);
    }

    private void clearBoard() {
        if (anchorPane != null) {
            anchorPane.getChildren().clear();
        }
    }

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

    private Pane createCell(int row, int col, boolean isBoardHidden) {
        Pane cell = new Pane();
        cell.setPrefSize(getTilesAcross(), getTilesDown());
        cell.setLayoutX(col * getTilesAcross());
        cell.setLayoutY(row * getTilesDown());

        int tileValue = getCell(row, col);
        Color tileColor = (isBoardHidden && tileValue == 1) ? BACKGROUND_COLOR_1 : determineTileColor(tileValue);

        cell.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(tileColor) + ";");
        cell.setUserData(new int[]{row, col});

        return cell;
    }

    private Color determineTileColor(int tileValue) {
        return switch (tileValue) {
            case 1 -> SHIP_COLOR;
            case 2 -> HIT_COLOR;
            case -1 -> MISS_COLOR;
            default -> BACKGROUND_COLOR_1;
        };
    }

    /**
     * Convierte un color de JavaFX a formato RGB para poder usarlo en el estilo CSS.
     *
     * @param color el color a convertir
     * @return el color en formato RGB como cadena
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
     * Verifica si un barco puede colocarse en la posición especificada.
     *
     * @param startX     Coordenada inicial en X.
     * @param startY     Coordenada inicial en Y.
     * @param size       Tamaño del barco.
     * @param horizontal Indica si el barco es horizontal.
     * @return true si se puede colocar, false en caso contrario.
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
     * Coloca un barco en el tablero.
     *
     * @param startX     Coordenada inicial en X.
     * @param startY     Coordenada inicial en Y.
     * @param size       Tamaño del barco.
     * @param horizontal Indica si el barco es horizontal.
     */
    public void placeShip(int startX, int startY, int size, boolean horizontal) {
        for (int i = 0; i < size; i++) {
            int row = horizontal ? startX : startX + i;
            int col = horizontal ? startY + i : startY;
            setCell(row, col, 1); // 1 representa un barco
        }
    }

    /**
     * Marca una celda como impactada.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     */
    public void registerHit(int row, int col) {
        setCell(row, col, 2); // 2 representa un impacto
    }

    /**
     * Marca una celda como un disparo fallido.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     */
    public void registerMiss(int row, int col) {
        setCell(row, col, -1); // -1 representa un fallo
    }

    /**
     * Verifica si una celda ya fue disparada.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @return true si ya fue disparada, false en caso contrario.
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
        printBoard();
        this.anchorPane = anchorPane;
        // Ensure the grid is updated after setting the new board
        updateGrid(false);  // Pass `true` if you want to hide the board
    }
}
