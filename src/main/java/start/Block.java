package start;

import java.awt.*;

public class Block {
    //Fielsds
    private int xPosition;
    private int yPosition;
    private int width = 50;
    private int height = 50;
    private Color color;
    private int health;
    private boolean isAlive;

    //Constructor
    public Block(Color c, int x, int y) {
        this.color = c;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
    }


    //Methods

    public void update() {
//        controlXP();

    }

    private void controlXP() {
        if (isAlive) {
            if (health < 1) {
                this.isAlive = false;
            }
        }
    }

    /**
     * в зависимости от типа пули будет отнимать разной количество хр
     */
    public void hit(Bullet bul) {
        this.health--;
        controlXP();
        System.out.println("hit JP = " + this.health);
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(color);
            g.fillRect(xPosition, yPosition, width, height);
        }
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
