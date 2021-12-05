package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.FallingBlock;

public class TetrisGrid extends Grid
{
    // Fields *****************************************************************
    private FallingBlock fallingBlock;

    // Constructors ***********************************************************
    public TetrisGrid(int width, int height, Block fallingBlock)
    {
        super(width, height);
        setFallingBlock(fallingBlock);
    }
    
    // Accessors **************************************************************
    public void setFallingBlock(final FallingBlock fallingBlock)
    {
        this.fallingBlock = fallingBlock;
    }

    public void setFallingBlock(final Block block)
    {
        setFallingBlock(createFallingBlock(block));
    }

    public FallingBlock getFallingBlock()
    {
        return fallingBlock;
    }

    private FallingBlock createFallingBlock(final Block block)
    {
        int centeredX = (getWidth() / 2) - (block.getWidth() / 2);
        return block.getDefaultState().falling(centeredX, 0);
    }
}
