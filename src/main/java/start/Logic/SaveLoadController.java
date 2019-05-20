package start.Logic;

import start.GamePanel;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveLoadController implements Constants {

    private GamePanel gp;
    private String filename;
    private ArrayList<ArrayList> savedData;

    public SaveLoadController(GamePanel gp) {
        this.gp = gp;
        this.savedData = new ArrayList<>();
    }

    private String generateNewFilename() {
        Date date = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }

    public void savePlayer(String nickName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(pathToSavedPlayers));
            out.write(nickName + "\n");
        } catch (IOException e) {

        }

    }
    public boolean saveGame() {
        System.out.println("Try to save game");
        savedData.add(gp.getPlayerAsArrayList());//worked
        savedData.add(GamePanel.blocks);
        savedData.add(gp.getBaseAsArrayList());
        savedData.add(GamePanel.bullets);
        savedData.add(GamePanel.getEnemySpawns());
        savedData.add(gp.getFreeSpacesMap());
        savedData.add(gp.getDrops());//may generate some problems with delays
        savedData.add(gp.getWaveAsArrayList());

        filename = "Saved game " + generateNewFilename() + ".dat";


        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathToSavedGame + filename));
            out.writeObject(savedData);

//            System.out.println("Saving player " + GamePanel.player.getPosition() +" "+ GamePanel.player.getHealth());
//            out.writeObject(GamePanel.player.prepareDataToSerialize());//this works

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "Error writing saved game to file", ex);

            //del file
            try {
                Files.deleteIfExists(Paths.get("src/main/resources/SavedGames/" + filename));
            } catch (NoSuchFileException e) {
                System.out.println("No such file/directory exists");
            } catch (DirectoryNotEmptyException e) {
                System.out.println("Directory is not empty.");
            } catch (IOException e) {
                System.out.println("Invalid permissions.");
            }

            System.out.println("Deletion successful.");

            return false;
        }

        return true;

    }

    public void loadGameFromFile(String file) {
        savedData.clear();
        System.out.println("trying to deserialize");
        file = pathToSavedGame + file;

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            try {
                savedData = (ArrayList<ArrayList>) ois.readObject();

            } catch (ClassNotFoundException e) {
                Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "Class not found", e);
            }
        } catch (IOException e) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "File not found", e);
        } catch (NullPointerException e) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "File null pointer exception", e);

        }
//        System.out.println(savedData);
    }

    public ArrayList<ArrayList> getLoadedData() {
        return savedData;
    }

    public String[] getExistingSavedGames() {

        File dir = new File(pathToSavedGame);
        ArrayList<String> ret = new ArrayList<>();

        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".dat");
            }
        });

        if (files != null) {
            for (File file : files) {
                ret.add(file.getName());
            }
        }
        Collections.sort(ret);

        return ret.toArray(new String[ret.size()]);

    }

    public String[] parseSavedPlayers() {

        File file = new File(pathToSavedPlayers);

        Scanner parser = null;
        try {
            parser = new Scanner(file);
        } catch (FileNotFoundException e) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "File not found", e);

        }

        String line;
        ArrayList<String> temp = new ArrayList<>();

        assert parser != null;
        while (parser.hasNext()) {
            line = parser.nextLine();
            temp.add(line);
        }
        System.out.println(temp);
        System.out.println(temp.size());


        return temp.toArray(new String[temp.size()]);

    }

}
