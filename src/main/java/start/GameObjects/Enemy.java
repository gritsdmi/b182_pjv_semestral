package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;
import start.Logic.SpawnPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Enemy implements Constants, Serializable {
    //Fields
    private int xPosition;
    private int yPosition;
    private int health = ENEMY_HEALTH;
    private boolean isAlive;
    private Point lastSeen;
    private int lastSeenSmer;
    private int smer;//1-up 2-right 3-left 4-down
    private Point actualPosition; //enemy's center position
    private ArrayList<Eye> eyes;
    private ArrayList<Eye> movingEyes;
    private int mood;//0 - normal; 1 - fury; 2 - go to the last seen point
    private int wight = 46;
    private int height = 46;
    private double helthBarLenght;
    private double pomuckaProVypocetDelkyBaru;

    private transient BufferedImage tankImg;
    private AffineTransform at;
    private transient AffineTransformOp op;
    private int rotation;
    private static int uniqId = 0;
    private int id;
    private Block tempBlock;

    public Enemy() {
        this.isAlive = true;
    }

    public Enemy(Point startPosition, GamePanel gp) {
        this.xPosition = (int) startPosition.getX();
        this.yPosition = (int) startPosition.getY();
        this.isAlive = true;
        this.smer = 0;

        this.eyes = new ArrayList<>();
        this.movingEyes = new ArrayList<>();

        this.actualPosition = startPosition;
        this.actualPosition.setLocation(this.xPosition + 25, this.yPosition + 25);

        eyes.add(new Eye(this, this.actualPosition, "Up", 0, gp));
        eyes.add(new Eye(this, this.actualPosition, "Right", 0, gp));
        eyes.add(new Eye(this, this.actualPosition, "Left", 0, gp));
        eyes.add(new Eye(this, this.actualPosition, "Down", 0, gp));

//        movingEyes.add(new Eye(this, this.actualPosition, "MoveUp", 1));
//        movingEyes.add(new Eye(this, this.actualPosition, "MoveRight", 1));
//        movingEyes.add(new Eye(this, this.actualPosition, "MoveLeft", 1));
//        movingEyes.add(new Eye(this, this.actualPosition, "MoveDown", 1));
//
        mood = 0;

        try {
            tankImg = ImageIO.read(new File("src/main/resources/Entity/GrayPixelTank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //image fields
        this.rotation = 0;
        rotateTankImage(smer);
        this.id = uniqId++;
        tempBlock = new Block(3, xPosition, yPosition, id);
        GamePanel.blocks.add(tempBlock);
    }


    public Enemy(Point startPosition) {
        this.xPosition = (int) startPosition.getX();
        this.yPosition = (int) startPosition.getY();
        this.isAlive = true;
        this.smer = 0;

        this.eyes = new ArrayList<>();
        this.movingEyes = new ArrayList<>();

        this.actualPosition = startPosition;
        this.actualPosition.setLocation(this.xPosition + 25, this.yPosition + 25);

        eyes.add(new Eye(this, this.actualPosition, "Up", 0));
        eyes.add(new Eye(this, this.actualPosition, "Right", 0));
        eyes.add(new Eye(this, this.actualPosition, "Left", 0));
        eyes.add(new Eye(this, this.actualPosition, "Down", 0));

        movingEyes.add(new Eye(this, this.actualPosition, "MoveUp", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveRight", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveLeft", 1));
        movingEyes.add(new Eye(this, this.actualPosition, "MoveDown", 1));

        mood = 0;

        try {
            tankImg = ImageIO.read(new File("src/main/resources/Entity/GrayPixelTank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //image fields
        this.rotation = 0;
        rotateTankImage(smer);
        this.id = uniqId++;
        tempBlock = new Block(3, xPosition, yPosition, id);
        GamePanel.blocks.add(tempBlock);
    }

    /**
     * Method create new AffineTransform according to actual direction
     *
     * @see AffineTransform
     * @see AffineTransformOp
     */
    void rotateTankImage(int ssmer) {
        directionToRotation(ssmer);
        at = AffineTransform.getRotateInstance(Math.toRadians(rotation), 25, 25);
        op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

    }


    /**
     * Method compute direction to rotation according to actual direction
     */
    private void directionToRotation(int ssmer) {
        switch (ssmer) {
            case 1:
                rotation = 0;
                break;
            case 2:
                rotation = 90;
                break;
            case 3:
                rotation = 270;
                break;
            case 4:
                rotation = 180;
                break;
            default:
                rotation = rotation;
                break;
        }
    }


    //TODO may be override
    boolean roughlyEqual(Point pos1, Point pos2) {
        try {

            if (Math.abs(pos1.getX() - pos2.getX()) < 15 && Math.abs(pos1.getY() - pos2.getY()) < 15) {
                mood = 1;
                return true;
            }
        } catch (NullPointerException e) {
//            System.err.println("Null pointer equal");
        }
        return false;
    }

    public boolean isEnemyOnMap() {
        if (xPosition > PANEL_WIDTH || yPosition > PANEL_HEIGHT || xPosition < 0 || yPosition < 0) return false;
        return true;
    }

    public void upC() {

        //central up line
        Point upEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_FIRE_DISTANCE);

        //right line
        Point rightEnd = new Point((int) this.actualPosition.getX() + ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());


        //left line
        Point leftEnd = new Point((int) this.actualPosition.getX() - ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());


        //central down line
        Point downEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_FIRE_DISTANCE);

        ArrayList<Point> endPointsSearchingEyes = new ArrayList<>();
        endPointsSearchingEyes.add(upEnd);
        endPointsSearchingEyes.add(rightEnd);
        endPointsSearchingEyes.add(leftEnd);
        endPointsSearchingEyes.add(downEnd);

        for (int i = 0; i < eyes.size(); i++) {
//                eyes.get(i).update(startPointsSearchingEyes.get(i), endPointsSearchingEyes.get(i));
            eyes.get(i).cc(this.actualPosition, endPointsSearchingEyes.get(i));
//            eyes.get(i).cB(this.actualPosition, endPointsSearchingEyes.get(i));
//            movingEyes.get(i).update(this.actualPosition, movingEyesList.get(i));
        }
    }

    public void upb() {

        //central up line
        Point upEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_FIRE_DISTANCE);

        //right line
        Point rightEnd = new Point((int) this.actualPosition.getX() + ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());


        //left line
        Point leftEnd = new Point((int) this.actualPosition.getX() - ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());


        //central down line
        Point downEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_FIRE_DISTANCE);

        ArrayList<Point> endPointsSearchingEyes = new ArrayList<>();
        endPointsSearchingEyes.add(upEnd);
        endPointsSearchingEyes.add(rightEnd);
        endPointsSearchingEyes.add(leftEnd);
        endPointsSearchingEyes.add(downEnd);

        for (int i = 0; i < eyes.size(); i++) {
//                eyes.get(i).update(startPointsSearchingEyes.get(i), endPointsSearchingEyes.get(i));
//            eyes.get(i).cc(this.actualPosition, endPointsSearchingEyes.get(i));
            eyes.get(i).cB(this.actualPosition, endPointsSearchingEyes.get(i));
//            movingEyes.get(i).update(this.actualPosition, movingEyesList.get(i));
        }
    }

    public void update() {
        if (isAlive) {
            pomuckaProVypocetDelkyBaru = ENEMY_HEALTH;
            helthBarLenght = 48 / (pomuckaProVypocetDelkyBaru / health);

            if (mood == 1 && control50()) {//fury mode && control 50 == true
                smer = 0;
                boolean temp = false;
                for (Eye eye : eyes) {
                    temp = temp || eye.isSeeHim();
                }
                if (!temp) newRandomMovingDirection(); //
            } else if (mood == 2) {//go to last point
                boolean temp = false;
                for (Eye eye : eyes) {
                    temp = temp || eye.isSeeAnother();
                }
                if (temp) {
                    mood = 0;
                } else if (control50()) smer = lastSeenSmer;
                // TODO override here
                if (roughlyEqual(getPosition(), lastSeen)) {
                    align();
                    if (control50()) mood = 0;
                }
            }

            switch (smer) {
                case 1: //up
                    yPosition -= ENEMY_MOVING_SPEED;
                    break;
                case 2://right
                    xPosition += ENEMY_MOVING_SPEED;
                    break;
                case 3://left
                    xPosition -= ENEMY_MOVING_SPEED;
                    break;
                case 4://down
                    yPosition += ENEMY_MOVING_SPEED;
                    break;
                case 0:
                    break;
            }

            this.actualPosition.setLocation(this.xPosition + 25, this.yPosition + 25);


            /**ends of eyes*/
            //central up line
            Point upEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_FIRE_DISTANCE);
            Point movUp = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() - ENEMY_MOVING_OFFSET);

            //right line
            Point rightEnd = new Point((int) this.actualPosition.getX() + ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());
            Point movRight = new Point((int) this.actualPosition.getX() + ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //left line
            Point leftEnd = new Point((int) this.actualPosition.getX() - ENEMY_FIRE_DISTANCE, (int) this.actualPosition.getY());
            Point movLeft = new Point((int) this.actualPosition.getX() - ENEMY_MOVING_OFFSET, (int) this.actualPosition.getY());


            //central down line
            Point downEnd = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_FIRE_DISTANCE);
            Point movDown = new Point((int) this.actualPosition.getX(), (int) this.actualPosition.getY() + ENEMY_MOVING_OFFSET);


            switch (smer) {
                case 1: //up
                    upEnd.setLocation(upEnd.getX(), upEnd.getY() - 20);
                    rightEnd.setLocation(rightEnd.getX(), rightEnd.getY() - 20);
                    leftEnd.setLocation(leftEnd.getX(), leftEnd.getY() - 20);


                    downEnd.setLocation(this.actualPosition);
                    movDown.setLocation(this.actualPosition);
                    break;
                case 2: //right
                    upEnd.setLocation(upEnd.getX() + 20, upEnd.getY());
                    rightEnd.setLocation(rightEnd.getX() + 20, rightEnd.getY());
                    downEnd.setLocation(downEnd.getX() + 20, downEnd.getY());

                    leftEnd.setLocation(this.actualPosition);
                    movLeft.setLocation(this.actualPosition);
                    break;
                case 3: //left
                    upEnd.setLocation(upEnd.getX() - 20, upEnd.getY());
                    leftEnd.setLocation(leftEnd.getX() - 20, leftEnd.getY());
                    downEnd.setLocation(downEnd.getX() - 20, downEnd.getY());


                    rightEnd.setLocation(this.actualPosition);
                    movRight.setLocation(this.actualPosition);
                    break;
                case 4: //down
                    rightEnd.setLocation(rightEnd.getX(), rightEnd.getY() + 20);
                    leftEnd.setLocation(leftEnd.getX(), leftEnd.getY() + 20);
                    downEnd.setLocation(downEnd.getX(), downEnd.getY() + 20);


                    upEnd.setLocation(this.actualPosition);
                    movUp.setLocation(this.actualPosition);
                    break;
            }

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
                int newdir = 0;
                if (mood == 0) {// normal mode
                    newdir = newRandomMovingDirection();
                    //next point of moving add to busyArray
                    addBusyBlock(newdir);
                } else
                    addBusyBlock(smer);
            }
            rotateTankImage(smer);
        }


    }

    private void addBusyBlock(int newDir) {

        switch (newDir) {
            case 1: //up
                tempBlock.setxPosition(xPosition);
                tempBlock.setyPosition(yPosition - 50);
                break;
            case 2://right
                tempBlock.setxPosition(xPosition + 50);
                tempBlock.setyPosition(yPosition);
                break;
            case 3://left
                tempBlock.setxPosition(xPosition - 50);
                tempBlock.setyPosition(yPosition);
                break;
            case 4://down
                tempBlock.setxPosition(xPosition);
                tempBlock.setyPosition(yPosition + 50);
                break;
            case 0:
                tempBlock.setPosition(this.getPosition());
        }
    }

    //don't use
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
            g.drawImage(op.filter(tankImg, null), xPosition, yPosition,null);
