package at.htlhl.javafxtetris.grid;

public class TetrisGrid extends Grid
{
    // Fields *****************************************************************
    private final int yOffset;

    // Constructors ***********************************************************
    public TetrisGrid(int width, int height, int yOffset)
    {
        super(width, height);
        this.yOffset = yOffset;
    }
    
    @Override
    public Cell getCell(int cellX, int cellY)
    {
        if(isCellInOffsetBounds(cellX, cellY))
           return new Cell(false); 
        
        return super.getCell(cellX, cellY);
    }
    
    public int getYOffset()
    {
        return yOffset;
    }
    
    /*
     * Checks whether the Cell at the specified position is OUTSIDE OF THE GRID but INSIDE THE GRID OFFSET
     */
    private boolean isCellInOffsetBounds(final int cellX, final int cellY)
    {
        return (cellY >= (0 - yOffset) && cellY < 0) && isCellInBounds(cellX, 0);
    }
}
