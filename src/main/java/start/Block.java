package start;

import java.awt.*;

public class Block {
    //Fielsds
    private int x;
    private int y;
    private Color color;
    private int health;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Constructor
    public Block(Color c, int x, int y) {
        this.color = c;
        this.x = x;
        this.y = y;

    }


    //Methods

    public void update() {
//

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, 50, 50);
    }
}
