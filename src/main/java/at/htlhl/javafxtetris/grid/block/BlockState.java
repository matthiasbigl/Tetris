package at.htlhl.javafxtetris.grid.block;

import at.htlhl.javafxtetris.grid.Cell;
import at.htlhl.javafxtetris.grid.Grid;

/**
 * Represents the state a Tetris {@link Block} is in (rotation)
 */
public class BlockState
{
	// Fields *****************************************************************
	private final Block block;
	private final Grid blockGrid;
	
	// Constructors ***********************************************************
	public BlockState(final Block block)
	{
		this.block = block;
		this.blockGrid = new Grid(toCellMatrix());
	}
	
	protected BlockState(final Block block, final Grid grid)
	{
		this.block = block;
		this.blockGrid = grid;
	}
	
	// Logic ******************************************************************
	/**
	 * Converts this {@link BlockState} to a Cell matrix
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
	 * Creates a new Grid using the {@link Cell} matrix of this {@link BlockState}
	 *
	 * @return A {@link Grid} object
	 */
	public Grid getGrid()
	{
		return blockGrid.clone();
	}
	
	/**
	 * Rotates this {@link BlockState}
	 * 
	 * @param rotation The rotation
	 * @return A new {@link BlockState}   
	 */
	public BlockState rotate(final Rotation rotation)
	{
		return new BlockState(this.block, rotateGrid(rotation));
	}
	
	/*
	 * Rotates the current Grid and returns a new one
	 */
	private Grid rotateGrid(Rotation rotation)
	{
		final double originX = block.getOriginX();
		final double originY = block.getOriginY();
		
		final int[][] rotMatrix = rotation.getRotationMatrix();
		final Cell[][] newMatrix = new Cell[blockGrid.getHeight()][];
		
		for(int y = 0; y < blockGrid.getHeight(); y++)
		{
			final int width = blockGrid.getWidth(y);
			newMatrix[y] = new Cell[width];
			for(int x = 0; x < width; x++)
			{
				final double relativeX = ((double) x) - originX;
				final double relativeY = ((double) y) - originY;
				
				// Not sure if all casts are necessary
				final int rotatedX = (int) (originX + (double) (rotMatrix[0][0]) * relativeX + (double) (rotMatrix[0][1]) * relativeY);
				final int rotatedY = (int) (originY + (double) (rotMatrix[1][0]) * relativeX + (double) (rotMatrix[1][1]) * relativeY);
				
				final Cell cell;
				if(blockGrid.isCellInBounds(rotatedX, rotatedY))
					cell = blockGrid.getCell(rotatedX, rotatedY);
				else
					cell = new Cell(false);
				
				newMatrix[y][x] = cell;
			}
		}
		
		return new Grid(newMatrix);
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
}
