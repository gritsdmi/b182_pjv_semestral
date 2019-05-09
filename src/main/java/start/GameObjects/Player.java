package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class Player implements Constants {
    //Fields
    public static double x;
    public static double y;
    private int speed = PLAYER_MOVING_SPEED;
    private double dx;
    private double dy;
    private boolean shield;
    private double shieldTime;
    private double fireUpTime;

    public static int score = 0;


    private double locationX;
    private double locationY;
    private double reload = 250;
    private GamePanel gp;

    private boolean nextMove = true;
    public static int direction;
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static int rotation;
    public static boolean banTop = false;
    public static boolean prevbanTop = false;
    public static boolean banRight = false;
    public static boolean prevbanRight = false;
    public static boolean banDown = false;
    public static boolean prevbanDown = false;
    public static boolean banLeft = false;
    public static boolean prevbanLeft = false;
    public static boolean M1pressed = false;
    public static Point dir;
    private int health = PLAYER_HEALTH;
    private int healthBarLength;
    private int fireLevel;
    private boolean isFireUpBuffed;

    public int getHealth() {
        return health;
    }

    public double getReload() {
        return reload;
    }

    public void setShield(boolean shield) {

        this.shield = shield;
        shieldTime = System.nanoTime();
    }

    public boolean isShield() {
        return shield;
    }

    //Constructor
    public Player(GamePanel gp) {
//        x = PANEL_WIDTH / 1.5;
//        y = PANEL_HEIGHT / 1.5;
        x = 600;
        y = 600;
        dx = 0;
        dx = 0;
        healthBarLength = 100;
        this.gp = gp;
        shield = false;
        fireLevel = 0;
        isFireUpBuffed = false;

        up = false;
        down = false;
        right = false;
        left = false;
    }

    //Methods

    public void fireRateChange() {
        if (!isFireUpBuffed) {
            fireUpTime = System.nanoTime();
            reload -= 100;
            isFireUpBuffed = true;

        }

    }

    public void checkFireUp() {
        if ((System.nanoTime() - fireUpTime) / 1000000 > 7500) {

            reload += 100;
            isFireUpBuffed = false;

        }
    }

    public void checkShield() {
        if ((System.nanoTime() - shieldTime) / 1000000 > 11000) {
            shield = false;

        }
    }

    public void hit(int damage) {
        if (!shield) {
            this.health -= damage;
//            System.out.println("Player get damage " + "HP: " + health);
        } else {
//            System.out.println("Blocked");
        }

    }

    public void restoreHealth(Drop drop) {
        this.health = PLAYER_HEALTH;
    }

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
        if (M1pressed == true) {

            if (gp.delay(reload)) {
                GamePanel.bullets.add(new Bullet(x + 25, y + 25, dir, (byte) 0));

            }


        }
        if (isFireUpBuffed) {
            checkFireUp();
        }
        healthBarLength = health;

    }

    public void draw(Graphics2D g) {

        locationX = 25;
        locationY = 25;
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(rotation), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.setColor(Color.BLACK);
// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(GamePanel.TankPicture, null), (int) x, (int) y, null);
        g.drawString(score + "", PANEL_WIDTH + 10, 140);
        if (shield) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawOval((int) x - 5, (int) y - 5, 60, 60);
            checkShield();
        }


        g.setColor(Color.red);
        g.fillRect(825, 100, healthBarLength, 30);
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawRect(825, 100, 100, 30);
        g.drawString("hp", 805, 110);

    }

    public Rectangle getRectangle() {
        return new Rectangle((int) x, (int) y, PLAYER_SIZE_WIDTH, PLAYER_SIZE_HEIGHT);
    }

    public Rectangle getSmallRectangle() {
//        return new Rectangle((int) (x + 22), (int) (y+22), 5, 5);
        return new Rectangle((int) (x + 10), (int) (y + 10), 30, 30); // 30x30
    }

    public Point getCenterPosition() {
        return new Point((int) x + 25, (int) y + 25);
    }

    public Point getPosition() {
        return new Point((int) x, (int) y);
    }

    public boolean isMoving() {
        if (x % 50 == 0 && y % 50 == 0) return false;
        else return true;
    }
}
