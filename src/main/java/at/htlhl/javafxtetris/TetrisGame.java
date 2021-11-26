package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.Block;
import at.htlhl.javafxtetris.grid.FallingBlock;
import at.htlhl.javafxtetris.grid.TetrisGrid;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame
{
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

    private boolean hasPlayerLost;

    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller)
    {
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
    public void start()
    {
        if(isRunning())
            return;

        this.isRunning = true;
        this.hasPlayerLost = false;
        unpause();

        this.totalTickCount = 1;
        this.tickTimer = new Timer();
        tickTimer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                if(!isPaused())
                {
                    tick();
                }
            }
        }, 0, 20);
    }

    public void stop()
    {
        if(!isRunning())
            return;

        this.isRunning = false;
        tickTimer.cancel();
    }

    public void pause()
    {
        this.isPaused = true;
    }

    public void unpause()
    {
        this.isPaused = false;
    }

    /**
     * @return Whether the game is running
     */
    public boolean isRunning()
    {
        return isRunning;
    }

    /**
     * @return Whether the game is paused
     */
    public boolean isPaused()
    {
        return isPaused;
    }

    /**
     * Is called x times per second
     */
    private void tick()
    {
        processUserInput();

        if(lastBlockFall + 10 <= totalTickCount)
        {
            this.lastBlockFall = totalTickCount;
            tetrisGrid.tick();

            if(!tetrisGrid.didBlockFall())
            {
                // Place the Block in the Grid
                FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
                if(fallingBlock.canPlace(tetrisGrid))
                {
                    fallingBlock.placeBlock(tetrisGrid);
                }
                else
                {
                    // Lost
                    this.hasPlayerLost = true;
                }

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
        // You can say here "YOU LOST"
        totalTickCount++;
        controller.updateTetrisGrid(tetrisGrid);
    }

    /*
     * Generates a new Block that is different from nextBlock
     */
    private void generateNextBlock()
    {
        this.nextBlock = Block.values()[(nextBlock.ordinal() +
                (int) (Math.random() * (Block.values().length - 1)) + 1)
                % Block.values().length];
    }

    private void processUserInput()
    {
        // * move the block
        // * rotate the block
        // according to the user input
    }



}
