package at.htlhl.javafxtetris.externLogic;

import at.htlhl.javafxtetris.TetrisGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.security.auth.Subject;
import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class Writer {

    public static final String APP_NAME = "ScoreData";
    public static final String CONFIG_DIR_PATH =
            System.getProperty("src/main/java/resources") + "/." + APP_NAME;
    public static final String MODEL_FILE_PATH =
            CONFIG_DIR_PATH + "/scores.json";

    public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private ArrayList<Scores> scores;

    File scoreFile;

    public Writer() {
        scores = new ArrayList<>();
        writeScores();

    }

    public void writeScores(){
        scores.add(new Scores(1, "Nico", "4302"));
        scores.add(new Scores(1, "Paul", "4302"));
        scores.add(new Scores(2, "Bigl", "4302"));
        scores.add(new Scores(2, "Mario", "4302"));
        scores.add(new Scores(3, "Yusuf", "10:02"));
        scores.add(new Scores(3, "Alex", "5:46"));
    }

    public void writeFile(String cSData, String fLD, String bD){

        System.out.println("File wird geschrieben");


        scoreFile = new File(MODEL_FILE_PATH);
        if(!scoreFile.exists()){
            scoreFile.mkdir();
        }

        try {
            JSON_MAPPER.writerWithDefaultPrettyPrinter().writeValue(scoreFile, scores);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void readFile(){

        if(scoreFile.exists())
            try {
                scores = JSON_MAPPER.readValue(scoreFile, ArrayList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
