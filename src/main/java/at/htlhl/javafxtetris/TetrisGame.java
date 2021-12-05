package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.Grid;
import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.Direction;
import at.htlhl.javafxtetris.grid.block.FallingBlock;
import at.htlhl.javafxtetris.grid.TetrisGrid;
import javafx.beans.property.SimpleIntegerProperty;
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
    private final TetrisGrid tetrisGrid;
    private Block nextBlock;
    
    // Timer & Tick loop
    private Timer tickTimer;
    private boolean isRunning;
    private boolean isPaused;
    private long totalTickCount; // The total number of ticks that have happened
    private long lastBlockFall;  // The last tick the FallingBlock was moved
    
    // Stats
    private SimpleIntegerProperty levelProp;        // The level the player is in (10 lines = 1 level)
    private SimpleIntegerProperty linesClearedProp; // The total number of lines the player has cleared
    private SimpleIntegerProperty scoreProp;
    
    // Constructors ***********************************************************
    public TetrisGame(TetrisController controller, Scene scene)
    {
        // Init loop variables
        this.isRunning = false;
        this.isPaused = true;
        
        // Init stats
        this.levelProp = new SimpleIntegerProperty(1);
        this.linesClearedProp = new SimpleIntegerProperty();
        this.scoreProp = new SimpleIntegerProperty();
        
        // Init the grid
        this.nextBlock = Block.randomBlock();
        this.tetrisGrid = new TetrisGrid(GRID_WIDTH, GRID_HEIGHT, nextBlock);
        
        // User input
        initUserInput(scene);
        
        // Init the controller
        this.controller = controller;
        controller.initTetrisGrid(tetrisGrid);
        controller.initPreviewGrid(nextBlock.getDefaultState().toGrid());
        controller.initStats(this);
        
        generateNextBlock();
    }
    
    // Game loop **************************************************************
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
        }, 1000, 10);
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
    
    // Tick *******************************************************************
    
    /**
     * Is called x times per second
     */
    private void tick()
    {
        // TODO: increase tick speed depending on level
        if(lastBlockFall + 30 <= totalTickCount)
        {
            this.lastBlockFall = totalTickCount;
            
            if(!tetrisGrid.getFallingBlock().tryMove(tetrisGrid, Direction.DOWN))
            {
                placeCurrentBlock();
            }
        }
        
        totalTickCount++;
        controller.updateTetrisGrid(tetrisGrid);
        controller.updateFallingBlock(tetrisGrid.getFallingBlock());
    }
    
    private void placeCurrentBlock()
    {
        this.lastBlockFall = totalTickCount;
        final FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
        if(!fallingBlock.tryPlaceBlockIn(tetrisGrid))
        {
            this.stop();
            App.instance().showLosingScreen();
            return;
        }
        
        // Update the Falling Block in the Grid
        tetrisGrid.setFallingBlock(nextBlock);
        // Generate a new Block
        generateNextBlock();
        // Delete all full lines in the Grid
        final int deletedLines = tetrisGrid.deleteFullLines();
        updateStats(deletedLines);
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
        controller.updatePreview(nextBlock.getDefaultState());
    }
    
    /**
     * Method to add Points, Level and lines based on the amount of lines removed
     *
     * @param amountOfLines The amount of lines cleared
     */
    private void updateStats(int amountOfLines)
    {
        // TODO: set score and lines to 0 if lost
        
        // Add amount of lines deleted to lines
        // .add() creates a new property and DOES NOT change the value
        linesClearedProp.set(linesClearedProp.get() + amountOfLines);
        
        // add points to score depending on level
        // using the scoring system from https://tetris.fandom.com/wiki/Scoring
        int baseScore;
        switch(amountOfLines)
        {
            case 1:
                baseScore = 40;
                break;
            case 2:
                baseScore = 100;
                break;
            case 3:
                baseScore = 300;
                break;
            case 4:
                baseScore = 1200;
                break;
            default:
                baseScore = 0;
                break;
        }
        
        scoreProp.set(scoreProp.get() + (baseScore * levelProp.get()));
        
        // for every 10 lines removed increase the level by one
        levelProp.set(linesClearedProp.get() / 10 + 1);
    }
    
    private void initUserInput(Scene scene)
    {
        scene.setOnKeyPressed(e ->
        {
            FallingBlock fallingBlock = tetrisGrid.getFallingBlock();
            switch(e.getCode().getCode())
            {
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
                    
                    for(int y = 0; y < tetrisGrid.getHeight() - fallingBlock.getY(); y++)
                    {
                        if(tetrisGrid.canPlace(cellsToPlace, fallingBlock.getX(), fallingBlock.getY() + y))
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
    
    // Properties *************************************************************
    public SimpleIntegerProperty scoreProperty()
    {
        return scoreProp;
    }
    
    public SimpleIntegerProperty levelProperty()
    {
        return levelProp;
    }
    
    public SimpleIntegerProperty linesClearedProperty()
    {
        return linesClearedProp;
    }
}
