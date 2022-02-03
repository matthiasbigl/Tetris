package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.grid.block.Block;
import at.htlhl.javafxtetris.grid.block.BlockState;
import at.htlhl.javafxtetris.grid.block.FallingBlock;

public class SwitchBlock {

    private boolean switched = false;
    private boolean switchedNow = false;
    private boolean holdIsFull = false;

    BlockState holdBlockState;
    FallingBlock holdBlock;

    public SwitchBlock() {
    }

    public boolean isSwitched() {
        return switched;
    }

    public void setSwitched(boolean switched) {
        this.switched = switched;
    }

    public boolean isHoldIsFull() {
        return holdIsFull;
    }

    public void setHoldIsFull(boolean holdIsFull) {
        this.holdIsFull = holdIsFull;
    }

    public BlockState getHoldBlockState() {
        return holdBlockState;
    }

    public void setHoldBlockState(BlockState holdBlockState) {
        this.holdBlockState = holdBlockState;
    }

    public FallingBlock getHoldBlock() {
        return holdBlock;
    }

    public void setHoldBlock(FallingBlock holdBlock) {
        this.holdBlock = holdBlock;
    }

    public boolean isSwitchedNow() {
        return switchedNow;
    }

    public void setSwitchedNow(boolean switchedNow) {
        this.switchedNow = switchedNow;
    }
}
