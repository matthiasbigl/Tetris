package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.graphics.Color;

/**
 * Represents a tile in a {@link Grid}
 */
public class Cell
{
    // Fields *****************************************************************
    private Color color;
    private boolean isSolid;

    // Constructors ***********************************************************
    public Cell(final Color color, boolean isSolid)
    {
        this.color = color;
        this.isSolid = isSolid;
    }

    public Cell(boolean isSolid)
    {
        this(Color.TRANSPARENT, isSolid);
    }
    
    // Logic ******************************************************************
    public void setSolid(boolean solid)
    {
        isSolid = solid;
    }
    
    /**
     * @returns Whether the tile is solid
     */
    public boolean isSolid()
    {
        return isSolid;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * @returns The {@link Color} of the tile
     */
    public Color getColor() {
        return isSolid() ? color : Color.TRANSPARENT;
    }
    
    @Override
    public String toString()
    {
        return "Cell{" +
                color +
                ", " + isSolid +
                '}';
    }

    @Override
    public Cell clone() {
        return new Cell(this.getColor(), this.isSolid());
    }
}
