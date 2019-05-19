package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Drop implements Constants {
    private int x;
    private int y;
    private Player player;
    private int type;
    private boolean dead;
    private Color color;
    private double lifeTimer;
    private BufferedImage image;

    public boolean isDead() {
        return dead;
    }

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
                    color = Color.PINK;
                    break;
                case 3:
                    image = ImageIO.read(new File("src/main/resources/Entity/ShieldBonus.png"));

                    break;

            }
        } catch (Exception e) {

        }



    }


    public boolean checkTimer() {
        if (((System.nanoTime() - lifeTimer) / 1000000) > 10000) {
            return true;
        } else {
            return false;
        }
    }

    public void update() {
        if (((player.getX() + 5 > x) && (player.getX() + 5 < x + 50) && (player.getY() + 5 > y) && (player.getY() + 5 < y + 50)) || ((player.getX() + 45 > x) && (player.getX() + 45 < x + 50) && (player.getY() + 45 > y) && (player.getY() + 45 < y + 50))) {
            switch (type) {
                case 0:
                    player.hit(30);
                    System.out.println("ti govno");
                    break;
                case 1:
                    player.fireRateChange();
                    GamePanel.background.startMode();
                    break;
                case 2:
                    System.out.println("restore health");
                    player.restoreHealth(this);
                    break;
                case 3:
                    System.out.println("Shield");
                    player.setShield(true);
                    break;
            }

            dead = true;

        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, x, y, null);
    }
}
