package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

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
    private Point lastSeen;
    private byte smer;//1-up 2-right 3-left 4-down
    private Point actualPosition; //enemy's center position
    private ArrayList<Eye> eyes;
    private ArrayList<Eye> movingEyes;


    public Enemy(Point startPosition) {
        this.xPosition = (int) startPosition.getX();
        this.yPosition = (int) startPosition.getY();
        color = Color.PINK;
        this.isAlive = true;
        this.smer = 0;

        this.eyes = new ArrayList<>();
        this.movingEyes = new ArrayList<>();

        this.actualPosition = startPosition;
        this.actualPosition.setLocation(this.xPosition + r / 2, this.yPosition + r / 2);

        eyes.add(new Eye(this, this.actualPosition, "Up", 0));
        eyes.add(new Eye(this, this.actualPosition, "Right", 0));
        eyes.add(new Eye(this, this.actualPosition, "Left", 0));
        eyes.add(new Eye(this, this.actualPosition, "Down", 0));

        movingEyes.add(new Eye(this, this.actualPosition, "MoveUp", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveRight", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveLeft", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveDown", 1));

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
            this.actualPosition.setLocation(this.xPosition + 25, this.yPosition + 25);


            //central up line
            Point up = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - fireDistance);
            Point movUp = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_MOVING_OFFSET);

            //right line
            Point right = new Point((int) this.actualPosition.getX() + fireDistance, (int) this.actualPosition.getY());
            Point movRight = new Point((int) this.actualPosition.getX() + ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //left line
            Point left = new Point((int) this.actualPosition.getX() - fireDistance, (int) this.actualPosition.getY());
            Point movLeft = new Point((int) this.actualPosition.getX() - ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //central down line
            Point down = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + fireDistance);
            Point movDown = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_MOVING_OFFSET);


            switch (smer) {
                case 1: //up
                    down.setLocation(this.actualPosition);
                    movDown.setLocation(this.actualPosition);
                    break;
                case 2: //right
                    left.setLocation(this.actualPosition);
                    movLeft.setLocation(this.actualPosition);
                    break;
                case 3: //left
                    right.setLocation(this.actualPosition);
                    movRight.setLocation(this.actualPosition);
                    break;
                case 4: //down
                    up.setLocation(this.actualPosition);
                    movUp.setLocation(this.actualPosition);
                    break;
            }


            ArrayList<Point> searchingEyesList = new ArrayList<>();
            searchingEyesList.add(up);
            searchingEyesList.add(right);
            searchingEyesList.add(left);
            searchingEyesList.add(down);

            ArrayList<Point> movingEyesList = new ArrayList<>();
            movingEyesList.add(movUp);
            movingEyesList.add(movRight);
            movingEyesList.add(movLeft);
            movingEyesList.add(movDown);

            for (int i = 0; i < eyes.size(); i++) {
                eyes.get(i).update(this.actualPosition, searchingEyesList.get(i));
                movingEyes.get(i).update(this.actualPosition, movingEyesList.get(i));
            }
        }

        align();
        if (control50()) newRandomMovingDirection();
    }

    public void draw(Graphics2D g) {
        if (isAlive) {
            g.setColor(color);
            g.fillOval(this.xPosition + 3, this.yPosition + 3, r, r);
//            for (Eye eye : eyes) eye.draw(g);
//            for (Eye eye : movingEyes) eye.draw(g);
        }
    }

    /**
     * Depend on Bullet's type entity get different damage.
     * @param bul is bullet, which do damage.
     * @see Bullet
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

    // функция на выравнивание позиции на 50 пиксель
    /**
     * Function which align enemy's position to 50th pixel
     * Worked only when Enemy's speed = 2
     */
    private void align() {
        if (xPosition % 50 == 1) xPosition--;
        else if (xPosition % 50 == 49) xPosition++;

        if (yPosition % 50 == 1) yPosition--;
        else if (yPosition % 50 == 49) yPosition++;

    }

    /**
     * Function randomly choose new moving direction
     * New direction depend on free space around
     * Enemy can't moving backward (only in case when another ways are impossible)
     *
     * @return int newDirection
     */
    private int newRandomMovingDirection() {
        int newDirection = 0;
        ArrayList<Byte> possibleDirections = new ArrayList<>();
        for (Eye eye : movingEyes) {
            if (!eye.isSee()) {
                switch (eye.getName()) {
                    case "MoveUp":
                        possibleDirections.add((byte) 1);
                        break;
                    case "MoveRight":
                        possibleDirections.add((byte) 2);
                        break;
                    case "MoveLeft":
                        possibleDirections.add((byte) 3);
                        break;
                    case "MoveDown":
                        possibleDirections.add((byte) 4);
                        break;
                }
            }
        }
        for (int i = 0; i < possibleDirections.size(); i++) {
            if (possibleDirections.get(i) == createOpositeSmer(smer)) {
                possibleDirections.remove(i);
                i--;
            }
        }
        if (possibleDirections.isEmpty()) possibleDirections.add(createOpositeSmer(smer));
        newDirection = possibleDirections.get(new Random().nextInt(possibleDirections.size()));
        smer = (byte) newDirection;
        return newDirection;
    }

    private boolean control50() {
        return xPosition % 50 == 0 & yPosition % 50 == 0;
    }

    private byte createOpositeSmer(byte smerr) {
        if (smerr == 1) return 4;
        else if (smerr == 2) return 3;
        else if (smerr == 3) return 2;
        else if (smerr == 4) return 1;

        return 0;
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

    public int getSmer() {
        return smer;
    }
}

