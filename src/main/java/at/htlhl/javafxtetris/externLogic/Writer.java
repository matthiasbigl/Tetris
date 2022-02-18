package at.htlhl.javafxtetris.externLogic;

import java.io.*;
import java.util.ArrayList;

public class Writer {

    public static final String APP_NAME = "ScoreBoard";
    public static final String CONFIG_DIR_PATH =
            System.getProperty("user.home") + "/." + APP_NAME;
    public static final String MODEL_FILE_PATH =
            CONFIG_DIR_PATH + "/scores.txt";

    private ArrayList<String> classicScores;
    private ArrayList<String> fourtyLines;
    private ArrayList<String> blitz;

    FileWriter path;
    BufferedWriter writer;


    public Writer() {
        classicScores = new ArrayList<>();
        fourtyLines = new ArrayList<>();
        blitz = new ArrayList<>();
        classicScores.add("Hallo1");
        fourtyLines.add("Hallo2");
        blitz.add("Hallo3");

    }

    public void writeFile(String cSData, String fLD, String bD){

        System.out.println("File wird geschrieben");
        classicScores.add(cSData);
        fourtyLines.add(fLD);
        blitz.add(bD);

        File configDir = new File(MODEL_FILE_PATH);
        if(!configDir.exists()){
            configDir.mkdir();
        }

        try {
            path = new FileWriter("C:/Users/ThinkPad/ScoreBoard/score.txt");
            writer = new BufferedWriter(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            writer.write(""+classicScores);
            writer.newLine();
            writer.write(""+fourtyLines);
            writer.newLine();
            writer.write(""+ blitz);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(){

        try{
            System.out.println("File wird gelesen");
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/ThinkPad/ScoreBoard/score.txt"));
            String line = reader.readLine();
            String temp = reader.readLine();

            while(line!= null){
                temp += line;
                line = reader.readLine();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getData(String temp){

        ArrayList<Integer> trenn = new ArrayList<>();

        for(int i = 0; i<=temp.length()-1; i++){
            if(temp.charAt(i)=='['){
                trenn.add(i);
            }
        }

        String classicScore = temp.substring(trenn.get(0), trenn.get(1)-1);
        String fourtyLineScore = temp.substring(trenn.get(1), trenn.get(2)-1);
        String blitzScore = temp.substring(trenn.get(2), trenn.get(3)-1);


    }
}
