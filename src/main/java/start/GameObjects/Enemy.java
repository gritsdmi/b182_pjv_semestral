package start.GameObjects;

import start.Logic.Constants;

import java.awt.*;
import java.awt.geom.Line2D;
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
    private int wayPointIndex = 0;
    //    private ArrayList<Eye> eyes;
    private Point actualPosition;


    public Enemy(Point startPosition) {
        this.xPosition = (int) startPosition.getX();
        this.yPosition = (int) startPosition.getY();
        color = Color.PINK;
        this.isAlive = true;
        this.wayPoints = createWayPoints();
        this.actualDirection = new Point(xPosition, yPosition);
        this.smer = 0;

//        Line2D up = new Line2D.Float(this.xPosition + r/2,this.yPosition + r/2,0,0);
//        Line2D left = new Line2D.Float(this.xPosition + r/2,this.yPosition + r/2,0,0);
//        Line2D right = new Line2D.Float(this.xPosition + r/2,this.yPosition + r/2,0,0);
//        Line2D down = new Line2D.Float(this.xPosition + r/2,this.yPosition + r/2,0,0);

//        this.eyes = new ArrayList<Eye>();
//        eyes.add(new Eye(this.actualPosition,"Up"));
//        eyes.add(new Eye(this.actualPosition,"Right"));
//        eyes.add(new Eye(this.actualPosition,"Left"));
//        eyes.add(new Eye(this.actualPosition,"Down"));
        this.actualPosition = startPosition;
        this.actualPosition.setLocation(this.xPosition + r / 2, this.yPosition + r / 2);
    }


    public void update() {
        if (isAlive) {
            updateEyes();

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
///////////////////////////////////////////////////////
            //centralUP line
            int toX1 = this.xPosition + r / 2;
            int toY1 = this.yPosition + r / 2 - fireDistance;

            //right line
            int toX2 = this.xPosition + r / 2 + fireDistance;
            int toY2 = this.yPosition + r / 2;

            //left line
            int toX3 = this.xPosition + r / 2 - fireDistance;
            int toY3 = this.yPosition + r / 2;

            //centralDown line
            int toX4 = this.xPosition + r / 2;
            int toY4 = this.yPosition + r / 2 + fireDistance;


            switch (smer) {
                case 1: //up
                    toX4 = this.xPosition + r / 2;
                    toY4 = this.yPosition + r / 2;
                    break;
                case 2: //right
                    toX3 = this.xPosition + r / 2;
                    toY3 = this.yPosition + r / 2;
                    break;
                case 3: //left
                    toX2 = this.xPosition + r / 2;
                    toY2 = this.yPosition + r / 2;
                    break;
                case 4: //down
                    toX1 = this.xPosition + r / 2;
                    toY1 = this.yPosition + r / 2;
                    break;
            }
            Point up = new Point(toX1, toY1);
            Point right = new Point(toX2, toY2);
            Point left = new Point(toX3, toY3);
            Point down = new Point(toX4, toY4);

            this.actualPosition = new Point(this.xPosition + r / 2, this.yPosition + r / 2);
//            this.eyes.get(0).setLine(new Line2D.Float(actualPosition,up));
//            this.eyes.get(1).setLine(new Line2D.Float(actualPosition,right));
//            this.eyes.get(2).setLine(new Line2D.Float(actualPosition,left));
//            this.eyes.get(3).setLine(new Line2D.Float(actualPosition,down));
////////////////////////////////////////////////////////
        }
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(color);
            g.fillOval(this.xPosition, this.yPosition, r, r);
            drawEyes(g);
        }
    }

    /**
     * в зависимости от типа пули будет отнимать разной количество hр
     */
    public void hit(Bullet bul) {
        if (isAlive) {
            this.health -= bul.getDamage();
            color = color.darker();
            controlHP();
            System.out.println("enemy hit");
        }
    }

    private void controlHP() {
        if (isAlive) {
            if (health < 1) {
                this.isAlive = false;
            }
        }
    }

    //TODO this function may be inicialized in map class
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
        wayPointIndex = wayPointIndex >= wayPoints.size() ? (wayPointIndex % wayPoints.size()) : wayPointIndex;
        this.actualDirection = this.wayPoints.get(wayPointIndex++);
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

    private void drawEyes(Graphics2D g) {
        g.setColor(Color.WHITE);
        //имплементовать 4 стороны но рисовать 3, в зависимости от стороны

//        for (Line2D line : eyes){
//            g.draw(line);
//        }
    }

    private void updateEyes() {
        controlEyesCollider();
    }


    private boolean controlEyesCollider() {
        //не работает, решение: укорачивать глаз, или boolean variable, или вообще создать класс на глаза
        // или для каждого глаза считать отдельно...


        return true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getRadius() {
        return r;
    }

    @Override
    public int getxPosition() {
        return xPosition;
    }

    @Override
    public int getyPosition() {
        return yPosition;
    }
}

class Eye {

    private boolean see;
    private Point position;
    private String name;
    private Line2D direction;

    public Eye(Point startPosition, String name) {
        this.position = new Point((int) (startPosition.getX() + 25), (int) (startPosition.getY() + 25));
        this.name = name;
        this.see = false;
        this.direction = new Line2D.Float(this.position, new Point(0, 0));
    }

    void update() {
    }

    void draw() {
    }

    public boolean isSee() {
        return see;
    }

    public void setSee(boolean see) {
        this.see = see;
    }
}
