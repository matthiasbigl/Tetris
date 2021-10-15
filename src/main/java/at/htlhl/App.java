package at.htlhl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("test.fxml"));
        loader.load();
        TetrisController controller = loader.getController();
        TetrisGame game = new TetrisGame(controller);
    }

    public static void main(String[] args) {
        launch();
    }

}