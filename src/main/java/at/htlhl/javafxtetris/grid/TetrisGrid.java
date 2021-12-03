package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.FallingBlock;

public class TetrisGrid extends Grid
{
    // Fields *****************************************************************
    private FallingBlock fallingBlock;
    private Cell[][] fallingBlockMatrix;
    private boolean didBlockFall;

    // Constructors ***********************************************************
    public TetrisGrid(int width, int height, Block fallingBlock)
    {
        super(width, height);
        setFallingBlock(fallingBlock);
    }

    // Logic ******************************************************************

    /**
     * Moves the {@link FallingBlock} down one cell.<br>
     * Updates the didBlockFall variable. See {@link TetrisGrid#didBlockFall()}
     */
    public void tick()
    {
        this.didBlockFall = false;
        if(fallingBlock.canMove(this, 0, 1))
        {
            fallingBlock.move(0, 1);
            this.didBlockFall = true;
        }
    }

    // Accessors **************************************************************
    @Override
    public Cell getVisibleCell(int cellX, int cellY)
    {
        final int relativeX = fallingBlock.getRelativeX(cellX);
        final int relativeY = fallingBlock.getRelativeY(cellY);

        if(fallingBlock.getBlock().isCellInBounds(relativeX, relativeY))
        {
            final Cell cell = fallingBlockMatrix[relativeY][relativeX];
            if(cell.isSolid())
            {
                return cell;
            }
        }
        
        return getCell(cellX, cellY);
    }

    public void setFallingBlock(final FallingBlock fallingBlock)
    {
        this.fallingBlock = fallingBlock;
        this.fallingBlockMatrix = fallingBlock.getBlock().toCellMatrix();
    }

    public void setFallingBlock(final Block block)
    {
        setFallingBlock(createFallingBlock(block));
    }

    public FallingBlock getFallingBlock()
    {
        return fallingBlock;
    }

    /**
     * @return Whether the {@link FallingBlock} has been moved down last tick
     */
    public boolean didBlockFall()
    {
        return didBlockFall;
    }

    private FallingBlock createFallingBlock(final Block block)
    {
        int centeredX = (getLine(0).length / 2) - (block.getWidth() / 2);
        return block.falling(centeredX, 0);
    }
}
