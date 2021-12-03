package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.Direction;
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
    
    // Accessors **************************************************************
    @Override
    public Cell getVisibleCell(int cellX, int cellY)
    {
        final int relativeX = cellX - fallingBlock.getX();
        final int relativeY = cellY - fallingBlock.getY();

        if(fallingBlock.getBlockState().toGrid().isCellInBounds(relativeX, relativeY))
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
        this.fallingBlockMatrix = fallingBlock.getBlockState().toCellMatrix();
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
        return block.getDefaultState().falling(centeredX, 0);
    }
}
