package at.htlhl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

        stage.setOnCloseRequest(e -> game.stop());
        
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}