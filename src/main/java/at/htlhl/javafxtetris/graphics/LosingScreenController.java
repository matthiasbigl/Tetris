package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.App;
import javafx.fxml.FXML;

public class LosingScreenController {
    @FXML
    public void retry(){
        App.instance().loadTetrisGame();
    }
}
