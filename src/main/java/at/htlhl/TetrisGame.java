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
		
		// Init the Game
		this.fallingBlock = Block.randomBlock();
		this.nextBlock = Block.randomBlock();

		controller.initPreviewGrid();
		controller.updatePreviewGrid(nextBlock);

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
				this.fallingBlock = nextBlock;
				this.nextBlock = generateNewBlock(nextBlock);
			}
			deleteFullLines();
		}
	}
	
	private void processUserInput()
	{
	
	}
	
	/**
	 * Generates a random Block that does not match the given one
	 */
	private Block generateNewBlock(Block excludedBlock)
	{
		return Block.values()[(excludedBlock.ordinal() +
				(int) (Math.random() * (Block.values().length - 1)))
				% Block.values().length];
	}
	
	/**
	 * Places the falling Block in the grid
	 */
	private void placeFallingBlock()
	{
	
	}

	/**
	 * method to check if block should be placed
	 * @param block
	 * @param y
	 * @param x
	 */
	private void placeBlockInGrid(Block block, int y, int x) {
		boolean[][] blockMatrix = block.getBlockMatrix();

		boolean place = false;

		if (x+blockMatrix.length-1 == 19) {
			place = true;
		} else {
			boolean[] lowestBlockLine = blockMatrix[blockMatrix.length - 1];
			int bottomLineIndex = y + blockMatrix.length - 1;

			Cell[] lineUnder = grid[bottomLineIndex + 1];

			for (int i = 0; i < lowestBlockLine.length; i++)
			{
				if (lowestBlockLine[i])
				{
					if (lineUnder[x + i].isVisible())
					{
						place = true;
					}
				}
			}
		}

		/**
		 * TODO: place block in grid
		 */
		if (place) {
			// place in grid
			placeInGrid(block.toCellMatrix(), x,y);
		}
	}
	
	/**
	 * Places the given {@link Cell} matrix in the grid.<br>
	 * DOES NOT update the {@link TetrisController}
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
				grid[posY + currY][posX + x] = cellMatrix[currY][x];
			}
		}
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
		controller.updateTetrisGrid(grid);
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


	/**
	 * Simple method to check if a line in the grid is full
	 *
	 * @param index Index of the line to check
	 * @return boolean if line is full
	 */
	private boolean lineFull(int index) {
		Cell[] line = grid[index];
		boolean isFull = true;
		for (Cell cell : line) {
			if (!cell.isVisible()) {
				isFull = false;
			}
		}
		return isFull;
	}
}
