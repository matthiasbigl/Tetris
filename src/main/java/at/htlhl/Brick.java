package at.htlhl;

public class Brick {

    /*
     * * * *
     * * * *
     * * * *
     * * * *
     */

    int[] brickMatrix;

    public Brick() {

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


