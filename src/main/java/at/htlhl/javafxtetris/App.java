package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("TetrisView.fxml"));
        Pane root = loader.load();
        TetrisController controller = loader.getController();

        TetrisGame game = new TetrisGame(controller);
        game.start();

        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(e -> game.stop());
        
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}