package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Base implements Constants, Serializable {
    // Fields
    private int health;
    private int x;
    private int y;
    private boolean isAlive;
    private Block bl1;
    private Block bl2;
    private Block bl3;
    private Block bl4;
    private transient BufferedImage baseimage;
    private GamePanel gp;
    private int healthBarLength;

    // Constructor

    public Base(Point position, GamePanel gp) {
        x = position.x;
        y = position.y;
        health = 100;
        this.gp = gp;
        bl1 = new Block(WALL_TYPE_INVIS, x, y, gp);
        bl2 = new Block(WALL_TYPE_INVIS, x + 50, y, gp);
        bl3 = new Block(WALL_TYPE_INVIS, x, y + 50, gp);
        bl4 = new Block(WALL_TYPE_INVIS, x + 50, y + 50, gp);
        GamePanel.blocks.add(bl1);
        GamePanel.blocks.add(bl2);
        GamePanel.blocks.add(bl3);
        GamePanel.blocks.add(bl4);
        isAlive = true;
        try {
            baseimage = ImageIO.read(new File("src/main/resources/Entity/Base.png"));
        } catch (IOException e) {
            Logger.getLogger(Base.class.getName()).log(Level.SEVERE, "Error loading file", e);
        }
    }

    // Methods

    /**
     * Method decreases base`s health by amount that
     *
     * @param damage represents
     *               and checks the current health,if it is less than 1 calls method die()
     */
    public void hit(int damage) {
        if (isAlive) {
            health -= damage;
            System.out.println("base health " + health);
            if (health <= 0) {
                die();
            }
        }

    }

    private void die() {
        isAlive = false;
        GamePanel.blocks.remove(bl1);
        GamePanel.blocks.remove(bl2);
        GamePanel.blocks.remove(bl3);
        GamePanel.blocks.remove(bl4);

        GamePanel.player.setHealth(0);
    }

    public void update() {

    }

    /**
     * Method draws object in the game by
     * @param g
     */
    public void draw(Graphics2D g) {
        if (isAlive) {
            g.drawImage(baseimage, x, y, null);
            g.setColor(Color.GREEN);

            g.fillRect(825, 200, (int) health, 30);
            g.setColor(Color.BLACK);
            g.drawRect(825, 200, 100, 30);
            g.drawString("hp base", 830, 190);
        }

    }


    public int getHealth() {
        return health;
    }

    Rectangle getRectangle() {
        return new Rectangle(x, y, BASE_SIZE_WIDTH, BASE_SIZE_HEIGHT);
    }

    Point getCenter() {
        return new Point(x + 50, y + 50);
    }

    public void setBaseImage() {
        try {
            baseimage = ImageIO.read(new File("src/main/resources/Entity/Base.png"));
        } catch (IOException e) {
            Logger.getLogger(Base.class.getName()).log(Level.SEVERE, "Error loading file", e);
        }
    }

}
