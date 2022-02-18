package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.TetrisGame;
import at.htlhl.javafxtetris.grid.Cell;
import at.htlhl.javafxtetris.grid.Grid;
import at.htlhl.javafxtetris.grid.TetrisGrid;
import at.htlhl.javafxtetris.grid.block.BlockState;
import at.htlhl.javafxtetris.grid.block.FallingBlock;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.effect.Glow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import static java.lang.Thread.sleep;

public class TetrisController
{
    // Fields *****************************************************************
    @FXML private GridPane tetrisGridPane;
    @FXML private GridPane previewGridPane;
    @FXML private GridPane holdGridPane;
    private Pane[][] tetrisPaneMatrix;
    private Pane[][] previewPaneMatrix;
    private Pane[][] holdPaneMatrix;

    @FXML
    private Text scoreText;
    @FXML
    private Text linesText;
    @FXML
    private Text levelText;

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

    public void initHoldGrid(final Grid initialGrid){
        this.holdPaneMatrix = new Pane[4][4];
        initGridPane(holdGridPane, holdPaneMatrix, initialGrid);
    }


    /**
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
     * Displays the specified {@link BlockState} in the previewGrid
     *
     * @param blockState The {@link BlockState} that should be displayed
     */
    public void updatePreview(BlockState blockState)
    {
        updatePaneMatrix(previewPaneMatrix, blockState.getGrid());
    }

    public void updateHold(BlockState blockState){
        updatePaneMatrix(holdPaneMatrix, blockState.getGrid());
    }

    /**
     * Updates the {@link FallingBlock} displayed in the tetrisGrid
     * 
     * @param fallingBlock The {@link FallingBlock} that should be displayed
     */
    public void updateFallingBlock(FallingBlock fallingBlock)
    {
        Grid grid = fallingBlock.getBlockState().getGrid();
        final int startY = Math.max(0, fallingBlock.getY());
        final int endY = Math.min(tetrisPaneMatrix.length, fallingBlock.getY() + grid.getHeight());

        for(int y = 0; y < endY - startY; y++)
        {
            final int startX = Math.max(0, fallingBlock.getX());
            final int endX = Math.min(tetrisPaneMatrix[y].length, fallingBlock.getX() + grid.getWidth());
            for(int x = 0; x < endX - startX; x++)
            {
                Cell cell = grid.getCell(startX - fallingBlock.getX() + x, startY - fallingBlock.getY() + y);
                if(cell.isSolid())
                {
                    updatePane(tetrisPaneMatrix[startY + y][Math.max(0, startX) + x], cell);
                }
            }
        }
    }
    
    private void updatePaneMatrix(final Pane[][] paneMatrix, final Grid grid)
    {
        for(int y = 0; y < grid.getHeight(); y++)
        {
            for(int x = 0; x < grid.getLine(y).length; x++)
            {
                updatePane(paneMatrix[y][x], grid.getCell(x, y));
            }
        }
    }

    /*
     * Updates a Pane object using the data from the specified Cell object
     * MUST be called in JavaFX Thread
     */
    private void updatePane(final Pane pane, final Cell cell)
    {
        Platform.runLater(() ->
        {
            pane.setStyle("-fx-background-color:" + cell.getColor().toHex() + ";");
            pane.setVisible(cell.isSolid());
        });
    }


    public void flashGrid(){
        Glow glow = new Glow();
        glow.setLevel(10);

        previewGridPane.setEffect(glow);
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        previewGridPane.setEffect(null);
    }

    public Pane[][] getHoldPaneMatrix() {
        return holdPaneMatrix;
    }

    /**
     * Init bindings for all stats displayed
     * 
     * @param game The {@link TetrisGame} instance
     */
    public void initStats(final TetrisGame game)
    {
        // Looks weird but works
        scoreText.textProperty().bind(new SimpleStringProperty("Score: ").concat(game.scoreProperty()));
        levelText.textProperty().bind(new SimpleStringProperty("Level: ").concat(game.levelProperty()));
        linesText.textProperty().bind(new SimpleStringProperty("Lines cleared: ").concat(game.linesClearedProperty()));
    }

}
