package at.htlhl;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Fall {

    public void fall(TetrisController controller) {
        Cell[][] grid = TetrisGame.grid;

        Color red = new Color(1, 0, 0);
        grid[0][3] = new Cell(red, true);
        grid[0][4] = new Cell(red, true);
        grid[1][5] = new Cell(red, true);
        grid[2][4] = new Cell(red, true);

        controller.updateGridMatrix(grid);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                for (int y = grid.length - 2; y >= 0; y--) {
                    for (int x = 0; x < grid[y].length; x++) {
                        if (!grid[y][x].getColor().equals(Color.TRANSPARENT)) {
                            if (grid[y + 1][x].getColor().equals(Color.TRANSPARENT)) {
                                grid[y][x].setColor(Color.TRANSPARENT);
                                grid[y + 1][x].setColor(red);
                                grid[y + 1][x].setVisible(true);
                                Platform.runLater(() -> controller.updateGridMatrix(grid));
                            }
                        }
                    }
                }

            }
        }, 1000, 250);
    }

}
