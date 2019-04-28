package start;

import start.Logic.Constants;

import java.awt.*;

public class MenuBackground implements Constants {
    //Fields
    private Color color;

    public MenuBackground() {
        color = Color.YELLOW;
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(PANEL_WIDTH, 0, 150, PANEL_HEIGHT);

    }
}
