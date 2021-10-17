package at.htlhl;


public class Cell
{
    // Fields *****************************************************************
    private Color color;
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
        return color;
    }
}
