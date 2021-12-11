package at.htlhl.javafxtetris.grid;

public class Grid
{
    // Fields *****************************************************************
    private final Cell[][] cellMatrix;
    
    // Constructors ***********************************************************
    
    /**
     * Constructs a new grid using the specified {@link Cell} matrix
     *
     * @param cellMatrix The initial contents of this {@link Grid}
     */
    public Grid(Cell[][] cellMatrix)
    {
        this.cellMatrix = cellMatrix;
        initCellMatrix();
    }
    
    /**
     * Constructs a new empty {@link Grid} with the specified dimensions
     *
     * @param width  The grid width
     * @param height The grid height
     */
    public Grid(int width, int height)
    {
        this(new Cell[height][width]);
    }
    
    // Logic ******************************************************************
    /*
     * Places an empty Cell object if the field in the array is null
     */
    private void initCellMatrix()
    {
        for(int y = 0; y < cellMatrix.length; y++)
        {
            for(int x = 0; x < cellMatrix[y].length; x++)
            {
                // Don't overwrite Cells that are already set
                if(cellMatrix[y][x] == null)
                {
                    cellMatrix[y][x] = new Cell(false);
                }
            }
        }
    }
    
    /**
     * @return The index of the Cell with the lowest y index in the Grid
     */
    public int getLowestCellY()
    {
        for(int y = 0; y < getTotalHeight(); y++)
        {
            for(int x = 0; x < getLine(y).length; x++)
            {
                if(getCell(x, y).isSolid())
                    return y;
            }
        }
        
        return -1;
    }
    
    /**
     * @return The index of the Cell with the highest y index in the Grid
     */
    public int getHighestCellY()
    {
        for(int y = getTotalHeight() - 1; y >= 0; y--)
        {
            for(int x = 0; x < getLine(y).length; x++)
            {
                if(getCell(x, y).isSolid())
                    return y;
            }
        }
    
        return -1;
    }
    
    // Line logic
    
    /**
     * Deletes one line and moves all above lines down one Cell
     *
     * @param lineY The y position of the line
     */
    public void deleteLine(int lineY)
    {
        // Move all lines down by referencing the array of the line above
        // This should be faster than copying every single object
        for(int y = lineY; y > 0; y--)
        {
            this.cellMatrix[y] = cellMatrix[y - 1];
        }
        
        // Clear the very first line, since it's going to reference the same array as the second one
        resetLine(0);
    }
    
    /**
     * Deletes all full lines using {@link Grid#deleteLine(int)}
     *
     * @return The amount of lines deleted
     */
    public int deleteFullLines()
    {
        int amount = 0;
        for(int y = 0; y < cellMatrix.length; y++)
        {
            if(isLineFull(y))
            {
                deleteLine(y);
                amount++;
            }
        }
        
        return amount;
    }
    
    /**
     * Clears a line by creating a new array and Cell objects
     *
     * @param lineY The y position of the line
     */
    private void resetLine(int lineY)
    {
        if(!isLineInBounds(lineY))
        {
            return;
        }
        
        setLine(createEmptyLine(cellMatrix[lineY].length), lineY);
    }
    
