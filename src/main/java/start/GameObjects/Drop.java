package start.GameObjects;

import start.Logic.Constants;

import java.awt.*;

public class Drop implements Constants {
    private int x;
    private int y;
    private Player player;
    private int type;
    private boolean dead;
    private Color color;

    public boolean isDead() {
        return dead;
    }

    public Drop(int x, int y, int type, Player player) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.player = player;
        dead = false;
        switch (type) {
            case 0: {
                color = Color.GRAY;
                break;
            }
            case 1:
                color = Color.YELLOW;
                break;
            case 2:
                color = Color.PINK;
                break;
            case 3:
                color = Color.CYAN;
                break;

        }
    }


    public void update() {
        if (((player.x + 5 > x) && (player.x + 5 < x + 50) && (player.y + 5 > y) && (player.y + 5 < y + 50)) || ((player.x + 45 > x) && (player.x + 45 < x + 50) && (player.y + 45 > y) && (player.y + 45 < y + 50))) {
            switch (type) {
                case 0:
                    player.hit(30);
                    System.out.println("ti govno");
                    break;
                case 1:
                    player.fireRateChange();
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
        g.setColor(color);
        g.fillRect(x + 7, y + 7, 36, 36);
    }
}
