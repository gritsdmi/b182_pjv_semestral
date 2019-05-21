package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Base implements Constants, Serializable {
    private int Health;
    private int x;
    private int y;
    private boolean Alive;
    private Block bl1;
    private Block bl2;
    private Block bl3;
    private Block bl4;
    private transient BufferedImage baseimage;


    public Base(Point loc) {
        x = loc.x;
        y = loc.y;
        Health = 10;
        bl1 = new Block(WALL_TYPE_INVIS, x, y);
        bl2 = new Block(WALL_TYPE_INVIS, x + 50, y);
        bl3 = new Block(WALL_TYPE_INVIS, x, y + 50);
        bl4 = new Block(WALL_TYPE_INVIS, x + 50, y + 50);
        GamePanel.blocks.add(bl1);
        GamePanel.blocks.add(bl2);
        GamePanel.blocks.add(bl3);
        GamePanel.blocks.add(bl4);
        Alive = true;
        try {
            baseimage = ImageIO.read(new File("src/main/resources/Entity/Base.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, BASE_SIZE_WIDTH, BASE_SIZE_HEIGHT);
    }

    public void hit(int damage) {
        if (Alive) {
            Health -= damage;
            System.out.println("base health " + Health);
            if (Health <= 0) {
                Alive = false;
                GamePanel.blocks.remove(bl1);
                GamePanel.blocks.remove(bl2);
                GamePanel.blocks.remove(bl3);
                GamePanel.blocks.remove(bl4);
            }
        }


    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        if (Alive) {
            g.drawImage(baseimage, x, y, null);
        }

    }


    public int getHealth() {
        return Health;
    }

}
