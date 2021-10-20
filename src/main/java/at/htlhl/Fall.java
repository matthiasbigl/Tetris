package at.htlhl;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Fall {

    private Cell[][] grid;

    public void fall(TetrisController controller) {
        Cell[][] grid = TetrisGame.grid;
        Color cellColor = Color.randomColor(false);
        grid[0][0].setColor(cellColor);
        controller.updateGridMatrix(grid);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (int y = 0; y < grid.length; y++) {
                    if (!grid[y][0].getColor().equals(Color.TRANSPARENT)) {
                        //System.out.println(1);
                        grid[y][0].setColor(Color.TRANSPARENT);
                        grid[y+1][0].setColor(cellColor);
                        for (Cell x[] : grid) {
                            for (Cell cell : x) {
                                if (cell.getColor().equals(Color.TRANSPARENT)) {
                                    System.out.print(0);
                                } else {
                                    System.out.print("x");
                                }
                            }
                            System.out.println();
                        }
                        Platform.runLater(() -> controller.updateGridMatrix(grid));

                        y = grid.length;
                    }
                }
                System.out.println();
            }
        }, 1000, 1000);
    }

}
