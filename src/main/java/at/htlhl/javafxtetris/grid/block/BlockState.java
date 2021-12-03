package at.htlhl.javafxtetris.grid.block;

import at.htlhl.javafxtetris.graphics.Color;
import at.htlhl.javafxtetris.grid.Cell;
import at.htlhl.javafxtetris.grid.Grid;

/**
 * Represents the state a Tetris {@link Block} is in (rotation, color)
 */
public class BlockState
{
	// Fields *****************************************************************
	private final Block block;
	
	private Color color;
	
	// Constructors ***********************************************************
	public BlockState(final Block block)
	{
		this.block = block;
	}
	
	// Logic ******************************************************************
	/**
	 * Converts this Block to a Cell matrix
	 *
	 * @return A 2 dimensional {@link Cell} array
	 */
	public Cell[][] toCellMatrix()
	{
		final boolean[][] blockMatrix = block.getBlockMatrix();
		final Cell[][] cellMatrix = new Cell[blockMatrix.length][];
		
		for(int y = 0; y < blockMatrix.length; y++)
		{
			cellMatrix[y] = new Cell[blockMatrix[y].length];
			for(int x = 0; x < blockMatrix[y].length; x++)
			{
				cellMatrix[y][x] = new Cell(block.getDefaultColor(), blockMatrix[y][x]);
			}
		}
		
		return cellMatrix;
	}
	
	/**
	 * Creates a new Grid using the {@link Cell} matrix of this {@link Block}
	 *
	 * @return A {@link Grid} object
	 */
	public Grid toGrid()
	{
		return new Grid(toCellMatrix());
	}
	
	/**
	 * Constructs a falling block at the specified location
	 *
	 * @param x The x position of the falling Block
	 * @param y The y position of the falling Block
	 * @return A {@link FallingBlock}
	 */
	public FallingBlock falling(int x, int y)
	{
		return new FallingBlock(this, x, y);
	}
	
	// Accessors **************************************************************
	public Block getBlock()
	{
		return block;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
