package at.htlhl.javafxtetris.externLogic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Scores {

    private StringProperty name = new SimpleStringProperty(this, "name");
    private StringProperty score = new SimpleStringProperty(this, "score");
    private IntegerProperty gameMode = new SimpleIntegerProperty(this, "gameMode");

    /*int gameMode;
    String name;
    String score;*/

    public Scores(){
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getScore() {
        return score.get();
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }

    public int getGameMode() {
        return gameMode.get();
    }

    public IntegerProperty gameModeProperty() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode.set(gameMode);
    }
}