class Eye implements Constants {

    private boolean see;
    private Point position; //start position
    private Point endPosition; //end position
    private String name;
    private Line2D eye;
    private byte type;//0 = seeing 1 = moving

    private double nanotime = System.nanoTime();


    public Eye(Enemy enemy, Point startPosition, String name, int type) {
        this.type = (byte) type;
        this.position = startPosition;
        this.endPosition = new Point(startPosition);
        this.name = name;
        this.see = false;
        this.eye = new Line2D.Float(this.position, this.endPosition);
    }

    void update(Point pos, Point endPos) {
        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
        this.eye.setLine(this.position, this.endPosition);

        if (controlPlayerCollider() && computeShootingDelay(ENEMY_SHOOTING_DELAY)) {
            GamePanel.bullets.add(new Bullet(position.getX(), position.getY(), GamePanel.player.getCenterPosition(), (byte) 1));
        }
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
     * Set boolean variable see depend on type of Eye
     *
     * @return true if enemy see player
     */
    private boolean controlPlayerCollider() {

        for (Block block : GamePanel.blocks) {
            if (eye.intersects(block.getRectangle())) {
                if (type == 0) {
                    if (name.equals("Up") | name.equals("Down")) {
                        eye.setLine(this.position.getX(), this.position.getY(), endPosition.getX(), block.getCenterPosition().getY());
                    } else {
                        eye.setLine(this.position.getX(), this.position.getY(), block.getCenterPosition().getX(), endPosition.getY());
                    }
                } else if (type == 1) {
                    setSee(true);
                    return false;
                }
            }
        }

        if (type == 0) {
            if (this.eye.intersects(GamePanel.player.getRectangle())) {
                this.eye.setLine(this.position, GamePanel.player.getCenterPosition());
                setSee(true);
                return true;
            }
        }

        setSee(false);
        return false;
    }

    void draw(Graphics2D g) {
        if (type == 0) {
            if (isSee()) g.setColor(Color.RED);
            else g.setColor(Color.WHITE);
            g.draw(this.eye);
        }

        if (type == 1) {
            if (isSee()) g.setColor(Color.RED);
            else g.setColor(Color.green);
            g.draw(this.eye);
        }
    }

    public boolean isSee() {
        return see;
    }

    public void setSee(boolean see) {
        this.see = see;
    }

    public String getName() {
        return this.name;
    }

}
