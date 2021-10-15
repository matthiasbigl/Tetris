package at.htlhl;

import javafx.scene.paint.Color;

public class Cell
{
    // Fields *****************************************************************
    private final Color color;
    private boolean isVisible;
    
    // Constructors ***********************************************************
    public Cell(final Color color)
    {
        this.color = color;
        this.isVisible = false;
    }
    
    /**
     * Constructor with default color
      */
    public Cell()
    {
        this(Color.WHITE);
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
}
