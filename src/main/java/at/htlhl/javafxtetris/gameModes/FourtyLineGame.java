package at.htlhl.javafxtetris.gameModes;

import at.htlhl.javafxtetris.App;
import at.htlhl.javafxtetris.TetrisGame;
import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.scene.Scene;

/**
 * @author Bigl Matthias
 * Class that adds the 40 LIne Gamemode
 * Stops the time it takes to clear a given (40) Lines
 */


public class FourtyLineGame extends TetrisGame {


    private long start;
    private long time;
    private final int linesToClear=20;


    /**
     * calls the Tetris Game constructor and measures current system time
     *
     * @param controller
     * @param scene
     */
    public FourtyLineGame(TetrisController controller, Scene scene) {

        super(controller, scene);
        startCounter();

    }

    /**
     * calls the tick methode of TetrisGame and adds a check of the current linesClearedProperty if it is equal
     * to the given number to win a game teh gameWon methode is called
     */
    public void tick() {

        super.tick();

        if (linesClearedProperty().get() >= linesToClear) {
            long end = System.currentTimeMillis();
            time = end - start;
            gameWon();
        }

    }

    public void terminateGame(){

        // Lose
        this.stop();
        App.instance().loadLosingScreen();
        return;

    }

    /**
     *Calls the winningScreen with the time it took to complete a game
     */
    private void gameWon() {
        double seconds = time / 1000.0;
        String timeAsString = String.valueOf(seconds);
        App.instance().loadWinningScreen("Deine Zeit:", timeAsString + " s");
        stop();

    }


    private void startCounter() {
        start = System.currentTimeMillis();
    }
}
