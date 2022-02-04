package at.htlhl.javafxtetris;

import at.htlhl.javafxtetris.gameModes.BlitzGame;
import at.htlhl.javafxtetris.graphics.TetrisController;
import at.htlhl.javafxtetris.graphics.WinScreenController;
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
    private String gamemode="Blitz";

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

            if(gamemode =="standart"){
            TetrisGame game = new TetrisGame(controller, scene);
                primaryStage.setOnCloseRequest(e -> game.stop());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                primaryStage.setFullScreen(true);

                game.start();}
            else if(gamemode =="40lines"){
                FourtyLineGame game = new FourtyLineGame(controller,scene);
                primaryStage.setOnCloseRequest(e -> game.stop());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                primaryStage.setFullScreen(true);

                game.start();

            }
            else if(gamemode=="Blitz"){
                TetrisGame game = new TetrisGame(controller, scene);
                primaryStage.setOnCloseRequest(e -> game.stop());
                primaryStage.setScene(scene);
                primaryStage.centerOnScreen();
                primaryStage.setFullScreen(true);
                BlitzGame blitz = new BlitzGame(game, primaryStage);
                blitz.startBlitzMode();

            }
            

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
    public void loadWinningScreen(String titel,String data)
    {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("WinScreens.fxml"));
        Platform.runLater(() ->
        {
            try {
                primaryStage.setScene(new Scene(loader.load()));
                primaryStage.centerOnScreen();
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

    public static void main(String[] args) {
        launch();
    }

}