package at.htlhl;


public class TetrisGame
{
    // Constants **************************************************************
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    
    // Fields *****************************************************************
    private final TetrisController controller;
    private final Cell[][] grid;
    private Block fallingBlock;
    
    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller)
    {
        this.controller = controller;
        this.grid = new Cell[GRID_HEIGHT][GRID_WIDTH];

        // Init the Grid Matrix
        initGridMatrix();
        tick();
    }
    
    // Logic ******************************************************************
    private void tick()
    {
        processUserInput();
        // Don't call this every tick
        // if(...)
        {
            if(moveBlock())
            {
                placeNewBlock();
            }
            deleteFullLines();
        }
    }
    
    private void processUserInput()
    {
    
    }
    
    /**
     * Generates a new random Block
     */
    private void placeNewBlock()
    {
        // TODO: use fallingBlock variable, don't set cells in grid
        // TODO: Don't generate the same block twice
        // TODO: Add nextBlock variable & logic
        Block newBlock = Block.randomBlock();
        final Cell[][] cellMatrix = newBlock.toCellMatrix();
    
        final int startY = 4 - cellMatrix.length;
        final int startX = GRID_WIDTH / 2 - 2;
        for (int y = 0; y < cellMatrix.length; y++)
        {
            for (int x = 0; x < cellMatrix[y].length; x++)
            {
                grid[startY + y][startX + x] = cellMatrix[y][x];
            }
        }
        
        updateGridMatrix();
    }
    
    /**
     * Moves the falling {@link Brick} down one cell.<br>
     * If it collides with anything, it will be placed in the grid
     *
     * @return Whether the {@link Brick} was placed in the grid
     */
    private boolean moveBlock()
    {
        return true;
    }
    
    private void deleteFullLines()
    {
    
    }
    
    /**
     * Fill the Grid Matrix with Cell objects
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

        controller.initGridMatrix(grid);
    }

    /**
     * Updates the Controller grid
     */
    private void updateGridMatrix()
    {
        controller.updateGridMatrix(grid);
    }

    /**
     * Randomizes the grid matrix
     * For testing purposes
     */
    void randomizeMatrix()
    {
        for (int y = 0; y < GRID_HEIGHT; y++)
        {
            for (int x = 0; x < GRID_WIDTH; x++)
            {
                grid[y][x].setColor(Color.randomColor(false));
            }
        }

        updateGridMatrix();
    }
}
