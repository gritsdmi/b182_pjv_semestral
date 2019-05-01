package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Enemy implements Constants {
    //Fields
    private int xPosition;
    private int yPosition;
    private int dx;
    private int dy;
    private int health = ENEMY_HEALTH;
    private int r = 46;
    private int speed = ENEMY_MOVING_SPEED;
    private boolean isAlive;
    private Color color;
    private int fireDistance = ENEMY_FIRE_DISTANCE;
    private ArrayList<Point> wayPoints;
    private Point actualDirection;
    private byte smer;//1-up 2-right 3-left 4-down
    private int wayPointIndex = 0;
    private ArrayList<Eye> eyes;
    private Point actualPosition; //enemy's center position
    private double enemyReload = 250;


    // функция на выравнивание позиции на 50 пиксель
    private void fce() {
        if (xPosition % 50 == 1) {
            xPosition--;
            System.out.println("x==1");
        } else if (xPosition % 50 == 49) {
            xPosition++;
            System.out.println("x==49");
        }
//        else
//        if(xPosition % 50 == 2){
//            xPosition--;
//            xPosition--;
//            System.out.println("x==2");
//        }else
//        if(xPosition % 50 == 48){
//            xPosition++;
//            xPosition++;
//            System.out.println("x==48");
//        }

        if (yPosition % 50 == 1) {
            yPosition--;
            System.out.println("y==1");
        } else if (yPosition % 50 == 49) {
            yPosition++;
            System.out.println("y==49");
        }
//        else
//        if(yPosition % 50 == 2){
//            yPosition--;
//            yPosition--;
//            System.out.println("y==2");
//        }else
//        if(yPosition % 50 == 48){
//            yPosition++;
//            yPosition++;
//            System.out.println("y==48");
//        }
    }
    //TODO на каждый 50 блок делать проверку на доступные стороны



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

        this.eyes = new ArrayList<Eye>();

        this.actualPosition = startPosition;
        this.actualPosition.setLocation(this.xPosition + r / 2, this.yPosition + r / 2);

        eyes.add(new Eye(this.actualPosition, "Up"));
        eyes.add(new Eye(this.actualPosition, "Right"));
        eyes.add(new Eye(this.actualPosition, "Left"));
        eyes.add(new Eye(this.actualPosition, "Down"));
    }


    public void update() {
        if (isAlive) {

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
//            //centralUP line
//            int toX1 = this.xPosition + r / 2;
//            int toY1 = this.yPosition + r / 2 - fireDistance;
//
//            //right line
//            int toX2 = this.xPosition + r / 2 + fireDistance;
//            int toY2 = this.yPosition + r / 2;
//
//            //left line
//            int toX3 = this.xPosition + r / 2 - fireDistance;
//            int toY3 = this.yPosition + r / 2;
//
//            //centralDown line
//            int toX4 = this.xPosition + r / 2;
//            int toY4 = this.yPosition + r / 2 + fireDistance;
//
//
//            switch (smer) {
//                case 1: //up
//                    toX4 = this.xPosition + r / 2;
//                    toY4 = this.yPosition + r / 2;
//                    break;
//                case 2: //right
//                    toX3 = this.xPosition + r / 2;
//                    toY3 = this.yPosition + r / 2;
//                    break;
//                case 3: //left
//                    toX2 = this.xPosition + r / 2;
//                    toY2 = this.yPosition + r / 2;
//                    break;
//                case 4: //down
//                    toX1 = this.xPosition + r / 2;
//                    toY1 = this.yPosition + r / 2;
//                    break;
//            }
            int toX1 = (int) this.actualPosition.getX();
            int toY1 = (int) this.actualPosition.getY() - fireDistance;

            //right line
            int toX2 = (int) this.actualPosition.getX() + fireDistance;
            int toY2 = (int) this.actualPosition.getY();

            //left line
            int toX3 = (int) this.actualPosition.getX() - fireDistance;
            int toY3 = (int) this.actualPosition.getY();

            //centralDown line
            int toX4 = (int) this.actualPosition.getX();
            int toY4 = (int) this.actualPosition.getY() + fireDistance;

            switch (smer) {
                case 1: //up
                    toX4 = (int) this.actualPosition.getX();
                    toY4 = (int) this.actualPosition.getY();
                    break;
                case 2: //right
                    toX3 = (int) this.actualPosition.getX();
                    toY3 = (int) this.actualPosition.getY();
                    break;
                case 3: //left
                    toX2 = (int) this.actualPosition.getX();
                    toY2 = (int) this.actualPosition.getY();
                    break;
                case 4: //down
                    toX1 = (int) this.actualPosition.getX();
                    toY1 = (int) this.actualPosition.getY();
                    break;
            }
            Point up = new Point(toX1, toY1);
            Point right = new Point(toX2, toY2);
            Point left = new Point(toX3, toY3);
            Point down = new Point(toX4, toY4);

            ArrayList<Point> qq = new ArrayList<Point>();
            qq.add(up);
            qq.add(right);
            qq.add(left);
            qq.add(down);
            for (int i = 0; i < eyes.size(); i++) {
                eyes.get(i).update(this.actualPosition, qq.get(i));
            }


            this.actualPosition.setLocation(this.xPosition + 25, this.yPosition + 25);
////////////////////////////////////////////////////////
        }
//        System.out.println("xPos "+ xPosition +" yPos "+ yPosition );

        //функция на выравнивание позиции на 50 пиксель
        fce();

    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(color);
            g.fillOval(this.xPosition + 3, this.yPosition + 3, r, r);
            for (Eye eye : eyes) eye.draw(g);
//            drawEyes(g);
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
            System.out.println("enemy hit " + health);
        }
    }

    private void controlHP() {
        if (health < 1) {
            this.isAlive = false;
        }
    }

    //TODO this function may be inicialized in map class
    private ArrayList<Point> createWayPoints() {
        ArrayList<Point> ret = new ArrayList<Point>();

        ret.add(new Point(500, 50));//
        ret.add(new Point(700, 50));
        ret.add(new Point(700, 300));
        ret.add(new Point(500, 300));
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

    public boolean isAlive() {
        return isAlive;
    }

    public int getRadius() {
        return r;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}

class Eye implements Constants {

    private boolean see;
    private Point position; //start position
    private Point endPosition; //end position
    private String name;
    private Line2D eye;

    private int smer;
    private int fireDistance = ENEMY_FIRE_DISTANCE;

    private int enemyReload = 250;
    private double nanotime = System.nanoTime();


    public Eye(Point startPosition, String name) {
        this.position = new Point((int) (startPosition.getX()), (int) (startPosition.getY()));
        this.endPosition = new Point(0, 0);
        this.name = name;
        this.see = false;
        this.eye = new Line2D.Float(this.position, this.endPosition);
    }

    //    void update(Point pos, int smerr) {//Point actualPosition, smer направление движения врага
//        this.smer = smerr;
    void update(Point pos, Point endPos) {//Point actualPosition,
        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
        this.eye.setLine(this.position, this.endPosition);

//        if(position.equals(endPosition)) System.out.println(name);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        //centralUP line
        int toX1 = (int) this.position.getX();
        int toY1 = (int) this.position.getY() - fireDistance;

        //right line
        int toX2 = (int) this.position.getX() + fireDistance;
        int toY2 = (int) this.position.getY();

        //left line
        int toX3 = (int) this.position.getX() - fireDistance;
        int toY3 = (int) this.position.getY();

        //centralDown line
        int toX4 = (int) this.position.getX();
        int toY4 = (int) this.position.getY() + fireDistance;

        switch (smer) {
            case 1: //up
                toX4 = (int) this.position.getX();
                toY4 = (int) this.position.getY();
                System.out.println(name + "up");
                break;
            case 2: //right
                toX3 = (int) this.position.getX();
                toY3 = (int) this.position.getY();
                System.out.println(name + "right");

                break;
            case 3: //left
                toX2 = (int) this.position.getX();
                toY2 = (int) this.position.getY();
                System.out.println(name + "left");

                break;
            case 4: //down
                toX1 = (int) this.position.getX();
                toY1 = (int) this.position.getY();
                System.out.println(name + "down");

                break;
        }
//        this.endPosition.setLocation();
//////////////////////////////////////////////////////////////////////////////////////////////////////////
        //TODO shooting delay
        if (controlPlayerCollider() && computeShootingDelay(enemyReload)) {
            GamePanel.bullets.add(new Bullet(position.getX(), position.getY(), GamePanel.player.getCenterPosition(), (byte) 1));
        }

//            GamePanel.bullets.add(new Bullet(position.getX() + 25, position.getY() + 25, dir, (byte) 0));


    }

    private boolean computeShootingDelay(double del) {
        if ((System.nanoTime() - nanotime) / 1000000 > del) {
            nanotime = System.nanoTime();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set boolean variable see depend on ...
     *
     * @return true if enemy see player
     */
    private boolean controlPlayerCollider() {

        for (Block block : GamePanel.blocks) {
            if (eye.intersects(block.getRectangle())) {
//                System.out.println(name + " see block");
//                eye.setLine(this.position,block.getCenterPosition());
                if (name.equals("Up") | name.equals("Down")) {
                    eye.setLine(this.position.getX(), this.position.getY(), endPosition.getX(), block.getCenterPosition().getY());
                } else {
                    eye.setLine(this.position.getX(), this.position.getY(), block.getCenterPosition().getX(), endPosition.getY());
                }
            }
        }

        if (this.eye.intersects(GamePanel.player.getRectangle())) {
//            System.out.println(name +" I see you");
            this.eye.setLine(this.position, GamePanel.player.getCenterPosition());
            setSee(true);
            return true;
        }
        setSee(false);
        return false;
    }

    void draw(Graphics2D g) {
        if (isSee()) g.setColor(Color.RED);
        else g.setColor(Color.WHITE);
        g.draw(this.eye);
    }

    public boolean isSee() {
        return see;
    }

    public void setSee(boolean see) {
        this.see = see;
    }
}
