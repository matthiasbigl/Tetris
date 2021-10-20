package at.htlhl;

/**
 * Represents a Tetris block
 */
public enum Block
{
    RIGHT_L(Color.ORANGE, new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, true, false}
    }),
    LEFT_L(Color.BLUE, new boolean[][] {
        {false, false, true, false},
        {false, false, true, false},
        {false, true, true, false}
    }),
    RIGHT_Z(Color.RED, new boolean[][] {
        {false, true, true, false},
        {true, true, false, false}
    }),
    LEFT_Z(Color.GREEN, new boolean[][] {
        {false, true, true, false},
        {false, false, true, true}
    }),
    I_SHAPE(Color.LIGHT_BLUE, new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false}
    }),
    STAIRS(Color.PURPLE, new boolean[][] {
        {false, true, false, false},
        {true, true, true, false}
    }),
    BRICK(Color.YELLOW, new boolean[][] {
        {false, true, true, false},
        {false, true, true, false}
    });
    
    // Fields *****************************************************************
    private final Color blockColor;
    private final boolean[][] blockMatrix;
    
    // Constructors ***********************************************************
    Block(final Color color, final boolean[][] blockMatrix)
    {
        this.blockColor = color;
        this.blockMatrix = blockMatrix;
    }
    
    // Logic ******************************************************************
    public boolean[][] getBlockMatrix()
    {
        return blockMatrix;
    }
    
    public static Block randomBlock()
    {
        return Block.values()[(int) (Math.random() * Block.values().length)];
    }
}
