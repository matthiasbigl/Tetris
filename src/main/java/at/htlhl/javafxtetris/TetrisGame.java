package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.Grid;
import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.BlockState;
import at.htlhl.javafxtetris.grid.block.Direction;
import at.htlhl.javafxtetris.grid.block.FallingBlock;
import at.htlhl.javafxtetris.grid.TetrisGrid;
import javafx.scene.Scene;

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
    private final TetrisGrid tetrisGrid; // TODO: Replace with normal grid and add 'visibleGrid'
    private Block nextBlock;

    // Timer & Tick loop
    private Timer tickTimer;
    private boolean isRunning;
    private boolean isPaused;

    // Movement
    private long totalTickCount; // The total number of ticks that have happened
    private long lastBlockFall;  // The last tick the FallingBlock was moved

    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller, Scene scene)
    {
        processUserInput(scene);

        this.controller = controller;

        // Init loop variables
        this.isRunning = false;
        this.isPaused = true;
    
        // Init the grid
        this.nextBlock = Block.randomBlock();
        this.tetrisGrid = new TetrisGrid(GRID_WIDTH, GRID_HEIGHT, nextBlock);
        
        // Init the controller
        controller.initTetrisGrid(tetrisGrid);
        controller.initPreviewGrid(nextBlock.getDefaultState().toGrid());
    
        generateNextBlock();
    }

    // Logic ******************************************************************
    public void start()
    {
        if(isRunning())
            return;
    
        this.isRunning = true;
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
        pause();
        
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
        if(lastBlockFall + 10 <= totalTickCount)
        {
            this.lastBlockFall = totalTickCount;
    
            if(!tetrisGrid.tryMoveFallingBlock())
            {
                placeCurrentBlock();
            }
        }

        totalTickCount++;
        controller.updateTetrisGrid(tetrisGrid);
    }
    
    private void placeCurrentBlock()
    {
        this.lastBlockFall = totalTickCount;
        final FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
        if(fallingBlock.canPlaceBlockIn(tetrisGrid))
        {
            // If the Block can be placed, place it in the Grid
            fallingBlock.placeBlockIn(tetrisGrid);
        }
        else
        {
            // Otherwise the player has lost
            this.stop();
            App.instance().showLosingScreen();
            return;
        }
    
        // Update the Falling Block in the Grid
        tetrisGrid.setFallingBlock(nextBlock);
        // Generate a new Block
        generateNextBlock();
        // Delete all full lines in the Grid
        tetrisGrid.deleteFullLines();
    }

    /*
     * Generates a new Block that is different from nextBlock
     */
    private void generateNextBlock()
    {
        this.nextBlock = Block.values()[(nextBlock.ordinal() +
                (int) (Math.random() * (Block.values().length - 1)) + 1)
                % Block.values().length];
        // Update the preview Block in the controller
        controller.updatePreviewGrid(nextBlock.getDefaultState().toGrid());
    }

    private void processUserInput(Scene scene)
    {
        scene.setOnKeyPressed(e -> {
            FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
            switch (e.getCode().getCode()) {
                //A, arrow_left
                case 65:
                case 37:
                    fallingBlock.tryMove(tetrisGrid, Direction.LEFT);
                    break;

                // S, arrow_down
                case 83:
                case 40:
                    fallingBlock.tryMove(tetrisGrid, Direction.DOWN);
                    break;

                // D, arrow_right
                case 68:
                case 39:
                    fallingBlock.tryMove(tetrisGrid, Direction.RIGHT);
                    break;

                // space
                case 32:
                    // Move the block down one by one
                    // TODO: Make better.
                    final Grid cellsToPlace = fallingBlock.getBlockState().toGrid();
                    int lowestValidY = 0;
                    
                    for (int y = 0; y < tetrisGrid.getHeight() - fallingBlock.getY(); y++)
                    {
                        if (tetrisGrid.canPlace(cellsToPlace, fallingBlock.getX(), fallingBlock.getY() + y))
                            lowestValidY = y;
                        else
                            break;
                    }
    
                    fallingBlock.setY(fallingBlock.getY() + lowestValidY);
                    placeCurrentBlock();
                    
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
