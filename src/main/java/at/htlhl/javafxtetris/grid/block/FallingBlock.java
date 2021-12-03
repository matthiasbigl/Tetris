package at.htlhl.javafxtetris.grid.block;

import at.htlhl.javafxtetris.grid.Cell;
import at.htlhl.javafxtetris.grid.Grid;

public class FallingBlock
{
    private final BlockState blockState;
    private int x, y;

    // Constructors ***********************************************************
    public FallingBlock(BlockState state, int x, int y)
    {
        this.blockState = state;
        this.x = x;
        this.y = y;
    }

    // Accessors **************************************************************
    public BlockState getBlockState()
    {
        return blockState;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    // Logic ******************************************************************

    /**
     * Checks if the block can move in the given directions
     *
     * @param gridIn The gridIn object
     * @param moveX  x movement
     * @param moveY  y movement
     * @return Whether the Block can move in the given directions
     */
    public boolean canMove(Grid gridIn, int moveX, int moveY)
    {
        final Cell[][] cellMatrix = getBlockState().toCellMatrix();

        // Iterate over every Cell in the matrix
        for(int y = 0; y < cellMatrix.length; y++)
        {
            for(int x = 0; x < cellMatrix[y].length; x++)
            {
                // If there is a Cell at the current position, check if it can move in the given direction
                if(cellMatrix[y][x].isSolid())
                {
                    // If there is a Cell at the new position, the current Cell can't be moved
                    if(gridIn.getCell(getX() + x + moveX, getY() + y + moveY).isSolid())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * moves the block in the specified directions
     *
     * @param moveX X movement
     * @param moveY Y movement
     */
    public void move(int moveX, int moveY)
    {
        setX(getX() + moveX);
        setY(getY() + moveY);
    }

    /**
     * Places the Block in the specified Grid
     *
     * @param grid The {@link Grid} to place the {@link Block} in
     */
    public void placeBlockIn(Grid grid)
    {
        grid.placeCellsInGrid(this.getBlockState().toGrid(), getX(), getY());
    }
    
    public boolean canPlaceBlockIn(Grid grid)
    {
        return grid.canPlace(getBlockState().toGrid(), getX(), getY());
    }
}
