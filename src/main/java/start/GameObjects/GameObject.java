package start.GameObjects;

import java.awt.*;

public abstract class GameObject {

    private int xPosition;
    private int yPosition;
    private boolean isAlive;

    public void update() {
    }

    public void draw(Graphics2D g) {
    }

    public void setX(int x) {
        this.xPosition = x;
    }

    public int getxPosition() {
        return this.xPosition;
    }

    public void setY(int y) {
        this.yPosition = y;
    }

    public int getyPosition() {
        return this.yPosition;
    }
}