//            for (Eye eye : eyes) eye.draw(g);
//            for (Eye eye : movingEyes) eye.draw(g);
            g.setColor(Color.RED);
            g.fillRect(xPosition + 2, yPosition - 5, (int) helthBarLenght, 6);
        }
    }

    /**
     * Depend on Bullet's type enemy get different damage.
     *
     * @param bul is bullet, which do damage.
     * @see Bullet
     */
    public void hit(Bullet bul) {
        if (isAlive) {
            this.health -= bul.getDamage();
            controlHP();
        }
    }

    private void controlHP() {
        if (health < 1) {
            this.isAlive = false;
            this.tempBlock.setyPosition(0);
            this.tempBlock.setxPosition(0);
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

    /**
     * Function randomly choose new moving direction
     * New direction depend on free space around
     * Enemy can't moving backward (only in case when another ways are impossible)
     *
     * @return int newDirection
     */
    private int newRandomMovingDirection() {
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
        return xPosition % 50 == 0 && yPosition % 50 == 0;
    }

    /**
     * Method compute opposite direction according to input variable.
     *
     * @param dir represents direction to which method creat new opposite direction
     * @return opposite direction
     */
    private int createOpositeSmer(int dir) {
        if (dir == 1) return 4;
        else if (dir == 2) return 3;
        else if (dir == 3) return 2;
        else if (dir == 4) return 1;

        return 0;
    }

    public boolean isAlive() {
        return isAlive;
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

    public int getWight() {
        return wight;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public void setEnemyTankPic(String pathname) {
        try {
            tankImg = ImageIO.read(new File("src/main/resources/Entity/GrayPixelTank.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void takeDamage(int damage) {
        this.health = health - damage;
        controlHP();
    }

    public void setHealth(int hp) {
        this.health = hp;
        if (this.health < 1) isAlive = false;
    }

    public int getHealth() {
        return health;
    }

}

class Eye implements Constants, Serializable {

    private Enemy enemy;
    private boolean see; // use for blocks
    private boolean seeHim; //use for player
    private boolean seeAnother; // use for enemies
    private Point position; //start position
    private Point endPosition; //end position
    private String name;
    private Line2D eye;
    private int type;//0 = seeing 1 = moving
    private GamePanel gp;

    private double nanotime = System.nanoTime();


    public Eye(Enemy enemy, Point startPosition, String name, int type) {
        this.enemy = enemy;
        this.type = type;
        this.position = startPosition;
        this.endPosition = new Point(startPosition);
        this.name = name;
        this.see = false;
        this.seeHim = false;
        this.seeAnother = false;
        this.eye = new Line2D.Float(this.position, this.endPosition);
    }

    public Eye(Enemy enemy, Point startPosition, String name, int type, GamePanel gp) {
        this.enemy = enemy;
        this.type = type;
        this.position = startPosition;
        this.endPosition = new Point(startPosition);
        this.name = name;
        this.see = false;
        this.seeHim = false;
        this.seeAnother = false;
        this.eye = new Line2D.Float(this.position, this.endPosition);
        this.gp = gp;
    }



    void update(Point pos, Point endPos) {
        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
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

    /**
     * Method compute shooting delay.
     *
     * @param del represents delay between shots. Is the constant, described in Constants class.
     * @return true if enemy can shot, otherwise - false.
     * @see Constants
     */
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
     * <p>
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
        //0 - playerSeeing; 1 - moving

        //loop controls if enemy see blocks
        for (Block block : GamePanel.blocks) {
            if ((eye.intersects(block.getRectangle()) && block.getAutor() != enemy.getId())) {
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

        if (type == 1) {
            if (eye.intersects(GamePanel.player.getRectangle())) {
                setSee(true);
                return true;
            }
        }

        //loop controls if enemy see another enemy
        for (SpawnPoint sp : GamePanel.getEnemySpawns()) {
            for (Enemy en : sp.getEnemies()) {
                if (eye.intersects(en.getRectangle()) && !en.equals(enemy)) {
                    if (type == 0) {//player seeing
                        if (name.equals("Up") | name.equals("Down")) {
                            eye.setLine(this.position.getX(), this.position.getY(), endPosition.getX(), en.getActualPosition().getY());
                        } else {
                            eye.setLine(this.position.getX(), this.position.getY(), en.getActualPosition().getX(), endPosition.getY());
                        }
                        seeAnother = true;
                    } else if (type == 1) {//moving
                        setSee(true);
                        seeAnother = true;
                        return false;
                    }
                } else {
                    seeAnother = false;
                }
            }
        }

        setSee(false);

        if (type == 0) {// player seeing eyes
            if (this.eye.intersects(GamePanel.player.getSmallRectangle())) {//player under seeing
                enemy.setMood(1);//set fury mode
                enemy.rotateTankImage(nameToSmer());
                if (!GamePanel.player.isMoving()) {
                    enemy.setLastSeenPoint(GamePanel.player.getPosition());//set point where enemy seen player(equaled on 50pixel)
                    enemy.setLastSeenSmer(this.nameToSmer());
                }


                setSeeHim(true);
                return true;

            } else {
                //player invisible to this eye
                if (this.isSeeHim()) { //if eye saw player right now
                    if (enemy.getMood() == 1) { //if player was fury on me right now
                        enemy.setMood(2);// last seen mode on
                    } else if (enemy.getMood() == 2) {//if enemy searching player
                        //todo turn on normal mood
                    }

                }
                setSeeHim(false);
            }
        }

        return false;
    }

    public void cc(Point pos, Point endPos) {

        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
        this.eye.setLine(this.position, this.endPosition);

        if (type == 0) {// player seeing eyes
            if (this.eye.intersects(gp.player.getSmallRectangle())) {//player under seeing
                enemy.setMood(1);//set fury mode
                enemy.rotateTankImage(nameToSmer());
                setSeeHim(true);
            }
        }
    }

    public void cB(Point pos, Point endPos) {
        this.position.setLocation(pos);
        this.endPosition.setLocation(endPos);
        this.eye.setLine(this.position, this.endPosition);

        if (type == 0) {// player seeing eyes
            if (this.eye.intersects(gp.base.getRectangle())) {//player under seeing
                enemy.setMood(1);//set fury mode
                enemy.rotateTankImage(nameToSmer());
                setSeeHim(true);
                gp.bullets.add(new Bullet(position.getX(), position.getY(), new Point(gp.base.getRectangle().x, gp.base.getRectangle().y), 1));

            }
        }
    }

    void draw(Graphics2D g) {
        if (type == 0) {
            if (isSeeHim()) g.setColor(Color.RED);
            else g.setColor(Color.blue);
            g.draw(this.eye);
        }

        if (type == 1) {
            if (isSee()) g.setColor(Color.RED);
            else g.setColor(Color.green);
            g.draw(this.eye);
        }
    }

    /**
     * Method convert eye's name to int variable
     * which represents direction of eye.
     *
     * @return int converted direction
     */
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

    public boolean isSeeHim() {
        return seeHim;
    }

    public void setSeeHim(boolean seeHim) {
        this.seeHim = seeHim;
    }

    public boolean isSeeAnother() {
        return seeAnother;
    }
}
