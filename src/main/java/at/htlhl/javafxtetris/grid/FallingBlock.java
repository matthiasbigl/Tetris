package at.htlhl.javafxtetris.grid;

public class FallingBlock
{
    private final Block block;
    private int x, y;

    // Constructors ***********************************************************
    public FallingBlock(Block block, int x, int y)
    {
        this.block = block;
        this.x = x;
        this.y = y;
    }

    public FallingBlock(Block block)
    {
        this(block, 0, 0);
    }

    // Accessors **************************************************************
    public Block getBlock()
    {
        return block;
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
        final Cell[][] cellMatrix = getBlock().toCellMatrix();

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
     * Returns the relative x value ({@code absoluteX -} {@link FallingBlock#getX()})
     *
     * @param absoluteX The absolute x value
     * @return The relative x value
     */
    public int getRelativeX(int absoluteX)
    {
        return absoluteX - getX();
    }

    /**
     * Returns the relative y value ({@code absoluteY -} {@link FallingBlock#getY()})
     *
     * @param absoluteY The absolute y value
     * @return The relative y value
     */
    public int getRelativeY(int absoluteY)
    {
        return absoluteY - getY();
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
    public void placeBlock(Grid grid)
    {
        grid.placeCellsInGrid(this.getBlock().toCellMatrix(), getX(), getY());
    }
}
