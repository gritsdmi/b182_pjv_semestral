package start.Logic;

import org.apache.commons.io.FileUtils;
import start.GamePanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveLoadController implements Constants, Serializable {

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

    /**
     * Method try to save actual game. Create directory (if it not exits)
     *
     * @param str represents name of created file
     * @return boolean true if saved complete successfully, otherwise false
     */
    boolean saveGame(String str) {
        System.out.println("Try to save game");

        savedData.add(gp.getPlayerAsArrayList());
        savedData.add(GamePanel.blocks);
        savedData.add(gp.getBaseAsArrayList());
        savedData.add(GamePanel.bullets);
        savedData.add(GamePanel.getEnemySpawns());
        savedData.add(gp.getFreeSpacesMap());
        savedData.add(gp.getDrops());
        savedData.add(gp.getWaveAsArrayList());

//        filename = "Saved game " + generateNewFilename() + ".dat";
        filename = str + ".dat";

        Path path = Paths.get(pathToSavedGame + GamePanel.name + '/');

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory
                Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "Error creating new file", e);
                return false;
            }
        }

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(pathToSavedGame + GamePanel.name + "/" + filename));
            out.writeObject(savedData);

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "Error writing saved game to file", ex);

            deleteFile(pathToSavedGame + GamePanel.name + "/" + filename);

            return false;
        }

        return true;

    }

    /**
     * Method try to deserialize saved game from file
     *
     * @param file represents filename of saved game
     */
    void loadGameFromFile(String file) {
        savedData.clear();
        file = pathToSavedGame + GamePanel.name + "/" + file;

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
    }

    /**
     * Method parses directory with saved games
     * Result depend on actulal username
     * File with saved game should end on '.dat'
     *
     * @return String[] array with names of saved games
     */
    String[] getExistingSavedGames() {

        File dir = new File(pathToSavedGame + GamePanel.name + "/");
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

    /**
     * Method tries parse file, witch contains player names.
     *
     * @return String[] array with names of saved players
     */
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
        int count = 0;
        while (parser.hasNext()) {
            line = parser.nextLine();
            if (line.equals(""))
                temp.add(null);//fix
            else temp.add(line);
            count++;
            if (count >= 3) break;
        }

        return temp.toArray(new String[3]);

    }

    /**
     * Method try to delete file with saved game
     *
     * @param file represents name of deleted file
     * @return boolean true if deleted complete successfully
     */
    boolean deleteFile(String file) {

        Path path = Paths.get(pathToSavedGame + GamePanel.name + "/" + file);

        try {
            Files.delete(path);
        } catch (IOException e) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "Deleting error", e);
        }

        return true;
    }

    /**
     * Method try write new player to saved players file
     *
     * @param name represents name of new player
     * @param pos  represents position(row) in this file
     */
    void writeNewPlayer(String name, int pos) {
        File file = new File(pathToSavedPlayers);
        String[] oldData = parseSavedPlayers();

        for (int i = 0; i < 3; i++) {
            if (oldData[i] == null && i + 1 == pos) {
                oldData[i] = name;
                break;
            }
        }

        try {
            FileWriter fW = new FileWriter(file);

            for (int i = 0; i < 3; i++) {
                if (oldData[i] != null) {
                    fW.write(oldData[i]);
                }
                fW.write('\n');
            }
            fW.flush();

            fW.close();
        } catch (IOException e) {
            Logger.getLogger(SaveLoadController.class.getName()).log(Level.SEVERE, "File write error", e);
        }

    }

    /**
     * Method delete player from file
     *
     * @param placing mean position(row) in this file
     */
    void deletePlayer(int placing) {
        int index = placing - 1;

        String[] playersInFile = parseSavedPlayers();
        String playersName = playersInFile[index];
        if (playersName == null) return;

        if (playersInFile[index].equals(GamePanel.name)) {
            GamePanel.name = "";
            System.out.println("equals names");
        } else {
            System.out.println("not equals names");
        }

        try {
            FileUtils.deleteDirectory(new File(pathToSavedGame + playersName + "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        delRowInFile(playersInFile, index);
    }

    private void delRowInFile(String[] playersInFile, int index) {
        File file = new File(pathToSavedPlayers);
        try {
            FileWriter fW = new FileWriter(file);

            for (int i = 0; i < 3; i++) {
                if (i == index) {
                    fW.write("");
                } else {
                    if (playersInFile[i] == null) {
                        fW.write("");
                    } else {
                        fW.write(playersInFile[i]);
                    }
                }
                fW.write('\n');
            }
            fW.flush();

            fW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ArrayList<ArrayList> getLoadedData() {
        return savedData;
    }

}
