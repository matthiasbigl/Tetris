package at.htlhl;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TetrisController
{
    // Fields *****************************************************************
    @FXML
    private GridPane gridPane;
    
    // Constructors ***********************************************************
    public TetrisController()
    {
    }

    /**
     * Creates a new Pane object from the given {@link Cell} object
     * @param cell The {@link Cell} object representing a tetris tile
     * @return The created pane
     */
    private Pane createPane(final Cell cell)
    {
        Pane pane = new Pane();
    
        pane.setStyle("-fx-background-color:" + cell.getColor().toHex() + ";");
        return pane;
    }

    /**
     * Updates the GridPane with the given grid data
     * @param newGrid The grid that should be displayed
     */
    public void updateGridMatrix(final Cell[][] newGrid)
    {
        for (int y = 0; y < newGrid.length; y++)
        {
            for (int x = 0; x < newGrid[y].length; x++)
            {
                // Create a new Pane object, since there is no proper way of retrieving the existing ones from the GridPane
                // TODO: Maybe save all Pane objects in an array instead
                gridPane.add(createPane(newGrid[y][x]), x, y);
            }
        }
    }
}
