package at.htlhl.javafxtetris;


import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.grid.Grid;
import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.BlockState;
import at.htlhl.javafxtetris.grid.block.Direction;
import at.htlhl.javafxtetris.grid.block.FallingBlock;
import at.htlhl.javafxtetris.grid.TetrisGrid;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame
{
    // Constants **************************************************************
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    public static final int GRID_Y_OFFSET = 4;
    
    // Fields *****************************************************************
    // Grid and Blocks
    private final TetrisController controller;
    private final TetrisGrid tetrisGrid;
    private FallingBlock currentBlock;
    private Block nextBlock;
    
    // Timer & Tick loop
    private Timer tickTimer;
    private boolean isRunning;
    private boolean isPaused;
    private long totalTickCount; // The total number of ticks that have happened
    private long lastBlockFall;  // The last tick the FallingBlock fell down
    private long lastBlockMove;  // The last tick the FallingBlock was moved
    
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
        
        // User input
        initUserInput(scene);
    
        // Init the grid
        this.nextBlock = Block.randomBlock();
        this.tetrisGrid = new TetrisGrid(GRID_WIDTH, GRID_HEIGHT, GRID_Y_OFFSET);
    
        // Init the controller
        this.controller = controller;
        controller.initTetrisGrid(tetrisGrid);
        controller.initPreviewGrid(nextBlock.getDefaultState().toGrid());
        controller.initStats(this);
    
        generateNewBlock();
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
        // Pseudo code:
/*      if ((can fall) & (should fall this tick))
 *          fall
 *          set lastBlockFall & lastBlockMove
 *
 *      if ((can't fall) & (should place this tick))
 *          place
 */
        
        final boolean canFall = currentBlock.canMove(tetrisGrid, Direction.DOWN);
        
        // TODO: increase tick speed depending on level
        final int period = 30;
        if(canFall && (lastBlockFall + period <= totalTickCount))
        {
            currentBlock.move(Direction.DOWN);
            this.lastBlockFall = this.lastBlockMove = totalTickCount;
        }
    
        // This should always be the same delay
        if(!canFall && (lastBlockMove + 30 <= totalTickCount))
            tryUpdateFallingBlock();
        
        totalTickCount++;
        controller.updateTetrisGrid(tetrisGrid);
        controller.updateFallingBlock(currentBlock);
    }
    
    /*
     * Attempts to place the current Block in the Grid
     * Generates a new Block
     * Deletes all full lines in the grid
     * 
     * Terminates the Game when the new Block can't be placed
     */
    private void tryUpdateFallingBlock()
    {
        this.lastBlockFall = this.lastBlockMove = totalTickCount;
        final FallingBlock fallingBlock = currentBlock;
        fallingBlock.tryPlaceBlockIn(tetrisGrid);
        
        // Generate a new Block
        generateNewBlock();
        
        // Delete all full lines in the Grid
        final int deletedLines = tetrisGrid.deleteFullLines();
        updateStats(deletedLines);
        
        if(!currentBlock.canPlaceBlockIn(tetrisGrid))
        {
            // Lose
            this.stop();
            App.instance().loadLosingScreen();
            return;
        }
    }
    
    /*
     * Generates a new Block that is different from nextBlock
     */
    private void generateNewBlock()
    {
        this.currentBlock = createFallingBlock(nextBlock.getDefaultState());
        this.nextBlock = Block.values()[(nextBlock.ordinal() +
                (int) (Math.random() * (Block.values().length - 1)) + 1)
                % Block.values().length];
        
        // Update the preview Block in the controller
        controller.updatePreview(nextBlock.getDefaultState());
    }
    
    /*
     * Creates a new FallingBlock that is centered in the Grid
     */
    private FallingBlock createFallingBlock(final BlockState state)
    {
        final Grid grid = state.toGrid();
        final int centeredX = ((tetrisGrid.getTotalWidth()) / 2) - ((grid.getTotalWidth() + 1) / 2);
        final int y = Math.max(- tetrisGrid.getYOffset(), - grid.getHighestCellY());
        return state.falling(centeredX, y + 1);
    }
    
    /**
     * Method to add Points, Level and lines based on the amount of lines removed
     *
     * @param amountOfLines The amount of lines cleared
     */
    private void updateStats(int amountOfLines)
    {
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
        // TODO: user input per frame
        scene.setOnKeyPressed(e ->
        {
            final FallingBlock fallingBlock = currentBlock;
            final KeyCode code = e.getCode();
            
            // TODO: rotate
            switch(code)
            {
                case Q:
                    break;
                case W:
                    break;
                case E:
                    break;
                default:
                    break;
            }
            
            // NOTE: Technically allows rotating and moving at the same time
            
            final Direction directionToMove;
            switch(code)
            {
                case A:
                case LEFT:
                    directionToMove = Direction.LEFT;
                    break;
                
                case S:
                case DOWN:
                    directionToMove = Direction.DOWN;
                    break;
                
                case D:
                case RIGHT:
                    directionToMove = Direction.RIGHT;
                    break;
                
                case SPACE:
                    // Move the block down one by one
                    // TODO: Make better.
                    final Grid cellsToPlace = fallingBlock.getBlockState().toGrid();
                    int lowestValidY = 0;
                    
                    for(int y = 0; y < tetrisGrid.getTotalHeight() - fallingBlock.getY(); y++)
                    {
                        if(tetrisGrid.canPlace(cellsToPlace, fallingBlock.getX(), fallingBlock.getY() + y))
                            lowestValidY = y;
                        else
                            break;
                    }
                    
                    fallingBlock.setY(fallingBlock.getY() + lowestValidY);
                    tryUpdateFallingBlock();
                    return;
                default:
                    return;
            }
            
            // If the block can move, set lastBlockMove, so the placement is delayed
            if(fallingBlock.tryMove(tetrisGrid, directionToMove))
                this.lastBlockMove = totalTickCount;
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
