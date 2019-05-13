package start;

import start.Logic.Constants;

import java.awt.*;


public class GameBackground implements Constants {
    //Fields
    private Color color;
    private int width;
    private int height;

    public GameBackground() {
        width = PANEL_WIDTH;
        height = PANEL_HEIGHT;
        color = Color.WHITE;
    }

    public void update() {

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
