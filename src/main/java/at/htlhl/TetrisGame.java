package at.htlhl;

import javafx.scene.paint.Color;

public class TetrisGame
{
    // Constants **************************************************************
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    
    // Fields *****************************************************************
    private final Cell[][] grid;
    
    // Constructors ***********************************************************
    public TetrisGame()
    {
        this.grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
        initGridMatrix();
    }
    
    // Logic ******************************************************************
    /**
     * Fill the Grid Matrix with invisible Cells
     */
    private void initGridMatrix()
    {
        for (int y = 0; y < GRID_HEIGHT; y++)
        {
            for (int x = 0; x < GRID_WIDTH; x++)
            {
                grid[y][x] = new Cell();
            }
        }
    }
}
