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
    private double locationX;
    private double locationY;
    private double reload = 200;

    private boolean nextMove = true;
    public static int direction;
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean isFiring;
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

    public double getReload() {
        return reload;
    }
    //Constructor
    public Player() {
        x = PANEL_WIDTH / 2;
        y = PANEL_HEIGHT / 2;
        dx = 0;
        dx = 0;


        up = false;
        down = false;
        right = false;
        left = false;
        isFiring = false;
    }

    //Metheds
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


    }

    public void draw(Graphics2D g) {

        locationX = 25;
        locationY = 25;
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(rotation), locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(GamePanel.TankPicture, null), (int) x, (int) y, null);

    }
}
