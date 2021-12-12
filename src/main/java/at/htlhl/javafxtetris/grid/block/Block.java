package at.htlhl.javafxtetris.grid.block;

import at.htlhl.javafxtetris.graphics.Color;

import java.util.Arrays;

/**
 * Represents a Tetris block
 */
public enum Block
{
    // TODO: if first line has no true, spawns one line too low
    RIGHT_L(Color.ORANGE, 1D, 1D, new boolean[][] {
            {false, true, false, false},
            {false, true, false, false},
            {false, true, true, false},
            {false, false, false, false}
    }),
    LEFT_L(Color.BLUE, 2D, 1D, new boolean[][] {
            {false, false, true, false},
            {false, false, true, false},
            {false, true, true, false},
            {false, false, false, false}
    }),
    RIGHT_Z(Color.RED, 2D, 2D, new boolean[][] {
            {false, false, false, false},
            {false, false, true, true},
            {false, true, true, false},
            {false, false, false, false}
    }),
    LEFT_Z(Color.GREEN, 2D, 2D, new boolean[][] {
            {false, false, false, false},
            {false, true, true, false},
            {false, false, true, true},
            {false, false, false, false}
    }),
    I_SHAPE(Color.LIGHT_BLUE, 1.5D, 1.5D, new boolean[][] {
            {false, true, false, false},
            {false, true, false, false},
            {false, true, false, false},
            {false, true, false, false}
    }),
    STAIRS(Color.PURPLE, 2, 2, new boolean[][] {
            {false, false, false, false},
            {false, false, true, false},
            {false, true, true, true},
            {false, false, false, false}
    }),
    BRICK(Color.YELLOW, 1.5D, 1.5D, new boolean[][] {
            {false, false, false, false},
            {false, true, true, false},
            {false, true, true, false},
            {false, false, false, false}
    });

    // Fields *****************************************************************
    private final Color blockColor;
    private final boolean[][] blockMatrix;
    private final double originX;
    private final double originY;

    // Constructors ***********************************************************
    Block(final Color color, final double originX, final double originY, final boolean[][] blockMatrix)
    {
        this.blockColor = color;
        this.blockMatrix = blockMatrix;
        this.originX = originX;
        this.originY = originY;
    }

    // Logic ******************************************************************
    public BlockState getDefaultState()
    {
        return new BlockState(this);
    }

    /**
     * Returns a random Block
     *
     * @return A random {@link Block} object
     */
    public static Block randomBlock()
    {
        return Block.values()[(int) (Math.random() * Block.values().length)];
    }

    // Accessors ******************************************************************
    public int getWidth()
    {
        // Technically this could be an issue if a row in the Block is longer than 4
        // This should never happen, since all Blocks are 4 in width
        return (blockMatrix.length == 0) ? 0 : blockMatrix[0].length;
    }

    public int getHeight()
    {
        return blockMatrix.length;
    }
    
    public double getOriginX()
    {
        return originX;
    }
    
    public double getOriginY()
    {
        return originY;
    }
    
    /**
     * Returns a copy of the Block matrix
     * @return A boolean array
     */
    public boolean[][] getBlockMatrix()
    {
        return Arrays.copyOf(blockMatrix, blockMatrix.length);
    }

    public Color getDefaultColor()
    {
        return blockColor;
    }
}
