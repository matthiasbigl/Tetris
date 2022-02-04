package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.TetrisGame;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartScreenController {

    @FXML
    private Button classic;

    @FXML
    private Button fourtyLines;

    @FXML
    private Button blitz;

    public void StartClassic() {
        TetrisGame game = new TetrisGame(controller, scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);

        game.start();

    }

    public void StartBlitz() {
        TetrisGame game = new TetrisGame(controller, scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        BlitzGame blitz = new BlitzGame(game, primaryStage);
        blitz.startBlitzMode();

    }

    public void StartFourty() {
        FourtyLineGame game = new FourtyLineGame(controller,scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);

        game.start();


    }


}
