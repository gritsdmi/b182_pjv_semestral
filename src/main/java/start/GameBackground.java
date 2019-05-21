package start;

import start.InputTesting.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;


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

        }

    }

    public void startMode() {
        epilepticmode = true;
        modeTime = System.nanoTime();
    }

    public void update() {

        if (epilepticmode) {
            if (i == 0) {
                color = Color.BLACK;
                i = 1;
            } else {
                color = Color.RED;
                i = 0;
            }
            if ((System.nanoTime() - modeTime) / 1000000 > 6000) {

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
                g.setColor(color);
                g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            }

        } else {
            g.setColor(color);
            g.fillRect(0, 0, width, height);
        }


    }

    public void setDim(int w, int h) {
        width = w;
        height = h;
    }

}
