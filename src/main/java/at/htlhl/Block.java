package at.htlhl;

/**
 * Represents a Tetris block
 */
public enum Block
{
    RIGHT_L(new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, true, false}
    }),
    LEFT_L(new boolean[][] {
        {false, false, true, false},
        {false, false, true, false},
        {false, true, true, false}
    }),
    RIGHT_Z(new boolean[][] {
        {false, true, true, false},
        {true, true, false, false}
    }),
    LEFT_Z(new boolean[][] {
        {false, true, true, false},
        {false, false, true, true}
    }),
    I_SHAPE(new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false}
    }),
    STAIRS(new boolean[][] {
        {false, true, false, false},
        {true, true, true, false}
    }),
    BRICK(new boolean[][] {
        {false, true, true, false},
        {false, true, true, false}
    });
    
    // Fields *****************************************************************
    private boolean[][] blockMatrix;
    
    // Constructors ***********************************************************
    Block(final boolean[][] blockMatrix)
    {
        this.blockMatrix = blockMatrix;
    }
    
    // Accessors **************************************************************
    public boolean[][] getBlockMatrix()
    {
        return blockMatrix;
    }
}
