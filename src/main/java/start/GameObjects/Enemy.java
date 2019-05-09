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
    private int lastSeenSmer;
    private int smer;//1-up 2-right 3-left 4-down
    private Point actualPosition; //enemy's center position
    private ArrayList<Eye> eyes;
    private ArrayList<Eye> movingEyes;
    private int mood;//0 - normal; 1 - fury; 2 - go to the last seen point
    private int eyesOffset = 20;


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

        mood = 0;
    }

    //TODO may be override
    boolean roughlyEqual(Point pos1, Point pos2) {
        try {

            if (Math.abs(pos1.getX() - pos2.getX()) < 15 && Math.abs(pos1.getY() - pos2.getY()) < 15) {
                System.out.println("Equal");
                mood = 1;
                return true;
            }
        } catch (NullPointerException e) {
            System.out.println("Null pointer equal");
        }
        return false;
    }

    public void update() {
        if (isAlive) {
            if (mood == 1 && control50()) {//fury mode && control 50 == true
                smer = 0;
                System.out.println("Fury && 50 in update. smer = 0" + "Pos " + this.getPosition());
                boolean temp = false;
                for (Eye eye : eyes) {
                    temp = temp || eye.isSeeHim();
//                    System.out.println(eye.isSeeHim());
                }
                if (!temp) newRandomMovingDirection(); //
            } else if (mood == 2) {//go to last point
                if (control50()) smer = lastSeenSmer;/////////////////////////////////
                //если позиции примерно равно roughly equal
                if (roughlyEqual(getPosition(), lastSeen)) {
                    align();
                    if (control50())
                        mood = 0;
                }
            }

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

            /**Starts of eyes*/
//            Point upStart = new Point(0,0);
//            Point rightStart = new Point(0,0);
//            Point leftStart = new Point(0,0);
//            Point downStart = new Point(0,0);


            /**ends of eyes*/
            //central up line
//            upStart.setLocation(this.actualPosition.getX(),this.actualPosition.getY() - eyesOffset);
            Point upEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - fireDistance);
            Point movUp = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_MOVING_OFFSET);

            //right line
//            rightStart.setLocation(this.actualPosition.getX()+eyesOffset,this.actualPosition.getY());
            Point rightEnd = new Point((int) this.actualPosition.getX() + fireDistance, (int) this.actualPosition.getY());
            Point movRight = new Point((int) this.actualPosition.getX() + ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //left line
//            leftStart.setLocation(this.actualPosition.getX()-eyesOffset,this.actualPosition.getY());
            Point leftEnd = new Point((int) this.actualPosition.getX() - fireDistance, (int) this.actualPosition.getY());
            Point movLeft = new Point((int) this.actualPosition.getX() - ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //central down line
//            downStart.setLocation(this.actualPosition.getX(),this.actualPosition.getY() + eyesOffset);
            Point downEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + fireDistance);
            Point movDown = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_MOVING_OFFSET);


            switch (smer) {
                case 1: //up
//                    upStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() - 20);
//                    rightStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() - 20);
//                    leftStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() - 20);
//                    downStart.setLocation(this.actualPosition);

                    upEnd.setLocation(upEnd.getX(), upEnd.getY() - 20);
                    rightEnd.setLocation(rightEnd.getX(), rightEnd.getY() - 20);
                    leftEnd.setLocation(leftEnd.getX(), leftEnd.getY() - 20);


                    downEnd.setLocation(this.actualPosition);
                    movDown.setLocation(this.actualPosition);
                    break;
                case 2: //right
//                    upStart.setLocation(this.actualPosition.getX()+20, this.actualPosition.getY());
//                    rightStart.setLocation(this.actualPosition.getX()+20, this.actualPosition.getY());
//                    leftStart.setLocation(this.actualPosition);
//                    downStart.setLocation(this.actualPosition.getX()+20,this.actualPosition.getY());

                    upEnd.setLocation(upEnd.getX() + 20, upEnd.getY());
                    rightEnd.setLocation(rightEnd.getX() + 20, rightEnd.getY());
                    downEnd.setLocation(downEnd.getX() + 20, downEnd.getY());

                    leftEnd.setLocation(this.actualPosition);
                    movLeft.setLocation(this.actualPosition);
                    break;
                case 3: //left

//                    upStart.setLocation(this.actualPosition.getX()-20, this.actualPosition.getY());
//                    rightStart.setLocation(this.actualPosition);
//                    leftStart.setLocation(this.actualPosition.getX()-20, this.actualPosition.getY());
//                    downStart.setLocation(this.actualPosition.getX()-20,this.actualPosition.getY());

                    upEnd.setLocation(upEnd.getX() - 20, upEnd.getY());
                    leftEnd.setLocation(leftEnd.getX() - 20, leftEnd.getY());
                    downEnd.setLocation(downEnd.getX() - 20, downEnd.getY());


                    rightEnd.setLocation(this.actualPosition);
                    movRight.setLocation(this.actualPosition);
                    break;
                case 4: //down
//                    upStart.setLocation(this.actualPosition);
//                    rightStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() + 20);
//                    leftStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() + 20);
//                    downStart.setLocation(this.actualPosition.getX(), this.actualPosition.getY() + 20);

                    rightEnd.setLocation(rightEnd.getX(), rightEnd.getY() + 20);
                    leftEnd.setLocation(leftEnd.getX(), leftEnd.getY() + 20);
                    downEnd.setLocation(downEnd.getX(), downEnd.getY() + 20);


                    upEnd.setLocation(this.actualPosition);
                    movUp.setLocation(this.actualPosition);
                    break;
            }

