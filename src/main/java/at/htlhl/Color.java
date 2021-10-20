package at.htlhl;

import java.util.Random;

public class Color
{
	// Constants **************************************************************
	public static final Color TRANSPARENT = new Color(0D, 0D, 0D, 0D);
	public static final Color ORANGE = new Color(.8D, .4D, .1D);
	public static final Color BLUE = new Color(0D, 0D, 1D);
	public static final Color RED = new Color(1D, 0D, 0D);
	public static final Color GREEN = new Color(0D, 1D, 0D);
	public static final Color LIGHT_BLUE = new Color(.1D, 0.5D, .85D);
	public static final Color PURPLE = new Color(.8D, 0.15D, .85D);
	public static final Color YELLOW = new Color(0D, 1D, 1D);

	// Fields *****************************************************************
	private final double red;
	private final double green;
	private final double blue;
	private final double alpha;

	// Constructors ***********************************************************
	/**
	 * Default RGBA constructor. All values are doubles from 0.0 to 1.0
	 * @param red red value
	 * @param green green value
	 * @param blue blue value
	 * @param alpha alpha value (opacity: 0 = 100% transparent)
	 */
	Color(double red, double green, double blue, double alpha)
	{
		checkRGBA(red, green, blue, alpha);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	/**
	 * RGB Constructor, 100% opaque
	 * @param red red value
	 * @param green green value
	 * @param blue blue value
	 */
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

	// Logic ******************************************************************
	/**
	 * Throws an exception when any color value is invalid
	 * @param red
	 * @param green
	 * @param blue
	 * @param alpha
	 */
	private static void checkRGBA(double red, double green, double blue, double alpha)
	{
		checkRGB(red, green, blue);
		if (alpha < 0D || alpha > 1D)
		{
			throw new IllegalArgumentException("value for alpha must be in range [0-1] (" + alpha + ")");
		}
	}

	/**
	 * Throws an exception when any color value is invalid
	 * @param red
	 * @param green
	 * @param blue
	 */
	private static void checkRGB(double red, double green, double blue)
	{
		if (red < 0D || red > 1D)
		{
			throw new IllegalArgumentException("value for red must be in range [0-1] (" + red + ")");
		}
		if (green < 0D || green > 1D)
		{
			throw new IllegalArgumentException("value for green must be in range [0-1] (" + green + ")");
		}
		if (blue < 0D || blue > 1D)
		{
			throw new IllegalArgumentException("value for blue must be in range [0-1] (" + blue + ")");
		}
	}

	// Static *****************************************************************
	public static Color randomColor(boolean randomizeAlpha)
	{
		final Random random = new Random();
		return new Color(random.nextDouble(), random.nextDouble(), random.nextDouble(), randomizeAlpha ? random.nextDouble() : 1D);
	}
}
