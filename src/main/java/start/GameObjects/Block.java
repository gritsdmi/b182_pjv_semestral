package start.GameObjects;

import start.Logic.Constants;

import java.awt.*;
import java.io.Serializable;

public class Block extends GameObject implements Constants, Serializable {
    //Fielsds
    private int xPosition;
    private int yPosition;
    private int width = 50;
    private int height = 50;
    private Color color;
    private int health;
    private boolean isAlive;
    private int type = -1;

    private Player player;


    //Constructor


    public Block(int type, int x, int y) {

        this.type = type;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
        this.color = associateColor();
        this.health = WALL_HEALTH;
    }

    private Color associateColor() {
        Color c;
        switch (this.type) {
            case WALL_TYPE_BORDER:
                c = Color.BLACK;
                break;
            case WALL_TYPE_BRICK:
                c = Color.RED;
                break;
            case WALL_TYPE_TEST:
                c = Color.GREEN;
                break;
            default:
                c = new Color(1f, 0f, 0f, .5f);
                break;
        }

        return c;
    }


    //Methods

    private void controlHP() {
        if (isAlive) {
            if (health < 1) {
                Player.score++;
                this.isAlive = false;
            }
        }
    }

    /**
     * в зависимости от типа пули будет отнимать разной количество hр
     */
    public void hit(Bullet bul) {
        if (type != WALL_TYPE_BORDER && type != WALL_TYPE_INVIS) {
            this.health -= bul.getDamage();
            color = color.darker();
        }
        controlHP();
    }

    public void draw(Graphics2D g) {
        if (isAlive && type != WALL_TYPE_INVIS) {
            g.setColor(color);
            g.fillRect(xPosition, yPosition, width, height);

            g.setStroke(new BasicStroke(3));
            g.setColor(Color.GRAY);
            g.drawRect(this.xPosition, this.yPosition, this.width, this.height);
            g.setStroke(new BasicStroke(1));

        }
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public Rectangle getRectangle() {
        return new Rectangle(this.xPosition, this.yPosition, width, height);
    }

    public Point getCenterPosition() {
        return new Point((int) xPosition + 25, (int) yPosition + 25);
    }
}
