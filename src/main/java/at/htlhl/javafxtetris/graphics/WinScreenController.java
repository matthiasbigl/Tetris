package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.externLogic.*;
import at.htlhl.javafxtetris.externLogic.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class WinScreenController {

    @FXML
    private Text titel;

    @FXML
    private Text data;
    @FXML
    private Button StartScreen;
    FileManager manager = new FileManager();

    @FXML
    public void init(String titel,String data){

        this.titel.setText(titel);
        this.data.setText(data);

    }

    @FXML
    public void start(){
        manager.writeFile();
        //App.instance().loadTetrisGame();
    }


}
