package at.htlhl;

import java.util.Random;

public class Brick
{
    // Fields *****************************************************************
    int[] brickMatrix;

    // Constructors ***********************************************************
    public Brick()
    {
        Random rand = new Random();
        int randIndex = rand.nextInt(6 + 1);

        String[] bricks = new String[]{
                "orangeRicky","blueRicky",
                "clevelandZ","rhodeIslandZ",
                "hero","teewee","smashBoy"};

        switch (bricks[randIndex]) {
            case "orangeRicky":
                orangeRicky();
                break;
            case "blueRicky":
                orangeRicky();
                break;
            case "clevelandZ":
                orangeRicky();
                break;
            case "rhodeIslandZ":
                orangeRicky();
                break;
            case "hero":
                orangeRicky();
                break;
            case "teewee":
                orangeRicky();
                break;
            case "smashBoy":
                orangeRicky();
                break;
            default:
                return;
        }
    }

    public int[] get() {
        return brickMatrix;
    }

    private void orangeRicky() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 1,
                0, 1, 1, 1};
    }

    private void blueRicky() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                1, 0, 0, 0,
                1, 1, 1, 0};

    }

    private void clevelandZ() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                1, 1, 0, 0,
                0, 1, 1, 0};

    }

    private void rhodeIslandZ() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 1, 1,
                0, 1, 1, 0};

    }

    private void hero() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                1, 1, 1, 1};

    }

    private void teewee() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0};

    }

    private void smashBoy() {
        this.brickMatrix = new int[]{0, 0, 0, 0,
                0, 0, 0, 0,
                1, 1, 0, 0,
                1, 1, 0, 0};
    }
}


