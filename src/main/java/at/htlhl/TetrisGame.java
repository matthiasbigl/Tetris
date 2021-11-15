package at.htlhl;


import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame
{
	// Constants **************************************************************
	public static final int GRID_WIDTH = 10;
	public static final int GRID_HEIGHT = 20;
	
	// Fields *****************************************************************
	// Grid and Blocks
	private final TetrisController controller;
	private final Cell[][] grid;
	private FallingBlock fallingBlock;
	private Block nextBlock;
	
	// Timer & Tick loop
	private Timer tickTimer;
	private boolean isRunning;
	private boolean isPaused;
	
	// Movement
	private long totalTickCount; // The total number of ticks that have happened
	private long lastBlockFall; // The last tick the FallingBlock was moved
	private boolean didBlockFall;
	
	// Constructors ***********************************************************
	public TetrisGame(TetrisController controller)
	{
		this.controller = controller;
		this.grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
		
		// Init the Game
		this.isRunning = false;
		this.isPaused = true;
		this.nextBlock = Block.randomBlock();
		generateNewBlock();

		// Init the controller
		controller.initPreviewGrid();
		controller.updatePreviewGrid(nextBlock);

		initGridMatrix();
		updateGridMatrix();
	}
	
	// Logic ******************************************************************
	public void start()
	{
		if(isRunning())
			return;
		
		this.isRunning = true;
		unpause();
		
		this.totalTickCount = 1;
		this.tickTimer = new Timer();
		tickTimer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
			if(!isPaused())
			{
				tick();
			}
			}
		}, 0, 20);
	}
	
	public void stop()
	{
		if(!isRunning())
			return;
		
		this.isRunning = false;
		tickTimer.cancel();
	}
	
	public void pause()
	{
		this.isPaused = true;
	}
	
	public void unpause()
	{
		this.isPaused = false;
	}
	
	/**
	 * @return Whether the game is running
	 */
	public boolean isRunning()
	{
		return isRunning;
	}
	
	/**
	 * @return Whether the game is paused
	 */
	public boolean isPaused()
	{
		return isPaused;
	}
	
	/**
	 * Is called x times per second
	 */
	private void tick()
	{
		// This should happen each tick:
		// * process user input
		// * move the block down one block (only do this every x ticks)
		// * if the block didn't move, place it in the grid
		// * increment the total tick count
		
		processUserInput();
		System.out.print("Tick " + totalTickCount);
		
		// TODO: Don't call this every tick
		if(lastBlockFall + 10 <= totalTickCount)
		{
			System.out.print(": Block fall");
			this.lastBlockFall = totalTickCount;
			this.didBlockFall = false;
			tryMoveBlock(0, 1);

            // If the block couldn't fall, place it in the grid
            if (!didBlockFall)
            {
                System.out.print(", Block placed");
                fallingBlock.placeBlock(this);
                generateNewBlock();
                controller.updatePreviewGrid(nextBlock);
                deleteFullLines();
            }
		}
		System.out.println();
		
		totalTickCount++;
		updateGridMatrix();
	}
	
	private void generateNewBlock()
	{
		int centeredX = (GRID_WIDTH / 2) - (nextBlock.getWidth() / 2);
		this.fallingBlock = nextBlock.falling(centeredX, 0);
		this.nextBlock = generateNewBlock(nextBlock);
	}
	
	private void processUserInput()
	{
		// * move the block
		// * rotate the block TODO
		// according to the user input
	}
	
	/**
	 * Generates a random Block that does not match the given one
	 */
	private Block generateNewBlock(Block excludedBlock)
	{
		return Block.values()[(excludedBlock.ordinal() +
				(int) (Math.random() * (Block.values().length - 1)) + 1)
				% Block.values().length];
	}
	
	/**
	 * Places the given {@link Cell} matrix in the grid.<br>
	 * DOES NOT update the {@link TetrisController}
	 *
	 * @param cellMatrix The {@link Cell}s to place in the grid
	 * @param posX       The x position
	 * @param posY       The y position
	 */
	public void placeInGrid(Cell[][] cellMatrix, int posX, int posY)
	{
		for (int currY = 0; currY < cellMatrix.length; currY++)
		{
			for (int x = 0; x < cellMatrix[currY].length; x++)
			{
				final Cell cell = cellMatrix[currY][x];
				if(cell.isVisible())
				{
					grid[posY + currY][posX + x] = cellMatrix[currY][x];
				}
			}
		}
	}
	
	/**
	 * Moves the {@link FallingBlock} according to the x and y parameters if it can and sets the didBlockFall variable
	 */
	private void tryMoveBlock(final int x, final int y)
	{
		// Move the block if it can be moved
		if(fallingBlock.canMove(this, x, y))
		{
			fallingBlock.move(x, y);
			didBlockFall = true;
		}
	}
	
	private void deleteFullLines()
	{
	
	}

	private void deleteLine(int index){
		//Line above comes down
		for (int i = index; i > 0; i--) {
			Cell[] lineabove = grid[i-1];
			for(int j = 0; j < lineabove.length; j++)
			{
				grid[i][j] = lineabove[j].clone();
			}
		}

		for (Cell cell : grid[0]){
			cell.setVisible(false);
		}
	}
	
	public Cell getCell(int x, int y)
	{
		if(isInBounds(x, y))
		{
			return grid[y][x];
		}
		
		return new Cell(Color.TRANSPARENT, true);
	}
	
	public boolean isInBounds(int x, int y)
	{
		if(y >= 0 && y < grid.length)
		{
			if(x >= 0 && x < grid[y].length)
			{
				return true;
			}
		}
		
		return false;
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
		updateGridMatrix();
	}
	
	/**
	 * Updates the Controller grid
	 */
	private void updateGridMatrix()
	{
		controller.updateTetrisGrid(grid);
		// Add the FallingBlock to the controller grid
		controller.addCellsToTetrisGrid(fallingBlock.getX(), fallingBlock.getY(), fallingBlock.getBlock().toCellMatrix());
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
