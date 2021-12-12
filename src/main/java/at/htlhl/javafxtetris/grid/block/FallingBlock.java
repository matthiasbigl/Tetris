package at.htlhl.javafxtetris.grid.block;

import at.htlhl.javafxtetris.grid.Grid;

public class FallingBlock
{
    private BlockState blockState;
    private int x, y;
    
    // Constructors ***********************************************************
    public FallingBlock(BlockState state, int x, int y)
    {
        this.blockState = state;
        this.x = x;
        this.y = y;
    }
    
    // Accessors **************************************************************
    public BlockState getBlockState()
    {
        return blockState;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    // Logic ******************************************************************
    
    /**
     * Attempts to move the Block in the specified {@link Direction}
     *
     * @param gridIn   The {@link Grid} to move in
     * @param movement movement {@link Direction}
     * @return Whether the Block was moved
     */
    public boolean tryMove(Grid gridIn, Direction movement)
    {
        if(!canMove(gridIn, movement))
            return false;
        
        move(movement);
        return true;
    }
    
    /**
     * Checks if the block can move in the given directions
     *
     * @param gridIn   The gridIn object
     * @param movement The movement direction
     * @return Whether the Block can move in the given directions
     */
    public boolean canMove(Grid gridIn, Direction movement)
    {
        return gridIn.canPlaceCells(getBlockState().getGrid(), getX() + movement.getX(), getY() + movement.getY());
    }
    
    /**
     * moves the block in the specified directions
     *
     * @param movement Y movement
     */
    public void move(Direction movement)
    {
        setX(getX() + movement.getX());
        setY(getY() + movement.getY());
    }
    
    /**
     * Attempts to rotate the Block
     *
     * @param gridIn   The {@link Grid} to rotate in
     * @param rotation rotation {@link Direction}
     * @return Whether the Block was rotated
     */
    public boolean tryRotate(Grid gridIn, Rotation rotation)
    {
        if(!canRotate(gridIn, rotation))
            return false;
        
        rotate(rotation);
        return true;
    }
    
    /**
     * Checks if the block can rotate in the given direction
     *
     * @param gridIn   The gridIn object
     * @param rotation The rotation direction
     * @return Whether the Block can rotate
     */
    public boolean canRotate(Grid gridIn, Rotation rotation)
    {
        final BlockState rotatedState = blockState.rotate(rotation);
        return gridIn.canPlaceCells(rotatedState.getGrid(), getX(), getY());
    }
    
    public void rotate(final Rotation rotation)
    {
        this.blockState = blockState.rotate(rotation);
    }
    
    /**
     * Attempts to place the Block in the specified {@link Grid}
     *
     * @param grid The {@link Grid} to place the Block in
     * @return Whether the Block was placed
     */
    public boolean tryPlaceBlockIn(Grid grid)
    {
        if(!canPlaceBlockIn(grid))
            return false;
        
        placeBlockIn(grid);
        return true;
    }
    
    /**
     * Places the Block in the specified Grid
     *
     * @param grid The {@link Grid} to place the {@link Block} in
     */
    public void placeBlockIn(Grid grid)
    {
        grid.placeCellsInGrid(this.getBlockState().getGrid(), getX(), getY());
    }
    
    public boolean canPlaceBlockIn(Grid grid)
    {
        return grid.canPlaceCells(getBlockState().getGrid(), getX(), getY());
    }
}
