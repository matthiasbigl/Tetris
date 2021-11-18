package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.grid.Block;
import at.htlhl.javafxtetris.grid.Cell;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisController
{
    // Fields *****************************************************************
    @FXML
    private GridPane tetrisGridPane;
    @FXML
    private GridPane previewGridPane;

    private Pane[][] tetrisGrid;
    private Pane[][] previewGrid;

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
        Platform.runLater(() ->
        {
            pane.setStyle("-fx-background-color:" + cell.getColor().toHex() + ";");
            pane.setVisible(cell.isVisible());
        });
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
                this.tetrisGrid[y][x] = new Pane();
                tetrisGridPane.add(tetrisGrid[y][x], x, y);
            }
        }
    }
    
    /**
     * Updates the GridPane using the given grid data
     *
     * @param newGrid The grid that should be displayed. Must be the same size as the GridPane
     */
    public void updateTetrisGrid(final Cell[][] newGrid)
    {
        for (int y = 0; y < tetrisGrid.length; y++)
        {
            for (int x = 0; x < tetrisGrid[y].length; x++)
            {
                updatePane(tetrisGrid[y][x], newGrid[y][x]);
            }
        }
    }
    
    public void addCellsToTetrisGrid(final int startX, final int startY, final Cell[][] newCells)
    {
        for (int y = 0; y < newCells.length && (startY + y) < tetrisGrid.length; y++)
        {
            for (int x = 0; x < newCells[y].length && (startX + x) < tetrisGrid[y].length; x++)
            {
                final int actualY = startY + y;
                final int actualX = startX + x;
                if(newCells[y][x].isVisible())
                {
                    updatePane(tetrisGrid[actualY][actualX], newCells[y][x]);
                }
            }
        }
    }

    public void initPreviewGrid()
    {
        this.previewGrid = new Pane[4][4];

        for (int y = 0; y < previewGrid.length; y++)
        {
            for (int x = 0; x < previewGrid[y].length; x++)
            {
                this.previewGrid[y][x] = new Pane();
                previewGridPane.add(previewGrid[y][x], x, y);
            }
        }
    }

    public void updatePreviewGrid(Block newBlock)
    {
        Cell[][] newGrid = newBlock.toCellMatrix();
        for (int y = 0; y < newGrid.length; y++)
        {
            for (int x = 0; x < newGrid[y].length; x++)
            {
                updatePane(previewGrid[y][x], newGrid[y][x]);
            }
        }
    }
}
