package start.GameObjects;

import start.Logic.Constants;

import java.awt.*;

public class Drop implements Constants {
    private int x;
    private int y;
    private Player player;
    private int type;
    private boolean dead;

    public boolean isDead() {
        return dead;
    }

    public Drop(int x, int y, int type, Player player) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        dead = false;
    }


    public void update() {
        if ((player.x >= x) && (player.x <= x + 50) && (player.y > y) && (player.y < y + 50)) {
            player.fireRateChange();
//            dead = true;

        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);
    }
}
