package at.htlhl.javafxtetris.grid;

import at.htlhl.javafxtetris.graphics.Color;

/**
 * Represents a tile in the Tetris grid
 */
public class Cell
{
    // Fields *****************************************************************
    /**
     * {@link Color} of the tile
     */
    private Color color;
    /**
     * Whether the tile is solid
     */
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
    
    public boolean isSolid()
    {
        return isSolid;
    }

    public void setColor(Color color) {
        this.color = color;
    }

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
