package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.*;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame {
    // Constants **************************************************************
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;

    // Fields *****************************************************************
    // Grid and Blocks
    private final TetrisController controller;
    private final TetrisGrid tetrisGrid;
    private Block nextBlock;

    // Timer & Tick loop
    private Timer tickTimer;
    private boolean isRunning;
    private boolean isPaused;

    // Movement
    private long totalTickCount; // The total number of ticks that have happened
    private long lastBlockFall;  // The last tick the FallingBlock was moved

    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller, Scene scene) {
        processUserInput(scene);

        this.controller = controller;

        // Init loop variables
        this.isRunning = false;
        this.isPaused = true;

        // Init the grid
        this.nextBlock = Block.randomBlock();
        this.tetrisGrid = new TetrisGrid(GRID_WIDTH, GRID_HEIGHT, nextBlock);
        generateNextBlock();

        // Init the controller
        controller.initTetrisGrid(tetrisGrid);
        controller.initPreviewGrid(nextBlock);
    }

    // Logic ******************************************************************
    public void start() {
        if (isRunning())
            return;

        this.isRunning = true;
        unpause();

        this.totalTickCount = 1;
        this.tickTimer = new Timer();
        tickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isPaused()) {
                    tick();
                }
            }
        }, 0, 20);
    }

    public void stop() {
        if (!isRunning())
            return;

        this.isRunning = false;
        tickTimer.cancel();
    }

    public void pause() {
        this.isPaused = true;
    }

    public void unpause() {
        this.isPaused = false;
    }

    /**
     * @return Whether the game is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * @return Whether the game is paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Is called x times per second
     */
    private void tick() {
        if (lastBlockFall + 10 <= totalTickCount) {
            this.lastBlockFall = totalTickCount;
            tetrisGrid.tick();

            if (!tetrisGrid.didBlockFall()) {
                // Place the Block in the Grid
                tetrisGrid.getFallingBlock().placeBlock(tetrisGrid);
                // Update the Falling Block in the Grid
                tetrisGrid.setFallingBlock(nextBlock);
                // Generate a new Block
                generateNextBlock();
                // Delete all full lines in the Grid
                tetrisGrid.deleteFullLines();
                // Update the preview Block in the controller
                controller.updatePreviewGrid(nextBlock);
            }
        }

        totalTickCount++;
        controller.updateTetrisGrid(tetrisGrid);
    }

    /*
     * Generates a new Block that is different from nextBlock
     */
    private void generateNextBlock() {
        this.nextBlock = Block.values()[(nextBlock.ordinal() +
                (int) (Math.random() * (Block.values().length - 1)) + 1)
                % Block.values().length];
    }

    private void processUserInput(Scene scene) {
        scene.setOnKeyPressed(e -> {
            FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
            switch (e.getCode().getCode()) {
                //A, arrow_left
                case 65:
                case 37:
                    if (fallingBlock.canMove(tetrisGrid, -1, 0)) {
                        fallingBlock.move(-1, 0);
                    }
                    break;

                // S, arrow_down
                case 83:
                case 40:
                    break;

                // D, arrow_right
                case 68:
                case 39:
                    if (fallingBlock.canMove(tetrisGrid, 1, 0)) {
                        fallingBlock.move(1, 0);
                    }
                    break;

                // space
                case 32:
                    /**
                     * would also work if y starts with gridheight
                     * checkheight calculates the space to the ground - better performance
                     */
                    int checkHeight = tetrisGrid.getHeight() - fallingBlock.getY() - fallingBlock.getBlock().getHeight()+1;
                    for (int y = checkHeight; y > 0; y--) {
                        if (fallingBlock.canMove(tetrisGrid, 0, y)) {
                            fallingBlock.move(0, y);
                            break;
                        }
                    }
                    break;

                // Q
                case 81:
                    break;

                // W
                case 87:
                    break;

                // E
                case 69:
                    break;
                default:
                    break;
            }
        });
    }
}
