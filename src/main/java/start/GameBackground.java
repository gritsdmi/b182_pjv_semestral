package start;

import java.awt.*;


public class GameBackground implements Constants {
    //Fields
    private Color color;

    public GameBackground() {
        color = Color.BLUE;
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

    }
}
