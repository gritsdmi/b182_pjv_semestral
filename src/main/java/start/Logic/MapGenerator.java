package start.Logic;

import start.GameObjects.Block;
import start.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapGenerator implements Constants {

    private GamePanel gp;
    private char[][] actualMap;
    private ArrayList<Point> freeSpaces = new ArrayList<Point>();
    private String map = LEVEL_1;
//    private String map = LEVEL_2;

    public ArrayList<Point> getFreeSpaces() {
        return freeSpaces;
    }

    /**
     * или же сделать отделный класс Map?
     */

    public MapGenerator(GamePanel gp) {
        this.gp = gp;


    }

    public boolean generateMap() {

//        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 250, 250));
//        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 350, 250));
//        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 350, 200));
//        gp.blocks.add(new Block(Constants.WALL_TYPE_TEST, 500, 400));




        return true;
    }


    /**
     * Method, in the future, will parse txt file
     * and generate lvl
     * еще сюда можно притулить тестов, соответствует ли файл нужному формату
     * @return array of char, represents actual map
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

                for (int i = 0; i < line.length(); i++) {
//                    System.out.print(levelInChar[lineCounter - 1][i]);
                }
//                System.out.println();
            }
            lineCounter++;
        }
        parser.close();
        return levelInChar;
    }


    /**
     * create Block objects according to actual map
     */
    public void buildMap(String map) {
        freeSpaces.clear();
        int xPos = 0;
        int yPos = 0;
        try {
            actualMap = readMapFromFile(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (char[] line : actualMap) {
            for (char blockType : line) {
                if (blockType != '_') {
                    gp.blocks.add(new Block((byte) (Character.getNumericValue(blockType)), xPos, yPos));
                } else {
                    freeSpaces.add(new Point(xPos, yPos));
                }

                xPos += 50;
            }
            xPos = 0;
            yPos += 50;
        }
    }
}
