package at.htlhl.javafxtetris.externLogic;

public class Scores {

    int gameMode;
    String name;
    String score;

    public Scores(int gameMode, String name, String score){
        this.gameMode = gameMode;
        this.name = name;
        this.score = score;
    }

    public int getGameMode() {
        return gameMode;
    }

    public String getName() {
        return name;
    }

    public String getScore() {
        return score;
    }
}
