package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.graphics.StartScreenController;
import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.graphics.WinScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    private static App instance;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        App.instance = this;
        this.primaryStage = stage;
        primaryStage.initStyle(StageStyle.DECORATED);
        
        Platform.setImplicitExit(true);
        
        loadTetrisGame();
        primaryStage.show();
    }

    public void loadTetrisGame() {
        Platform.runLater(() ->
        {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("TitleScreen.fxml"));
            
            Pane root;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            
            StartScreenController controller = loader.getController();
            controller.init(primaryStage);
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
            primaryStage.setOnCloseRequest(e -> System.exit(0));
            primaryStage.setFullScreen(true);
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
                primaryStage.setOnCloseRequest(e -> System.exit(0));
                primaryStage.setFullScreen(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void loadWinningScreen(String titel,String data)
    {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("WinScreens.fxml"));
        Platform.runLater(() ->
        {
            try {
                primaryStage.setScene(new Scene(loader.load()));
                primaryStage.centerOnScreen();
                primaryStage.setOnCloseRequest(e -> System.exit(0));
                primaryStage.setFullScreen(true);

                WinScreenController WinScreen = loader.getController();
                WinScreen.init(titel,data);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static App instance()
    {
        return instance;
    }

    public static void main() {
        launch();
    }

}