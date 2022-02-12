package at.htlhl.javafxtetris.graphics;

import at.htlhl.javafxtetris.App;
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


    public void init(String titel,String data){

        this.titel.setText(titel);
        this.data.setText(data);

    }
    public void start(){
        App.instance().loadTetrisGame();
    }


}
