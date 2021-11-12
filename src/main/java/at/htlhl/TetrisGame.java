package at.htlhl;


import java.util.Timer;
import java.util.TimerTask;

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
	
	private Timer tickTimer;
	private boolean isRunning = false;
	private boolean isPaused = true;
	
	// Constructors ***********************************************************
	public TetrisGame(TetrisController controller)
	{
		this.controller = controller;
		this.grid = new Cell[GRID_HEIGHT][GRID_WIDTH];
		
		// Init the Game
		this.fallingBlock = Block.randomBlock();
		this.nextBlock = Block.randomBlock();
		controller.initNextGridMatrix();
		controller.updateNextBlock(nextBlock);
		initGridMatrix();
	}
	
	// Logic ******************************************************************
	public void start()
	{
		if(isRunning())
			return;
		
		this.isRunning = true;
		unpause();
		
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
		}, 0, 100);
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
	 * Is called 10 times per second
	 */
	private void tick()
	{
		System.out.println("Tick");
		processUserInput();
		
		if (moveBlock())
		{
			this.fallingBlock = nextBlock;
			this.nextBlock = generateNewBlock(nextBlock);
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

	private void deleteLine(int index){
		//Line delete
		Cell[] line = grid[index];
		for (Cell cell : line){
			cell.setVisible(false);
		}

		//Line above comes down
		for (int i = index; i > 0; i--) {
			Cell[] lineabove = grid[index-1];
			grid[i] = lineabove;
		}
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
