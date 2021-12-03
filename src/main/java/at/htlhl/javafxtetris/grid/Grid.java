package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.graphics.TetrisController;

public class Grid
{
    // Fields *****************************************************************
    private final Cell[][] cellMatrix;

    // Constructors ***********************************************************
    /**
     * Constructs a new grid using the specified {@link Cell} matrix
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
     */
    public void deleteFullLines()
    {
        int amount = 0;
        for (int y = 0; y < cellMatrix.length; y++) {
            if (isLineFull(y)) {
                deleteLine(y);
                amount++;
            }
        }

        // update points, lines and level
        addStats(amount);
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

        cellMatrix[lineY] = createEmptyLine(cellMatrix[lineY].length);
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
            return createEmptyLine(10); // TODO: Add width and height
	
		return cellMatrix[lineY];
    }
	
	/**
	 * Places the specified line at the specified position in the grid
	 *
	 * @param newLine The line to place as a {@link Cell} array
	 * @param lineY       The {@link Cell} to place
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
     * @param posX  The x position
     * @param posY  The y position
     */
    public void placeCellsInGrid(Grid cellsToPlace, int posX, int posY)
    {
        for(int currY = 0; currY < cellsToPlace.getHeight(); currY++)
        {
            for(int x = 0; x < cellsToPlace.getWidth(); x++)
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
        for(int currY = 0; currY < cellsToPlace.getHeight(); currY++)
        {
            for(int currX = 0; currX < cellsToPlace.getWidth(); currX++)
            {
                final Cell cellToPlace = cellsToPlace.getCell(currX, currY);
                if(cellToPlace.isSolid() && getCell(posX + currX, posY + currY).isSolid())
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
	 * Returns the Cell that is visible at the specified position in the Grid
	 *
	 * @param cellX The x position of the Cell
	 * @param cellY The y position of the Cell
	 * @return A {@link Cell} object from the Cell matrix
	 */
	public Cell getVisibleCell(int cellX, int cellY)
    {
        return getCell(cellX, cellY);
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
		if (!isCellInBounds(cellX, cellY))
			return currentCell;
		
		cellMatrix[cellY][cellX] = newCell;
		return currentCell;
	}
	
    /**
     * Returns the Cell at the specified position in the Grid
     * @param cellX The x position of the Cell
     * @param cellY The y position of the Cell
     * @return A {@link Cell} object from the Cell matrix
     */
    public Cell getCell(int cellX, int cellY)
    {
        if (!isCellInBounds(cellX, cellY))
			return new Cell(true);
		
		return cellMatrix[cellY][cellX];
    }
    
    public int getHeight()
    {
        return cellMatrix.length;
    }

    public int getWidth() {
        return getLine(0).length;
    }

    /**
     * Method to add Points, Level and lines based on the amount of lines removed
     * @param amountOfLines The amount of removed lines
     */
    private void addStats(int amountOfLines) {
        // TODO: set score and lines to 0 if lost

        // add amount of lines deleted to lines
        TetrisController.lines += amountOfLines;

        // add points to score depending on level
        // scoring system from https://tetris.fandom.com/wiki/Scoring
        switch (amountOfLines) {
            case 1:
                TetrisController.score += 40 * (TetrisController.level + 1);
                break;
            case 2:
                TetrisController.score += 100 * (TetrisController.level + 1);
                break;
            case 3:
                TetrisController.score += 300 * (TetrisController.level + 1);
                break;
            case 4:
                TetrisController.score += 1200 * (TetrisController.level + 1);
                break;
            default:
                break;
        }

        // if 10 lines got removed, increase level by one
        // TODO: increase tick speed depending on level
        // TODO: show level in graphic
        if (TetrisController.lines > 0) {
            if (TetrisController.lines % 10 == 0) {
                TetrisController.level = TetrisController.lines / 10;
            }
        }
    }
}