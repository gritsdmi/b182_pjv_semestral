package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Player implements Constants, Serializable {

    //Fields

    private int x;
    private int y;
    private int speed = PLAYER_MOVING_SPEED;
    private double dx;
    private double dy;
    private boolean shield;
    private double shieldTime;
    private double fireUpTime;
    private double locationX;
    private double locationY;
    private double reload = 400;
    private GamePanel gp;
    private boolean nextMove = true;
    private int direction;
    private int rotation;
    private boolean banTop = false;
    private boolean prevbanTop = false;
    private boolean banRight = false;
    private boolean prevbanRight = false;
    private boolean banDown = false;
    private boolean prevbanDown = false;
    private boolean banLeft = false;
    private boolean prevbanLeft = false;
    private boolean M1pressed = false;
    private Point dir;
    private transient BufferedImage tankPicture;
    private transient BufferedImage tankTowerPicture;
    private int health = PLAYER_HEALTH;
    private int healthBarLength;
    private int fireLevel;
    private boolean isFireUpBuffed;
    private int type;
    private Point turelDirection = new Point(0, 0);






    //Constructor
    public Player(GamePanel gp, int x, int y, int type) {

        this.x = x;
        this.y = y;
        this.type = type;
        dx = 0;
        dx = 0;
        healthBarLength = 100;
        this.gp = gp;
        shield = false;
        fireLevel = 0;
        isFireUpBuffed = false;


    }

    //Methods

    /**
     * Method simulates picking up the shield bonus
     * assigns shield variable true and sets the timer of shield
     */
    public void setShield() {

        shield = true;
        shieldTime = System.nanoTime();
    }

    /**
     * Method simulates picking up the boost bonus
     * decreases reload time, increase speed and sets the timer of boost
     */
    public void fireRateChange() {
        if (!isFireUpBuffed) {
            fireUpTime = System.nanoTime();
            reload -= 200;
            speed = speed * 2;
            isFireUpBuffed = true;

        }

    }

    /**
     * Method assigns its pictures variables right pictures from resource file by 2wo String params:
     *
     * @param tankPic
     * @param turelPIc
     */
    public void setTankPictures(String tankPic, String turelPIc) {
        try {
            tankPicture = ImageIO.read(new File(tankPic));
            tankTowerPicture = ImageIO.read(new File(turelPIc));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method simulates delay effect by 7500 ns
     * after time passed returnes player to normal mode
     */
    public void checkFireUp() {
        if ((System.nanoTime() - fireUpTime) / 1000000 > 7500) {

            reload += 200;
            speed = (int) speed / 2;
            isFireUpBuffed = false;

        }
    }

    /**
     * Method simulates delay effect by 11000 ns
     * after time passed ends shield effect
     */
    public void checkShield() {
        if ((System.nanoTime() - shieldTime) / 1000000 > 11000) {
            shield = false;

        }
    }


    /**
     * Method simulates picking up the restoreHealth bonus
     * restore player`s health to maximum
     */
    public void restoreHealth(Drop drop) {
        this.health = PLAYER_HEALTH;
    }

    /**
     * Method updates player`s position,
     * checks available directions to move,
     * checks states of bonus effects
     * and sets healthBar length
     */
    public void update() {
        if (nextMove) {
            prevbanLeft = false;
            prevbanDown = false;
            prevbanRight = false;
            prevbanTop = false;
            for (int i = 0; i < GamePanel.blocks.size(); i++) {
                if ((GamePanel.blocks.get(i).getxPosition() == (x - 50)) && (GamePanel.blocks.get(i).getyPosition() == y)) {
                    banLeft = true;
                    prevbanLeft = true;
                } else if ((GamePanel.blocks.get(i).getxPosition() == (x + 50)) && (GamePanel.blocks.get(i).getyPosition() == y)) {
                    banRight = true;
                    prevbanRight = true;
                } else if ((GamePanel.blocks.get(i).getxPosition() == x) && (GamePanel.blocks.get(i).getyPosition() == (y - 50))) {
                    banTop = true;
                    prevbanTop = true;
                } else if ((GamePanel.blocks.get(i).getxPosition() == x) && (GamePanel.blocks.get(i).getyPosition() == (y + 50))) {
                    banDown = true;
                    prevbanDown = true;
                }
            }
            if ((banTop) && (!prevbanTop)) {
                banTop = false;

            }
            if ((banRight) && (!prevbanRight)) {
                banRight = false;

            }
            if ((banDown) && (!prevbanDown)) {
                banDown = false;

            }
            if ((banLeft) && (!prevbanLeft)) {
                banLeft = false;

            }
        }
        if (nextMove) {
            switch (direction) {
                case 1:
                    if (!banTop) {
                        dx = 0;
                        dy = -speed;
                        nextMove = false;
                    }

                    rotation = 0;

                    break;
                case 2:
                    if (!banRight) {
                        dx = speed;
                        dy = 0;
                        nextMove = false;
                    }

                    rotation = 90;

                    break;
                case 3:
                    if (!banDown) {
                        dx = 0;
                        dy = speed;
                        nextMove = false;
                    }

                    rotation = 180;

                    break;
                case 4:
                    if (!banLeft) {
                        dx = -speed;
                        dy = 0;
                        nextMove = false;
                    }

                    rotation = 270;

                    break;
                default:
                    dx = 0;
                    dy = 0;
                    break;
            }
        }

        x += dx;
        y += dy;
        if ((dy != 0) && (y % 50 == 0) && !nextMove) {
            dy = 0;

            nextMove = true;
        }
        if ((dx != 0) && (x % 50 == 0) && !nextMove) {
            dx = 0;
            nextMove = true;
        }
        if (!GamePanel.isClient) {
            if (M1pressed) {

                shoot(1);


            }
            if (isFireUpBuffed) {
                checkFireUp();
            }
        }




    }

    /**
     * Method creates object Bullet of type:
     *
     * @param num end sets its position and direction
     */
    public void shoot(int num) {
        if (num == 1) {
            if (gp.delay(reload)) {
                GamePanel.bullets.add(new Bullet(x + 25, y + 25, dir, 0));

            }
        } else {
            if (gp.delay2(reload)) {
                GamePanel.bullets.add(new Bullet(x + 25, y + 25, dir, 2));

            }
        }

    }

    /**
     * Method calls player`s bonus-effect methods depending on
     * @param bonus
     */
    public void takeBounus(Drop bonus) {
        switch (bonus.getType()) {
            case 0:
                takeDamage(30);
                System.out.println("ti govno");
                break;
            case 1:
                fireRateChange();
                GamePanel.background.startMode();
                break;
            case 2:
                System.out.println("restore health");
                restoreHealth(bonus);
                break;
            case 3:
                System.out.println("Shield");
                setShield();
                break;
        }
    }

    /**
     * Method sets player`s health to new by
     * @param health
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Method draws object in the game by
     * @param g
     */
    public void draw(Graphics2D g) {

        locationX = 25;
        locationY = 25;
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(rotation), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.setColor(Color.BLACK);
// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(tankPicture, null), x, y, null);

        if (shield) {
            g.setColor(Color.CYAN);
            g.setStroke(new BasicStroke(3));
            g.drawOval(x - 5, y - 5, 60, 60);
            checkShield();
        }

        if (type == 1) {
            g.setColor(Color.red);
            g.fillRect(825, 100, healthBarLength, 30);
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.drawRect(825, 100, 100, 30);
            g.drawString("hp", 860, 90);
        }


        //TUREL


        AffineTransform turelAt = AffineTransform.getRotateInstance(Math.toRadians(Math.toDegrees(
                Math.atan2(turelDirection.y - (y + 25), turelDirection.x - (x + 25))) + 90), 25, 50);
        AffineTransformOp turelOp = new AffineTransformOp(turelAt, AffineTransformOp.TYPE_BILINEAR);

        // Drawing the rotated image at the required drawing locations
        g.drawImage(turelOp.filter(tankTowerPicture, null), x, y - 25, null);

    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, PLAYER_SIZE_WIDTH, PLAYER_SIZE_HEIGHT);
    }

    public Rectangle getSmallRectangle() {
        return new Rectangle(x + 10, y, 30, 30); // 30x30
    }

    public Point getCenterPosition() {
        return new Point(x + 25, y + 25);
    }

    public Point getPosition() {
        return new Point(x, y);
    }

    public boolean isMoving() {
        if (x % 50 == 0 && y % 50 == 0) return false;
        else return true;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(Point newPosition) {
        this.x = (int) newPosition.getX();
        this.y = (int) newPosition.getY();
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isM1pressed() {
        return M1pressed;
    }

    public void setM1pressed(boolean m1pressed) {
        M1pressed = m1pressed;
    }

    public Point getDir() {
        return dir;
    }

    public void setDir(Point dir) {
        this.dir = dir;
    }

    /**
     * Method for serialisation
     * @return
     */
    public ArrayList<ArrayList> prepareDataToSerialize() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(this.getPosition());
        points.add(this.getDir());

        ArrayList<Integer> intData = new ArrayList<>();
        intData.add(health);


        ArrayList<ArrayList> ret = new ArrayList<>();
        ret.add(points);
        ret.add(intData);

        return ret;
    }

    /**
     * Method decrease player`s health by
     * @param damage amount
     */
    public void takeDamage(int damage) {
        if (!shield) {
            this.health = health - damage;
            healthBarLength = health;
        }

    }

    public boolean isAlive() {
        if (health < 1) return false;
        else return true;
    }

    public Point getTurelDirection() {
        return turelDirection;
    }

    public void turelSetDirection(Point direction) {
        turelDirection = direction;
    }

    public int getHealth() {
        return health;
    }



}
