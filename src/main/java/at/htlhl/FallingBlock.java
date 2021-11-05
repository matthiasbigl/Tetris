package at.htlhl;

public class FallingBlock
{
	private final Block block;
	private int x, y;
	
	public FallingBlock(Block block, int x, int y)
	{
		this.block = block;
		this.x = x;
		this.y = y;
	}
	
	public FallingBlock(Block block)
	{
		this(block, 0, 0);
	}
	
	public Block getBlock()
	{
		return block;
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
	
	public void move(int moveX, int moveY)
	{
		setX(getX() + moveX);
		setY(getY() + moveY);
	}
}
