package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.graphics.Color;

/**
 * Represents a Tetris block
 */
public enum Block
{
    // TODO: Change to 4x4 matrix for every Block
    RIGHT_L(Color.ORANGE, new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, true, false},
        {false, false, false, false}
    }),
    LEFT_L(Color.BLUE, new boolean[][] {
        {false, false, true, false},
        {false, false, true, false},
        {false, true, true, false},
        {false, false, false, false}
    }),
    RIGHT_Z(Color.RED, new boolean[][] {
        {false, false, false, false},
        {false, false, true, true},
        {false, true, true, false},
        {false, false, false, false}
    }),
    LEFT_Z(Color.GREEN, new boolean[][] {
        {false, false, false, false},
        {false, true, true, false},
        {false, false, true, true},
        {false, false, false, false}
    }),
    I_SHAPE(Color.LIGHT_BLUE, new boolean[][] {
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false},
        {false, true, false, false}
    }),
    STAIRS(Color.PURPLE, new boolean[][] {
        {false, false, false, false},
        {false, false, true, false},
        {false, true, true, true},
        {false, false, false, false}
    }),
    BRICK(Color.YELLOW, new boolean[][] {
        {false, false, false, false},
        {false, true, true, false},
        {false, true, true, false},
        {false, false, false, false}
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
    
    // Accessors ******************************************************************
    public int getWidth()
    {
        // TODO: Maybe have size variables for every Block
        // Technically this could be an issue if an array representing a row is larger than 4
        // This should never happen, since all Blocks are 4 wide
        // Safety check if the y-Array is empty
        return (blockMatrix.length == 0) ? 0 : blockMatrix[0].length;
    }
    
    public int getHeight()
    {
        return blockMatrix.length;
    }
    
    public boolean[][] getBlockMatrix()
    {
        return blockMatrix;
    }
    
    public Color getColor()
    {
        return blockColor;
    }
    
    // Logic
    public Cell[][] toCellMatrix()
    {
        final boolean[][] blockMatrix = getBlockMatrix();
        final Cell[][] cellMatrix = new Cell[blockMatrix.length][];
        
        for (int y = 0; y < blockMatrix.length; y++)
        {
            cellMatrix[y] = new Cell[blockMatrix[y].length];
            for (int x = 0; x < blockMatrix[y].length; x++)
            {
                cellMatrix[y][x] = new Cell(getColor(), blockMatrix[y][x]);
            }
        }
        
        return cellMatrix;
    }
    
    public FallingBlock falling(int x, int y)
    {
        return new FallingBlock(this, x, y);
    }

    public FallingBlock falling()
    {
        return falling(0, 0);
    }
    
    public static Block randomBlock()
    {
        return Block.values()[(int) (Math.random() * Block.values().length)];
    }
}