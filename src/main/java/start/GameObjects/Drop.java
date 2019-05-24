package start.GameObjects;

import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Drop implements Constants, Serializable {
    // Fields

    private int x;
    private int y;
    private Player player;
    private int type;
    private boolean dead;
    private double lifeTimer;
    private transient BufferedImage image;

    //Constructor

    public Drop(int x, int y, int type, Player player) {
        lifeTimer = System.nanoTime();
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        dead = false;
        try {
            switch (type) {
                case 0: {
                    image = ImageIO.read(new File("src/main/resources/Entity/BadBonus.png"));

                    break;
                }
                case 1:
                    image = ImageIO.read(new File("src/main/resources/Entity/ModeBonus.png"));
                    break;
                case 2:
                    image = ImageIO.read(new File("src/main/resources/Entity/HealthRestoreBonus.png"));
                    break;
                case 3:
                    image = ImageIO.read(new File("src/main/resources/Entity/ShieldBonus.png"));

                    break;

            }
        } catch (IOException e) {
            Logger.getLogger(Drop.class.getName()).log(Level.SEVERE, "Error loading file", e);
        }

    }

    //Methods

    /**
     * Method simulates delay effect by 10000 ns
     *
     * @return true if needed time passed
     */
    public boolean checkTimer() {
        if (((System.nanoTime() - lifeTimer) / 1000000) > 10000) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Method controls collision of drop`s collider with other collider of player
     * in case of collision method calls player`s methods depending on drop`s type
     */
    public void update() {
        if (((player.getX() + 5 > x) && (player.getX() + 5 < x + 50) && (player.getY() + 5 > y) && (player.getY() + 5 < y + 50)) || ((player.getX() + 45 > x) && (player.getX() + 45 < x + 50) && (player.getY() + 45 > y) && (player.getY() + 45 < y + 50))) {
            player.takeBounus(this);

            dead = true;

        }
    }

    /**
     * Method draws object in the game by
     * @param g
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, null);
    }

    int getType() {
        return type;
    }

    public boolean isDead() {
        return dead;
    }
}
