package at.htlhl.javafxtetris.gameModes;

import at.htlhl.javafxtetris.App;
import at.htlhl.javafxtetris.TetrisGame;
import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.scene.Scene;




public class BlitzGame extends TetrisGame {


    private long start;
    private final int StopingTime=120000;


    /**
     * calls the TetrisGame Constructor and measures current System Time
     * @param controller
     * @param scene
     */
    public BlitzGame(TetrisController controller, Scene scene) {


        super(controller, scene);
        startCounter();

    }

    /**
     * subtracts the start time from the current time and checks if time is up if so calls gameWon()
     */

    public void tick() {
        super.tick();

        if (System.currentTimeMillis()-start >= StopingTime) {
          gameWon();
        }
    }

    /**
     * is called when a game is Won
     * stops the game and Loads the Winning screen with the LInes Cleared
     */
    private void gameWon() {
        App.instance().loadWinningScreen("Lines Cleared:", String.valueOf(linesClearedProperty().get()));
        stop();
    }


    private void startCounter() {
        start = System.currentTimeMillis();
    }
}
