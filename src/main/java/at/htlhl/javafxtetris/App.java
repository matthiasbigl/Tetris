package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.gameModes.BlitzGame;
import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static App instance;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        App.instance = this;
        this.primaryStage = stage;
        
        Platform.setImplicitExit(true);
        
        loadTetrisGame();
        primaryStage.show();
    }

    public void loadTetrisGame() {
        Platform.runLater(() ->
        {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("TetrisView.fxml"));
            
            Pane root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            TetrisController controller = loader.getController();
            Scene scene = new Scene(root);
            
            TetrisGame game = new TetrisGame(controller, scene);
            
            primaryStage.setOnCloseRequest(e -> game.stop());
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setFullScreen(true);

            BlitzGame blitzGame = new BlitzGame(game, primaryStage);
            
            game.start();
        });
    }

    public void loadLosingScreen()
    {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("LosingScreen.fxml"));
        Platform.runLater(() ->
        {
            try {
                primaryStage.setScene(new Scene(loader.load()));
                primaryStage.centerOnScreen();
                primaryStage.setFullScreen(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static App instance()
    {
        return instance;
    }

    public static void main(String[] args) {
        launch();
    }

}