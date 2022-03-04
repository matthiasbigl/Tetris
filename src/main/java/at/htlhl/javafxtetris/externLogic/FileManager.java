package at.htlhl.javafxtetris.externLogic;

import at.htlhl.javafxtetris.App;
import at.htlhl.javafxtetris.TetrisGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {
    ArrayList<Scores> scores;

    public FileManager() {
        App.scores = new ArrayList<Scores>();
    }

    public void writeFile() {

        System.out.println("File wird geschrieben");

        File scoreFile = new File(App.MODEL_FILE_PATH);

        if(!scoreFile.exists()){
            try {
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            App.JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValue(scoreFile, App.scores);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<Scores> create(){
        ArrayList<Scores> list = new ArrayList<Scores>();
        Scores score1 = new Scores(), score2 = new Scores(), score3 = new Scores();

        score1.setGameMode(1);
        score1.setScore("6000");
        score1.setName("Nico");

        score2.setGameMode(2);
        score2.setScore("5000");
        score2.setName("Markus");

        score3.setGameMode(2);
        score3.setScore("20:59");
        score3.setName("Ben");

        list.add(score1);
        list.add(score2);
        list.add(score3);

        return list;
    }

    public void readFile(){

        File scoreFile = new File(App.MODEL_FILE_PATH);

        if(scoreFile.exists())
            try {
                Scores[] arrayCars  = App.JSON_MAPPER.readValue(scoreFile, Scores[].class);
                App.scores = new ArrayList<>();
                App.scores.addAll(Arrays.asList(arrayCars));
                for(Scores score:scores){
                    System.out.println(score.getGameMode());
                    System.out.println(score.getName());
                    System.out.println(score.getScore());
                }
            } catch (IOException e) {
                e.getMessage();
                System.err.println("Couldnt load Highscores");
            }
    }
}
