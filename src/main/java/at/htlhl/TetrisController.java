package at.htlhl;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisController
{
    // Fields *****************************************************************
    @FXML
    private GridPane gridPane;

    private Pane[][] tetrisGrid;

    // Constructors ***********************************************************
    public TetrisController()
    {
    }

    /**
     * Updates a {@link Pane} object with the color from the given {@link Cell} object
     *
     * @param cell The {@link Cell} object representing a tetris tile
     */
    private void updatePane(final Pane pane, final Cell cell)
    {
        pane.setStyle("-fx-background-color:" + cell.getColor().toHex() + ";");
        pane.setVisible(cell.isVisible());
    }

    /**
     * Initialises the objects in the GridPane
     *
     * @param initialGrid
     */
    public void initGridMatrix(final Cell[][] initialGrid)
    {
        this.tetrisGrid = new Pane[initialGrid.length][];

        for (int y = 0; y < initialGrid.length; y++)
        {
            this.tetrisGrid[y] = new Pane[initialGrid[y].length];
            for (int x = 0; x < initialGrid[y].length; x++)
            {
                final Pane newPane = new Pane();
                updatePane(newPane, initialGrid[y][x]);
                this.tetrisGrid[y][x] = newPane;
                gridPane.add(tetrisGrid[y][x], x, y);
            }
        }
    }

    /**
     * Updates the GridPane with the given grid data
     *
     * @param newGrid The grid that should be displayed
     */
    public void updateGridMatrix(final Cell[][] newGrid)
    {
        for (int y = 0; y < newGrid.length; y++)
        {
            for (int x = 0; x < newGrid[y].length; x++)
            {
                updatePane(tetrisGrid[y][x], newGrid[y][x]);
            }
        }
    }
}
