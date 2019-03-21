package start;

import java.awt.*;

public class Bullet {
    //Fields
    private double x;
    private double y;
    private int r;
    private int speed;

    private Point dir;
    private double dx;
    private double dy;
    private double angle;

    Color color1;

    //Constructor
    public Bullet(double x, double y, Point directoin) {
        this.x = x;
        this.y = y;
        r = 2;
        color1 = Color.WHITE;
        speed = 10;
        this.dir = directoin;

        this.dy = Math.sin(Math.toRadians(Math.toDegrees(Math.atan2(dir.y - this.y, dir.x - this.x))));
        this.dx = Math.cos(Math.toRadians(Math.toDegrees(Math.atan2(dir.y - this.y, dir.x - this.x))));
//        this.angle =

    }

    //Methods
    public boolean remove() {
//        if (y < 0) return true;
        return false;
    }

    public void update() {
//        y -= speed;
        y += dy * speed;
        x += dx * speed;
//        controlAlive();


    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) x, (int) y, r, r);

    }


}