    /**
     * Checks whether a line is full (= all {@link Cell}s are solid)
     *
     * @param lineY The y position of the line
     * @return Whether the line is full
     */
    public boolean isLineFull(int lineY)
    {
        Cell[] line = cellMatrix[lineY];
        for(Cell cell : line)
        {
            if(!cell.isSolid())
            {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Checks whether the line at the specified position is IN the grid
     *
     * @param lineY The y position of the line
     * @return Whether the line is in bounds
     */
    public boolean isLineInBounds(int lineY)
    {
        return lineY >= 0 && lineY < cellMatrix.length;
    }
    
    /**
     * Returns the line at the specified position in the grid
     *
     * @param lineY The y index of the line
     * @return A {@link Cell} array
     */
    public Cell[] getLine(int lineY)
    {
        if(!isLineInBounds(lineY))
            return createEmptyLine(getTotalWidth());
        
        return cellMatrix[lineY];
    }
    
    /**
     * Places the specified line at the specified position in the grid
     *
     * @param newLine The line to place as a {@link Cell} array
     * @param lineY   The {@link Cell} to place
     * @return The line that currently is at the specified position as a {@link Cell} array
     */
    public Cell[] setLine(Cell[] newLine, int lineY)
    {
        final Cell[] currentLine = getLine(lineY);
        
        if(!isLineInBounds(lineY))
            return currentLine;
        
        cellMatrix[lineY] = newLine;
        return currentLine;
    }
    
    /*
     * Creates a Cell array containing empty Cells
     */
    private Cell[] createEmptyLine(int length)
    {
        Cell[] line = new Cell[length];
        for(int x = 0; x < line.length; x++)
            line[x] = new Cell(false);
        
        return line;
    }
    
    // Cell logic
    
    /**
     * Places the given {@link Cell}s in the grid at the specified location
     *
     * @param cellsToPlace The {@link Cell}s to place in the grid
     * @param posX         The x position
     * @param posY         The y position
     */
    public void placeCellsInGrid(Grid cellsToPlace, int posX, int posY)
    {
        for(int currY = 0; currY < cellsToPlace.getTotalHeight(); currY++)
        {
            for(int x = 0; x < cellsToPlace.getTotalWidth(); x++)
            {
                final Cell cell = cellsToPlace.getCell(x, currY);
                if(cell.isSolid())
                {
                    setCell(cell, posX + x, posY + currY);
                }
            }
        }
    }
    
    /**
     * Checks if the contents of the specified {@link Grid} can be placed at the x,y coordinates in this {@link Grid}
     *
     * @param cellsToPlace The {@link Cell}s to place
     * @param posX         x position
     * @param posY         y position
     * @return Whether the {@link Grid} contents can be placed
     */
    public boolean canPlace(Grid cellsToPlace, int posX, int posY)
    {
        for(int currY = 0; currY < cellsToPlace.getTotalHeight(); currY++)
        {
            for(int currX = 0; currX < cellsToPlace.getTotalWidth(); currX++)
            {
                final Cell cellToPlace = cellsToPlace.getCell(currX, currY);
                if(cellToPlace.isSolid() && this.getCell(posX + currX, posY + currY).isSolid())
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Checks whether the Cell at the specified position is IN the grid
     *
     * @param cellX The x position of the Cell
     * @param cellY The y position of the Cell
     * @return Whether the {@link Cell} is in bounds
     */
    public boolean isCellInBounds(int cellX, int cellY)
    {
        return isLineInBounds(cellY) && (cellX >= 0 && cellX < cellMatrix[cellY].length);
    }
    
    /**
     * Places a {@link Cell} at the specified position in the {@link Grid}
     *
     * @param newCell The {@link Cell} to place
     * @param cellX   The x position
     * @param cellY   The y position
     * @return The {@link Cell} that currently is at the specified position
     */
    public Cell setCell(final Cell newCell, int cellX, int cellY)
    {
        final Cell currentCell = getCell(cellX, cellY);
        if(!isCellInBounds(cellX, cellY))
            return currentCell;
        
        cellMatrix[cellY][cellX] = newCell;
        return currentCell;
    }
    
    /**
     * Returns the Cell at the specified position in the Grid
     *
     * @param cellX The x position of the Cell
     * @param cellY The y position of the Cell
     * @return A {@link Cell} object from the Cell matrix
     */
    public Cell getCell(int cellX, int cellY)
    {
        if(!isCellInBounds(cellX, cellY))
            return new Cell(true);
        
        return cellMatrix[cellY][cellX];
    }
    
    /**
     * Returns the total height of the Grid (including all empty Cells)
     * 
     * Example:<br>
     * <pre>----  1</pre>
     * <pre>-*--  2</pre>
     * <pre>----  3</pre>
     * <pre>-**-  4</pre>
     * Calling {@link Grid#getTotalHeight()} on this Grid will return 4
     * 
     * @return The height of the Grid
     */
    public int getTotalHeight()
    {
        return cellMatrix.length;
    }
    
    /**
     * Calculates the 'real' height of this {@link Grid}, meaning that only the highest and lowest solid Cells will be taken into account<br>
     * Example:<br>
     * <pre>- - - -</pre>
     * <pre>- * - - 1</pre>
     * <pre>- - - - 2</pre>
     * <pre>- * * - 3</pre>
     * Calling {@link Grid#getRealHeight()} on this Grid will return 4
     *
     * @return The 'real' height of this {@link Grid}
     */
    public int getRealHeight()
    {
        final int topCellY = getLowestCellY();
        final int bottomCellX = getHighestCellY();
    
        if(topCellY < 0 || bottomCellX < 0)
            return 0;
    
        return bottomCellX - topCellY + 1;
    }
    
    /**
     Returns the total width of the Grid (including all empty Cells)
     *
     * Example:<br>
     * <pre>- - - -</pre>
     * <pre>- * - -</pre>
     * <pre>- - - -</pre>
     * <pre>- * * -</pre>
     * <pre>1 2 3 4</pre>
     * Calling {@link Grid#getTotalWidth()} on this Grid will return 4
     *
     * @return The width of the Grid
     */
    public int getTotalWidth()
    {
        return getLine(0).length;
    }
}