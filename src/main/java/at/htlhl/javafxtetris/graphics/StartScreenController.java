package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.App;
import at.htlhl.javafxtetris.TetrisGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

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

    private void init(){
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TetrisView.fxml"));

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
