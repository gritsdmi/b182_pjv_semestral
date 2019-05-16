package start.Logic;

import start.GameObjects.Block;
import start.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class represents level's map
 */
public class MapGenerator implements Constants {

    private final GamePanel gp;
    private char[][] actualMap;
    private ArrayList<Point> freeSpaces;
    private ArrayList<SpawnPoint> spawnPoints;


    public MapGenerator(GamePanel gp) {
        this.gp = gp;

        freeSpaces = new ArrayList<>();
        spawnPoints = new ArrayList<>();

    }


    /**
     * Method parse txt file
     * and generate lvl
     * еще сюда можно притулить тестов, соответствует ли файл нужному формату
     *
     * @return 2 dimensional array of char, represents actual map
     */
    private char[][] readMapFromFile(String map) throws FileNotFoundException {

        File map_file = new File(map);
        Scanner parser = new Scanner(map_file);
        String line;
        char[][] levelInChar = new char[16][16];//map size
        int lineCounter = 0;

        while (parser.hasNext()) {
            line = parser.nextLine();

            if (lineCounter > 0) {//ignore first line

                levelInChar[lineCounter - 1] = line.toCharArray();

            }
            lineCounter++;
        }
        parser.close();
        return levelInChar;
    }


    /**
     * create Block objects according to actual map
     *
     * @param map path to file
     */
    public void buildMap(String map) {
        freeSpaces.clear();
        spawnPoints.clear();

        int xPos = 0;
        int yPos = 0;
        try {
            actualMap = readMapFromFile(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (char[] line : actualMap) {
            for (char blockType : line) {
                if (blockType == 'x') {
                    spawnPoints.add(new SpawnPoint(this, 1, new Point(xPos, yPos)));
                } else if (blockType != '_') {
                    gp.blocks.add(new Block((Character.getNumericValue(blockType)), xPos, yPos));
                } else {
                    freeSpaces.add(new Point(xPos, yPos));
                }

                xPos += 50;
            }
            xPos = 0;
            yPos += 50;
        }
    }


    public ArrayList<Point> getFreeSpaces() {
        return freeSpaces;
    }

    public ArrayList<SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }

}

