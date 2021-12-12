package at.htlhl.javafxtetris.grid.block;

public enum Rotation
{
    ROTATE_LEFT_90(new int[][] 
    {
        { 0, -1}, 
        {+1,  0}
    }),
    ROTATE_RIGHT_90(new int[][]
    {
        { 0, +1},
        {-1,  0}
    }),
    ROTATE_180(new int[][]
    {
        {-1,  0},
        { 0, -1}
    });
    
    private final int[][] rotationMatrix;
    
    Rotation(final int[][] matrix)
    {
        this.rotationMatrix = matrix;
    }
    
    public int[][] getRotationMatrix()
    {
        return rotationMatrix;
    }
}