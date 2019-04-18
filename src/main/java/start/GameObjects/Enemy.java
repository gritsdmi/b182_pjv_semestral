package start.GameObjects;

import start.Logic.Constants;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends GameObject implements Constants {
    //Fields
    private int xPosition;
    private int yPosition;
    private int dx;
    private int dy;
    private int health = ENEMY_HEALTH;
    private int r = 50;
    private int speed = ENEMY_MOVING_SPEED;
    private boolean isAlive;
    private Color color;
    private int fireDistance = ENEMY_FIRE_DISTANCE;
    private ArrayList<Point> wayPoints;
    private Point actualDirection;
    private byte smer;//1-up 2-right 3-left 4-down
    private int index = 0;


    public Enemy(Point startPosition) {
        this.xPosition = (int) startPosition.getX();
        this.yPosition = (int) startPosition.getY();
        color = Color.PINK;
        this.isAlive = true;
        this.wayPoints = createWayPoints();
        this.actualDirection = new Point(xPosition, yPosition);
        this.smer = 0;

    }


    public void update() {

        switch (smer) {
            case 1: //up
                yPosition -= speed;
                break;
            case 2://right
                xPosition += speed;
                break;
            case 3://left
                xPosition -= speed;
                break;
            case 4://down
                yPosition += speed;
                break;
            case 0:
                yPosition = yPosition;
                xPosition = xPosition;
        }

        if (this.xPosition == actualDirection.getX() && this.yPosition == actualDirection.getY()) {
            //TODO stay here for 3 sec before change way point
            changePoint();
//            System.out.println("changePoint to " + actualDirection);
        }
        setNewDirection();

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillOval(this.xPosition, this.yPosition, r, r);
    }

    /**
     * в зависимости от типа пули будет отнимать разной количество hр
     */
    public void hit(Bullet bul) {
        this.health -= bul.getDamage();
        color = color.darker();
        controlHP();
    }

    private void controlHP() {
        if (isAlive) {
            if (health < 1) {
                this.isAlive = false;
            }
        }
    }

    private ArrayList<Point> createWayPoints() {
        ArrayList<Point> ret = new ArrayList<Point>();

        ret.add(new Point(500, 50));//
        ret.add(new Point(701, 50));
        ret.add(new Point(701, 299));
        ret.add(new Point(500, 299));
//        System.out.println(ret);

        return ret;
    }

    /**
     * @param
     */
    private void changePoint() {
//        System.out.println("index of way points "+index);
        index = index >= wayPoints.size() ? (index % wayPoints.size()) : index;
        this.actualDirection = this.wayPoints.get(index++);
    }

    private void setNewDirection() {
        //right
        if (this.actualDirection.getX() - this.xPosition > 0) smer = 2;
            //left
        else if (this.actualDirection.getX() - this.xPosition < 0) smer = 3;
        //down
        if (this.actualDirection.getY() - this.yPosition > 0) smer = 4;
            //up
        else if (this.actualDirection.getY() - this.yPosition < 0) smer = 1;
//        System.out.print("setNewDir= " + smer);
//        System.out.println(this.actualDirection.toString() + this.yPosition);

    }

    public boolean isAlive() {
        return isAlive;
    }
}
