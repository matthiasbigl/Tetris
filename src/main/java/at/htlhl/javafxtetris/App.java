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

    private static App instance;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        App.instance = this;
        this.primaryStage = stage;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("TetrisView.fxml"));
        Pane root = loader.load();
        TetrisController controller = loader.getController();

        Scene scene = new Scene(root);

        TetrisGame game = new TetrisGame(controller, scene);
        game.start();

        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(e -> game.stop());
        
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void showLosingScreen()
    {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("LosingScreen.fxml"));
        Platform.runLater(() ->
        {
            try {
                primaryStage.setScene(new Scene(loader.load()));
                primaryStage.centerOnScreen();
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