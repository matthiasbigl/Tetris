package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.Block;
import at.htlhl.javafxtetris.grid.FallingBlock;
import at.htlhl.javafxtetris.grid.Grid;
import at.htlhl.javafxtetris.grid.TetrisGrid;
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
            switch (e.getCode().getChar()) {
                case "A":
                    //System.out.println("A pressed");

                    if (fallingBlock.getX() >= 0) {
                        fallingBlock.move(-1, 0);
                    }
                    break;
                case "S":
                    System.out.println("S pressed");
                    break;
                case "D":
                    //System.out.println("D pressed");

                    if (fallingBlock.getX() + fallingBlock.getBlock().getTrueWith() < 10) {
                        fallingBlock.move(1, 0);
                    }
                    break;
                case " ":
                    System.out.println("SPACE pressed");
                    break;
                case "Q":
                    System.out.println("Q pressed");
                    break;
                case "W":
                    System.out.println("W pressed");
                    break;
                case "E":
                    System.out.println("E pressed");
                    break;
                default:
                    break;
            }
        });
    }
}
