package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.TetrisGame;

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
     * @param game  The game object
     * @param moveX x movement
     * @param moveY y movement
     * @return Whether the Block can move in the given directions
     */
    public boolean canMove(TetrisGame game, int moveX, int moveY)
    {
        final Cell[][] cellMatrix = getBlock().toCellMatrix();

        // Iterate over every Cell in the matrix
        for (int y = 0; y < cellMatrix.length; y++)
        {
            for (int x = 0; x < cellMatrix[y].length; x++)
            {
                // If there is a Cell at the current position, check if it can move in the given direction
                if (cellMatrix[y][x].isSolid())
                {
                    // If there is a Cell at the new position, the current Cell can't be moved
                    if (game.getCell(getX() + x + moveX, getY() + y + moveY).isSolid())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void move(int moveX, int moveY)
    {
        setX(getX() + moveX);
        setY(getY() + moveY);
    }

    public void placeBlock(TetrisGame game)
    {
        game.placeInGrid(this.getBlock().toCellMatrix(), getX(), getY());
    }
}
