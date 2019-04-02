package start;

import java.awt.*;

public class MapGenerator implements Constants {

    private GamePanel gp;

    public MapGenerator(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Method, in the future, will parse txt file
     * and generate lvl
     */
    public boolean generateMap() {

        gp.blocks.add(new Block(Color.RED, 250, 250));
        gp.blocks.add(new Block(Color.RED, 350, 250));
        gp.blocks.add(new Block(Color.RED, 350, 200));

        return true;
    }

    public void BildBorder() {
        int bx = 0;
        int by = 0;
        while (bx != gp.WIDTH - 50) {
            gp.blocks.add(new Block(Color.BLACK, bx, by));
            bx = bx + 50;
        }
        while (by != gp.HEIGHT - 50) {
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

}
