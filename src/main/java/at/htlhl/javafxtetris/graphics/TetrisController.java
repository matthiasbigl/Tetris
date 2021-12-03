package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.grid.Cell;
import at.htlhl.javafxtetris.grid.Grid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisController
{
    // Fields *****************************************************************
    @FXML private GridPane tetrisGridPane;
    @FXML private GridPane previewGridPane;
    private Pane[][] tetrisPaneMatrix;
    private Pane[][] previewPaneMatrix;

    // Constructors ***********************************************************
    public TetrisController()
    {
    }

    // Logic ******************************************************************
    // Init logic
    /**
     * Initialises the tetrisGrid
     *
     * @param grid The data to fill the tetrisGrid with
     */
    public void initTetrisGrid(final Grid grid)
    {
        this.tetrisPaneMatrix = new Pane[grid.getHeight()][];
        initGridPane(tetrisGridPane, tetrisPaneMatrix, grid);
    }

    /**
     * Initialises the previewGrid
     *
     * @param initialGrid The data to fill the tetrisGrid with
     */
    public void initPreviewGrid(final Grid initialGrid)
    {
        this.previewPaneMatrix = new Pane[4][4];
        initGridPane(previewGridPane, previewPaneMatrix, initialGrid);
    }

    /*
     * Initialises the specified GridPane and paneMatrix using data from the cellMatrix
     * The length of paneMatrix must be the same as cellMatrix
     */
    private void initGridPane(final GridPane gridPane, final Pane[][] paneMatrix, final Grid grid)
    {
        for(int y = 0; y < grid.getHeight(); y++)
        {
            paneMatrix[y] = new Pane[grid.getLine(y).length];
            for(int x = 0; x < grid.getLine(y).length; x++)
            {
                paneMatrix[y][x] = new Pane();
                gridPane.add(paneMatrix[y][x], x, y);
            }
        }
        
        updatePaneMatrix(paneMatrix, grid);
    }

    // Update logic
    /**
     * Updates the tetrisGrid using data from the specified {@link Grid}
     *
     * @param tetrisGrid The {@link Grid} that should be displayed
     */
    public void updateTetrisGrid(final Grid tetrisGrid)
    {
        updatePaneMatrix(tetrisPaneMatrix, tetrisGrid);
    }

    /**
     * Updates the previewGrid using the specified {@link Grid}
     *
     * @param newGrid The {@link Grid} that should be displayed
     */
    public void updatePreviewGrid(Grid newGrid)
    {
        updatePaneMatrix(previewPaneMatrix, newGrid);
    }
    
    private void updatePaneMatrix(final Pane[][] paneMatrix, final Grid grid)
    {
        for(int y = 0; y < grid.getHeight(); y++)
        {
            for(int x = 0; x < grid.getLine(y).length; x++)
            {
                updatePane(paneMatrix[y][x], grid.getVisibleCell(x, y));
            }
        }
    }

    /**
     * Updates a {@link Pane} object using the data from the specified {@link Cell} object
     *
     * @param cell The {@link Cell} object representing a tetris tile
     */
    private void updatePane(final Pane pane, final Cell cell)
    {
        Platform.runLater(() ->
        {
            pane.setStyle("-fx-background-color:" + cell.getColor().toHex() + ";");
            pane.setVisible(cell.isSolid());
        });
    }
}
