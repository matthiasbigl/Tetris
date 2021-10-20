package at.htlhl;


public class TetrisGame
{
    // Constants **************************************************************
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    
    // Fields *****************************************************************
    private final TetrisController controller;
    private final Cell[][] grid;
    private Brick fallingBrick;
    
    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller)
    {
        this.controller = controller;
        this.grid = new Cell[GRID_HEIGHT][GRID_WIDTH];

        // Init the Grid Matrix
        initGridMatrix();
    }
    
    // Logic ******************************************************************
    private void tick()
    {
        processUserInput();
        // Don't call this every tick
        // if(...)
        {
            moveBrick();
            deleteFullLines();
        }
    }
    
    private void processUserInput()
    {
    
    }
    
    /**
     * Moves the falling {@link Brick} down one cell.<br>
     * If it collides with anything, it will be placed in the grid
     *
     * @return Whether the {@link Brick} was placed in the grid
     */
    private boolean moveBrick()
    {
        return false;
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

        updateGridMatrix();
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
