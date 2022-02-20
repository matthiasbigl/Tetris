package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.App;
import at.htlhl.javafxtetris.TetrisGame;
import at.htlhl.javafxtetris.gameModes.BlitzGame;
import at.htlhl.javafxtetris.gameModes.FourtyLineGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController {

    @FXML
    private Button classic;

    @FXML
    private Button fourtyLines;

    @FXML
    private Button blitz;

    private TetrisController controller;
    private Scene scene;
    Stage primaryStage;

    public void init(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TetrisView.fxml"));
        this.primaryStage=primaryStage;

        Pane root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        controller = loader.getController();
         scene = new Scene(root);
    }


    public void StartClassic() {
        TetrisGame game = new TetrisGame(controller, scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        game.start();

    }

    public void StartBlitz() {
        BlitzGame game = new BlitzGame(controller,scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        game.start();

    }

    public void StartFourty() {
        FourtyLineGame game = new FourtyLineGame(controller,scene);
        primaryStage.setOnCloseRequest(e -> game.stop());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        game.start();


    }


}
