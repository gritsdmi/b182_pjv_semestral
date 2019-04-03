package start;

import java.awt.*;

public class Block implements Constants {
    //Fielsds
    private int xPosition;
    private int yPosition;
    private int width = 50;
    private int height = 50;
    private Color color;
    private int health;
    private boolean isAlive;
    private byte type = -1;

    //Constructor
    public Block(Color c, int x, int y) {
        this.color = c;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
    }


    public Block(byte type, int x, int y) {
        this.type = type;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
        this.color = asociateColor();
    }

    private Color asociateColor() {
        Color c;
        switch (this.type) {
            case WALL_TYPE_BORDER:
                c = Color.BLACK;
                break;
            case WALL_TYPE_BRICK:
                c = Color.RED;
                break;
            default:
                c = new Color(1f, 0f, 0f, .5f);
        }

        return c;
    }


    //Methods

    public void update() {
//        controlXP();

    }

    private void controlXP() {
        if (isAlive) {
            if (health < 1) {
                this.isAlive = false;
            }
        }
    }

    /**
     * в зависимости от типа пули будет отнимать разной количество хр
     */
    public void hit(Bullet bul) {
        this.health--;
        controlXP();
//        System.out.println("hit JP = " + this.health);
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(color);
            g.fillRect(xPosition, yPosition, width, height);

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.GRAY);
            g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
            g.setStroke(new BasicStroke(1));

        }
    }

    public int getX() {
        return xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
