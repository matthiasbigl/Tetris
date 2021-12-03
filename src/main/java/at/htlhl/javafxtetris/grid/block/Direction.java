package at.htlhl.javafxtetris.grid.block;

public enum Direction
{
	RIGHT(1, 0),
	LEFT(-1, 0),
	DOWN(0, 1);
	
	private int x, y;
	
	Direction(int moveX, int moveY)
	{
		this.x = moveX;
		this.y = moveY;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
