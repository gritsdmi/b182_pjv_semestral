package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;
import java.io.Serializable;

public class Block implements Constants, Serializable {
    //Fielsds
    private int xPosition;
    private int yPosition;
    private int width = 50;
    private int height = 50;
    private Color color;
    private int health;
    private boolean isAlive;
    private int type = -1;
    private int autor = -1;
    private GamePanel gp;
    private Player player;


    //Constructor

    public Block(int type, int x, int y, GamePanel gp) {

        this.type = type;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
        this.color = associateColor();
        this.health = WALL_HEALTH;
        this.gp = gp;
    }

    //testing new blocks for enemy's moving
    public Block(int type, int x, int y, int autor) {
        this.type = type;
        this.xPosition = x;
        this.yPosition = y;
        this.isAlive = true;
        this.color = associateColor();
        this.health = WALL_HEALTH;
        this.autor = autor;

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
                c = new Color(0f, 0f, 0f, .5f);
                break;
        }

        return c;
    }


    //Methods

    private boolean controlHP() {
        if (isAlive) {
            if (health < 1) {
                Player.score++;
                this.isAlive = false;
                gp.getFreeSpacesMap().add(new Point(xPosition, yPosition));
                return false;
            }
        }
        return true;
    }

    /**
     * Depend on type, bullet does different damage.
     *
     * @param bul Bullet that intersects with this block
     * @see Bullet
     */
    public void hit(Bullet bul) {
        if (type != WALL_TYPE_BORDER && type != WALL_TYPE_INVIS) {
            this.health -= bul.getDamage();
            color = color.darker();
        }
        controlHP();
    }

    public void draw(Graphics2D g) {
        if (isAlive && (type != WALL_TYPE_INVIS)) {
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
        return new Rectangle(xPosition, yPosition, width, height);
    }

    public Point getCenterPosition() {
        return new Point(xPosition + 25, yPosition + 25);
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getAutor() {
        return autor;
    }

    public void setPosition(Point position) {
        this.xPosition = (int) position.getX();
        this.yPosition = (int) position.getY();
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
        controlHP();
    }

    public void setHealth(int hp) {
        this.health = hp;
        controlHP();
    }

    public int getHealth() {
        return health;
    }

}
