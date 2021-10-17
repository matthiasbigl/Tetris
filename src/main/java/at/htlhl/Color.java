package at.htlhl;

import java.util.Random;

public class Color
{
	// Constants **************************************************************
	public static final Color TRANSPARENT = new Color(0D, 0D, 0D, 0D);
	// Fields *****************************************************************
	private double red;
	private double green;
	private double blue;
	private double alpha;

	// Constructors ***********************************************************
	Color(double red, double green, double blue, double alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	Color(double red, double green, double blue)
	{
		this(red, green, blue, 1D);
	}

	public int getRed() {
		return (int) (red * 255);
	}

	// Accessors **************************************************************
	public int getGreen() {
		return (int) (green * 255);
	}

	public int getBlue() {
		return (int) (blue * 255);
	}

	public int getAlpha() {
		return (int) (alpha * 255);
	}

	public String getHex()
	{
		return String.format("#%02x%02x%02x%02x", getRed(), getBlue(), getGreen(), getAlpha());
	}

	// Static *****************************************************************
	public static Color randomColor(boolean randomizeAlpha)
	{
		final Random random = new Random();
		return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), randomizeAlpha ? random.nextDouble() : 1D);
	}
}
