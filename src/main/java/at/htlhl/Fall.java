package at.htlhl;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class Fall {

    private Cell[][] grid;

    public void fall(TetrisController controller) {
        Cell[][] grid = TetrisGame.grid;

        Color red = new Color(1, 0, 0);
        grid[0][4] = new Cell(red, true);
        grid[0][5] = new Cell(red, true);
        grid[1][4] = new Cell(red, true);
        grid[2][4] = new Cell(red, true);

        //Color cellColor = Color.randomColor(false);
        //grid[0][0].setColor(cellColor);
        controller.updateGridMatrix(grid);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                for (int y = grid.length - 1; y >= 0; y--) {
                    for (int x = 0; x < grid[y].length; x++) {
                        if (!grid[y][x].getColor().equals(Color.TRANSPARENT)) {
                            grid[y][x].setColor(Color.TRANSPARENT);
                            grid[y + 1][x].setColor(red);
                            Platform.runLater(() -> controller.updateGridMatrix(grid));
                        }
                    }
                }
                for (Cell x[] : grid) {
                    for (Cell cell : x) {
                        if (cell.getColor().equals(Color.TRANSPARENT)) {
                            System.out.print("_");
                        } else {
                            System.out.print("X");
                        }
                    }
                    System.out.println();
                }
                System.out.println();

            }
        }, 1000, 1000);
    }

}
