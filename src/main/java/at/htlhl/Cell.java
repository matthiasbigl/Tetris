package at.htlhl;

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
     * Whether the tile is visible (= has collision)
     */
    private boolean isVisible;

    // Constructors ***********************************************************
    public Cell(final Color color, boolean isVisible)
    {
        this.color = color;
        this.isVisible = isVisible;
    }

    public Cell(final Color color)
    {
        this(color, false);
    }
    
    /**
     * Constructor with default color
      */
    public Cell()
    {
        this(Color.TRANSPARENT);
    }

    // Logic ******************************************************************
    public void setVisible(boolean visible)
    {
        isVisible = visible;
    }
    
    public boolean isVisible()
    {
        return isVisible;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return isVisible() ? color : Color.TRANSPARENT;
    }
    
    @Override
    public String toString()
    {
        return "Cell{" +
                color +
                ", " + isVisible +
                '}';
    }

    @Override
    protected Cell clone() {
        return new Cell(this.getColor(), this.isVisible());
    }
}
