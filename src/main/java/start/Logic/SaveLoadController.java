package start.Logic;

import start.GamePanel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SaveLoadController {

    private GamePanel gp;
    private String filename;
    private ArrayList<ArrayList> savedData;

    public SaveLoadController(GamePanel gp) {
        this.gp = gp;
        this.savedData = new ArrayList<>();
    }

    private String generateNewFilename() {
        Date date = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return df.format(date);
    }

    public void SaveGame() {
        System.out.println("Try to save game");
        savedData.add(gp.getPlayerAsArrayList());
        savedData.add(GamePanel.blocks);
        savedData.add(GamePanel.bullets);
        savedData.add(GamePanel.getEnemySpawns());
        savedData.add(gp.getDrops());//may generate some problems with delays

        String filename = generateNewFilename() + ".dat";

    }

    public void LoadGameFromFile(String filename) {

    }

}
