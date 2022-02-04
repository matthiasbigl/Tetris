package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.scene.Scene;

import static java.lang.Thread.sleep;


public class FourtyLineGame extends TetrisGame{


    private long start;
    private long end;
    private long time;




    public FourtyLineGame(TetrisController controller, Scene scene) {
        super(controller, scene);
        startCounter();
        Thread t1=new Thread(()->{
        checkLineCount();
        });
        t1.start();
    }

    private void checkLineCount() {
        boolean win=false;
        while (true) {
            if(linesClearedProperty().get()>=40){
                win=true;

                end = System.currentTimeMillis();
                stop();
                time= end - start;
                System.out.println("Time: "+ time);
                break;


            }

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }if(win==true){
            double seconds = time / 1000.0;
            String timeAsString=String.valueOf(seconds);
            App.instance().loadWinningScreen("Deine Zeit:", timeAsString+" s");


        }

    }


    private void startCounter() {
        start=System.currentTimeMillis();
    }
}
