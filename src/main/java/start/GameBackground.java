package start;

import java.awt.*;


public class GameBackground {
    //Fields
    private Color color;

    public GameBackground() {
        color = Color.BLUE;
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

    }
}
