package start;

import start.Logic.Constants;

import java.awt.*;


public class GameBackground implements Constants {
    //Fields
    private Color color;
    private int width;
    private int height;
    private boolean epilepticmode;
    private int i;
    double modeTime;

    public GameBackground() {
        width = PANEL_WIDTH;
        height = PANEL_HEIGHT;
        color = Color.WHITE;
        epilepticmode = false;
        i = 0;
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
        g.setColor(color);
        g.fillRect(0, 0, width, height);

    }

    public void setDim(int w, int h) {
        width = w;
        height = h;
    }

}
