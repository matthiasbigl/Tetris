package at.htlhl.javafxtetris.gameModes;

import at.htlhl.javafxtetris.TetrisGame;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class BlitzGame extends Service<Integer> {

    private Timer playTimer;
    private TetrisGame game;
    private Stage primaryStage;

    public BlitzGame(TetrisGame game, Stage primaryStage) {
        this.game = game;
        this.primaryStage = primaryStage;
        start();
    }

    @Override
    protected Task<Integer> createTask(){
        return new counterTask();
    }

    private class counterTask extends Task<Integer> {
        public counterTask() {
            super();
        }

        @Override
        protected Integer call(){
            playTimer = new Timer();
            playTimer.scheduleAtFixedRate(new TimerTask() {
                int counter;
                @Override
                public void run() {
                    counter++;
                    if(counter == 5){
                        game.pause();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                game.createMessageBox(primaryStage);
                            }
                        });
                        playTimer.cancel();
                    }
                    System.out.println(counter);
                }
            } , 1000, 1000);
            return null;
        }
    }


}
