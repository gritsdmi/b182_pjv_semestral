package start;

import start.Logic.Constants;

import java.awt.*;

public class Base implements Constants {
    private int Health;
    private int x;
    private int y;

    public Base(Point loc) {
        x = loc.x;
        y = loc.y;
        Health = 100;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, BASE_SIZE_WIDTH, BASE_SIZE_HEIGHT);
    }

    public void hit(int damage) {
        Health -= damage;
        System.out.println("base health " + Health);

    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, BASE_SIZE_WIDTH, BASE_SIZE_HEIGHT);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.GRAY);
        g.drawRect(x, y, BASE_SIZE_WIDTH, BASE_SIZE_HEIGHT);
        g.setStroke(new BasicStroke(1));
    }


    public int getHealth() {
        return Health;
    }

}
