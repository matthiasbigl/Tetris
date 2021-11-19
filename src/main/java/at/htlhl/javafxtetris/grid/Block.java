package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.graphics.Color;

import java.util.Arrays;

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

    // Logic
    public boolean isCellInBounds(int cellX, int cellY)
    {
        return (cellY >= 0 && cellY < getHeight()) && (cellX >= 0 && cellX < blockMatrix[cellY].length);
    }

    /**
     * Converts this Block to a Cell matrix
     *
     * @return A 2 dimensional {@link Cell} array
     */
    public Cell[][] toCellMatrix()
    {
        final boolean[][] blockMatrix = getBlockMatrix();
        final Cell[][] cellMatrix = new Cell[blockMatrix.length][];

        for(int y = 0; y < blockMatrix.length; y++)
        {
            cellMatrix[y] = new Cell[blockMatrix[y].length];
            for(int x = 0; x < blockMatrix[y].length; x++)
            {
                cellMatrix[y][x] = new Cell(getColor(), blockMatrix[y][x]);
            }
        }

        return cellMatrix;
    }

    /**
     * Creates a new Grid using the {@link Cell} matrix of this {@link Block}
     *
     * @return A {@link Grid} object
     */
    public Grid toGrid()
    {
        return new Grid(toCellMatrix());
    }

    /**
     * Constructs a falling block at the specified location
     *
     * @param x The x position of the falling Block
     * @param y The y position of the falling Block
     * @return A {@link FallingBlock}
     */
    public FallingBlock falling(int x, int y)
    {
        return new FallingBlock(this, x, y);
    }

    /**
     * Constructs a falling block at the location (0|0)
     *
     * @return A {@link FallingBlock}
     */
    public FallingBlock falling()
    {
        return falling(0, 0);
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

    public int getTrueWith() {
        int with = 0;
        for (int y = 0; y < blockMatrix.length; y++) {
            for (int x = 0; x < blockMatrix[y].length; x++) {
                if (blockMatrix[y][x] == true) {
                    if (with < x) {
                        with = x;
                    }
                }
            }
        }
        return with+1;
    }

    public int getTrueHeight() {
        int height = 0;
        for (int y = 0; y < blockMatrix.length; y++) {
            for (int x = 0; x < blockMatrix[y].length; x++) {
                if (blockMatrix[y][x] == true) {
                    height = y;
                }
            }
        }
        return height+1;
    }

    public boolean[][] getBlockMatrix()
    {
        return blockMatrix;
    }

    public Color getColor()
    {
        return blockColor;
    }

}
