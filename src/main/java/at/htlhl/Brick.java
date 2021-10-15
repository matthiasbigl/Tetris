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

    private int[] orangeRicky() {
        this.brickMatrix = new int[]{   0, 0, 0, 0,
                                        0, 0, 0, 0,
                                        0, 0, 0, 1,
                                        0, 1, 1, 1};
        return brickMatrix;
    }

    private int[] blueRicky() {
        this.brickMatrix = new int[] {}
    }

}
