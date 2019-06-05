package start;

import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameBackground implements Constants, Serializable {
    //Fields
    private Color color;
    private int width;
    private int height;
    private boolean epilepticmode;
    private int i;
    double modeTime;
    private BufferedImage image;

    public GameBackground() {
        width = PANEL_WIDTH;
        height = PANEL_HEIGHT;
        color = Color.ORANGE;
        epilepticmode = false;
        i = 0;
        try {
            image = ImageIO.read(new File("src/main/resources/Entity/map.png"));
        } catch (Exception e) {
            Logger.getLogger(GameBackground.class.getName()).log(Level.SEVERE, "Error loading file", e);
        }

    }

    public void startMode() {
        epilepticmode = true;
        modeTime = System.nanoTime();
    }

    public void update() {

        if (epilepticmode) {

            if ((System.nanoTime() - modeTime) / 1000000 > 7500) {

                epilepticmode = false;
                color = Color.WHITE;

            }
        }
    }


    public void draw(Graphics2D g) {
        if (!GamePanel.menu) {
            if (!epilepticmode) {
                g.drawImage(image, 0, 0, null);

            } else {
                g.drawImage(image, 0, 0, null);
                g.setColor(new Color(1, 0f, 0f, 0.6f));
                g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
                g.setColor(Color.BLACK);
            }

        } else {
            g.setColor(color);
            g.fillRect(0, 0, width, height);
        }


    }

    void setDim(int w, int h) {
        width = w;
        height = h;
    }

    void setEpilepticmode(boolean epilepticmode) {
        this.epilepticmode = epilepticmode;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
