package start;

import java.awt.*;

public class Bullet {
    //Fields
    private double x;
    private double y;
    private int r;
    private int speed;

    Color color1;

    //Constructor
    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        r = 2;
        color1 = Color.WHITE;
        speed = 10;
    }

    //Methods
    public boolean remove() {
        if (y < 0) return true;
        return false;
    }

    public void update() {
        y -= speed;

    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) x, (int) y, r, r);

    }


}