//            ArrayList<Point> startPointsSearchingEyes = new ArrayList<>();
//            startPointsSearchingEyes.add(upStart);
//            startPointsSearchingEyes.add(rightStart);
//            startPointsSearchingEyes.add(leftStart);
//            startPointsSearchingEyes.add(downStart);

            ArrayList<Point> endPointsSearchingEyes = new ArrayList<>();
            endPointsSearchingEyes.add(upEnd);
            endPointsSearchingEyes.add(rightEnd);
            endPointsSearchingEyes.add(leftEnd);
            endPointsSearchingEyes.add(downEnd);

            ArrayList<Point> movingEyesList = new ArrayList<>();
            movingEyesList.add(movUp);
            movingEyesList.add(movRight);
            movingEyesList.add(movLeft);
            movingEyesList.add(movDown);

            for (int i = 0; i < eyes.size(); i++) {
//                eyes.get(i).update(startPointsSearchingEyes.get(i), endPointsSearchingEyes.get(i));
                eyes.get(i).update(this.actualPosition, endPointsSearchingEyes.get(i));
                movingEyes.get(i).update(this.actualPosition, movingEyesList.get(i));
            }

            align();

            if (control50()) {//every 50th pixel
                if (mood == 0) {// normal mode
                    newRandomMovingDirection();
                }
            }
        }


    }

    /**
     * Function compute player's position according to 50
     *
     * @return Point player's position
     */
    private Point computePlayerPosition() {

        return new Point(0, 0);
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
     *
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

    //todo брать в расчет позицию других enemies при генерировании нового направления
    /**
     * Function randomly choose new moving direction
     * New direction depend on free space around
     * Enemy can't moving backward (only in case when another ways are impossible)
     *
     * @return int newDirection
     */
    private int newRandomMovingDirection() {
//        System.out.println("new Random dir");
        int newDirection;
        ArrayList<Integer> possibleDirections = new ArrayList<>();
        for (Eye eye : movingEyes) {
            if (!eye.isSee()) {
                switch (eye.getName()) {
                    case "MoveUp":
                        possibleDirections.add(1);
                        break;
                    case "MoveRight":
                        possibleDirections.add(2);
                        break;
                    case "MoveLeft":
                        possibleDirections.add(3);
                        break;
                    case "MoveDown":
                        possibleDirections.add(4);
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
        smer = newDirection;
        return newDirection;
    }

    private boolean control50() {
        return xPosition % 50 == 0 && yPosition % 50 == 0;//тут былр написано "&"
    }

    private int createOpositeSmer(int smerr) {
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

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getMood() {
        return mood;
    }

    public void setSmer(int smer) {
        this.smer = smer;
        System.out.println("Smer " + smer + " setted");
    }

    public void setLastSeenPoint(Point lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Point getLastSeen() {
        return lastSeen;
    }

    public Point getActualPosition() {
        return actualPosition;
    }

    public Point getPosition() {
        return new Point(xPosition, yPosition);
    }

    public int getLastSeenSmer() {
        return lastSeenSmer;
    }

    public void setLastSeenSmer(int lastSeenSmer) {
        this.lastSeenSmer = lastSeenSmer;
    }

    public Rectangle getRectangle() {
        return new Rectangle(xPosition, yPosition, 50, 50);
    }


}

class Eye implements Constants {

    private Enemy enemy;
    private boolean see; // use for blocks
    private boolean seeHim; //use for player
    private Point position; //start position
    private Point endPosition; //end position
    private String name;
    private Line2D eye;
    private int type;//0 = seeing 1 = moving

    private double nanotime = System.nanoTime();


    public Eye(Enemy enemy, Point startPosition, String name, int type) {
        this.enemy = enemy;
        this.type = type;
        this.position = startPosition;
        this.endPosition = new Point(startPosition);
        this.name = name;
        this.see = false;
        this.seeHim = false;
        this.eye = new Line2D.Float(this.position, this.endPosition);
    }

    void update(Point pos, Point endPos) {
        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
//        if(type == 0) changeOffsetAccordingSmer();
        this.eye.setLine(this.position, this.endPosition);

        if (controlPlayerCollider() && computeShootingDelay(ENEMY_SHOOTING_DELAY)) {
            GamePanel.bullets.add(new Bullet(position.getX(), position.getY(), GamePanel.player.getCenterPosition(), 1));
        }
    }

    //don't used
    private void changeOffsetAccordingSmer() {
        switch (this.enemy.getSmer()) {
            case 1://up
                this.position.setLocation(this.position.getX(), this.position.getY() - 25);
                this.endPosition.setLocation(this.position.getX(), this.position.getY() - 25);
                break;
            case 2://right
                this.position.setLocation(this.position.getX() + 25, this.position.getY());
                this.endPosition.setLocation(this.position.getX() + 25, this.position.getY());
                break;
            case 3://left
                this.position.setLocation(this.position.getX() - 25, this.position.getY());
                this.endPosition.setLocation(this.position.getX() - 25, this.position.getY());
                break;
            case 4://down
                this.position.setLocation(this.position.getX(), this.position.getY() + 25);
                this.endPosition.setLocation(this.position.getX(), this.position.getY() + 25);
                break;
            case 0:
                break;
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
     * Set boolean variable see true if Line2D eye
     * intersects Block's Rectangle or another Enemy's Rectangle
     *
     * Set boolean variable seeHim true if Line2D eye
     * intersects player's Rectangle
     *
     * @see Enemy
     * @see Block
     * @see Line2D
     * @see Rectangle
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

        for (Enemy en : GamePanel.enemies) {
            if (eye.intersects(en.getRectangle()) && !en.equals(enemy)) {
                if (type == 0) {
                    if (name.equals("Up") | name.equals("Down")) {
                        eye.setLine(this.position.getX(), this.position.getY(), endPosition.getX(), en.getActualPosition().getY());
                    } else {
                        eye.setLine(this.position.getX(), this.position.getY(), en.getActualPosition().getX(), endPosition.getY());
                    }
                } else if (type == 1) {
                    setSee(true);
                    return false;
                }
            }
        }

        setSee(false);

        if (type == 0) {//seeing eyes
            if (this.eye.intersects(GamePanel.player.getSmallRectangle())) {//игрока видно
                enemy.setMood(1);//set fury mode
                if (!GamePanel.player.isMoving()) {
                    enemy.setLastSeenPoint(GamePanel.player.getPosition());
                    System.out.println("last point setted" + enemy.getLastSeen() + "player stay");
                    enemy.setLastSeenSmer(this.nameToSmer());
                }


                setSeeHim(true);
                return true;

            } else {//игрока не видно этим глазом &&
                if (this.isSeeHim()) {//если он только что видел
                    if (enemy.getMood() == 1) {//если только что был fury//потерял игрока из виду
                        System.out.println("dont see && fury " + name);
                        enemy.setMood(2);// last seen mode on
//                        enemy.setLastSeenPoint(GamePanel.player.getPosition());
//                        enemy.setLastSeenSmer(this.nameToSmer());
                        System.out.println("last seen mode on" + " last point" + enemy.getLastSeen());
                    } else if (enemy.getMood() == 2) {//если enemy ищет меня
                        //todo переключение на normal mood (0)
                    }

                }
                setSeeHim(false);
            }
        }

        return false;
    }

    void draw(Graphics2D g) {
        if (type == 0) {
            if (isSeeHim()) g.setColor(Color.RED);
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

    public boolean getSee() {
        return see;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return type;
    }

    public int nameToSmer() {
        switch (name) {
            case "Up":
                return 1;
            case "Right":
                return 2;
            case "Left":
                return 3;
            case "Down":
                return 4;
        }
        return 0;
    }

    public boolean isSeeHim() {
        return seeHim;
    }

    public void setSeeHim(boolean seeHim) {
        this.seeHim = seeHim;
    }
}
