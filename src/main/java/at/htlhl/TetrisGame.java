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
	private Block nextBlock;
	
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
			if (moveBlock())
			{
				generateNewBlock();
			}
			deleteFullLines();
		}
	}
	
	private void processUserInput()
	{
	
	}
	
	/**
	 * Generates a new Block that is different from the current Block
	 */
	private void generateNewBlock()
	{
		this.fallingBlock = nextBlock;
		this.nextBlock = Block.values()[fallingBlock.ordinal() + (int) (Math.random() * (Block.values().length - 1))];
	}
	
	/**
	 * Places the falling Block in the grid
	 */
	private void placeFallingBlock()
	{
	
	}
	
	/**
	 * Places the given {@link Cell} matrix in the grid and updates it
	 *
	 * @param cellMatrix The {@link Cell}s to place in the grid
	 * @param posX       The x position
	 * @param posY       The y position
	 */
	private void placeInGrid(Cell[][] cellMatrix, int posX, int posY)
	{
		for (int currY = 0; currY < cellMatrix.length; currY++)
		{
			for (int x = 0; x < cellMatrix[currY].length; x++)
			{
				grid[posX + currY][posX + x] = cellMatrix[currY][x];
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
