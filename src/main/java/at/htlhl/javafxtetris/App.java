package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.graphics.TetrisController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        TetrisController controller = loader.getController();

        TetrisGame game = new TetrisGame(controller);
        game.start();

        Platform.setImplicitExit(true);
        stage.setOnCloseRequest(e -> game.stop());


        stage.setScene(new Scene(loader.load()));
        stage.setFullScreen(true);
        stage.show();
    }

    public void showLosingScreen()
    {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("LosingScreen.fxml"));
        try {
            primaryStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static App instance()
    {
        return instance;
    }

    public static void main(String[] args) {
        launch();
    }

}