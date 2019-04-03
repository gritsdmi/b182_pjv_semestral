package start;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MapGenerator implements Constants {

    private GamePanel gp;
    private char[][] actualMap;

    /**
     * или же сделать отделный класс Map?
     */

    public MapGenerator(GamePanel gp) {
        this.gp = gp;

        try {
            actualMap = readMapFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean generateMap() {

        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 250, 250));
        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 350, 250));
        gp.blocks.add(new Block(Constants.WALL_TYPE_BRICK, 350, 200));

        return true;
    }

    /**
     * я бы это переделал
     */
    public void BildBorder() {
        int bx = 0;
        int by = 0;
        while (bx != PANEL_WIDTH - 50) {
            gp.blocks.add(new Block(Color.BLACK, bx, by));
            bx = bx + 50;
        }
        while (by != PANEL_HEIGHT - 50) {
            gp.blocks.add(new Block(Color.BLACK, bx, by));
            by = by + 50;
        }
        while (bx != 0) {
            gp.blocks.add(new Block(Color.BLACK, bx, by));
            bx = bx - 50;
        }
        while (by != 0) {
            gp.blocks.add(new Block(Color.BLACK, bx, by));
            by = by - 50;
        }

    }

    /**
     * Method, in the future, will parse txt file
     * and generate lvl
     * еще сюда можно притулить тестов, соответствует ли файл нужному формату
     */
    private char[][] readMapFromFile() throws FileNotFoundException {

        File map_file = new File(LEVEL_1);
        Scanner parser = new Scanner(map_file);
        String line;
        char[][] levelInChar = new char[16][16];
        int lineCounter = 0;

        while (parser.hasNext()) {
            line = parser.nextLine();
//            System.out.println(line);
            if (lineCounter > 0) {//ignorovat prvni radek

                levelInChar[lineCounter - 1] = line.toCharArray();

                for (int i = 0; i < line.length(); i++) {
                    System.out.print(levelInChar[lineCounter - 1][i]);
                }
                System.out.println();
            }
            lineCounter++;
        }
        parser.close();
        return levelInChar;
    }

    public void buildMap() {
        int xPos = 0;
        int yPos = 0;

        for (char[] line : actualMap) {
            for (char blockType : line) {
                if (blockType != '_')
                    gp.blocks.add(new Block((byte) (Character.getNumericValue(blockType)), xPos, yPos));
                xPos += 50;
            }
            xPos = 0;
            yPos += 50;
        }
    }
}
