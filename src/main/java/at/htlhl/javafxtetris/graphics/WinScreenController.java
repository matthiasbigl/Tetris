package at.htlhl.javafxtetris.graphics;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WinScreenController {

    @FXML
    private Text titel;

    @FXML
    private Text data;

    public void init(String titel,String data){

        this.titel.setText(titel);
        this.data.setText(data);

    }

}
