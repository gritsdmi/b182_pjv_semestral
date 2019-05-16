package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;
import start.Logic.SpawnPoint;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Bullet implements Constants, Serializable {
    //Fields
    private double xPosition;
    private double yPosition;
    private int speed = BULLET_SPEED;

    private Point dir;
    private double dx;
    private double dy;
    private double angle;
    private final double scale = 1.0;

    //    private static BufferedImage imgBuff = GamePanel.BulletPicture;
//    private AffineTransformOp op;
    private BufferedImage scaled;
    private boolean isAlive;
    private int damage;
    private int autor;//0 = player; 1 = enemy 2 = another player

    //Constructor
    public Bullet(double x, double y, Point direction, int autor) {

        this.xPosition = x;
        this.yPosition = y;
        this.dir = direction;

        this.angle = Math.toRadians(Math.toDegrees(Math.atan2(dir.y - yPosition, dir.x - xPosition)));
        this.dy = Math.sin(angle);
        this.dx = Math.cos(angle);
        this.isAlive = true;
        damage = 10;

        //creating and preparing image to next transformation, scaling if it needed
//        scaled = new BufferedImage(imgBuff.getWidth() * (int) (scale), imgBuff.getHeight() * (int) (scale), BufferedImage.TYPE_INT_ARGB);
/*
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaled = scaleOp.filter(imgBuff, scaled);


        //rotate's image transformation
        at = AffineTransform.getRotateInstance(
                Math.toRadians(Math.toDegrees(Math.atan2(direction.y - (this.yPosition), direction.x - (this.xPosition))) + 90),
                scaled.getWidth() / 2, scaled.getHeight() / 2);
        this.op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

 */
        this.autor = autor;
    }

    private boolean controlCollider() {//четвертый пиксель пули
        for (Block bl : GamePanel.blocks) {
            if (bl.isAlive()) {
                if (bl.getxPosition() <= this.xPosition && (bl.getxPosition() + bl.getWidth()) >= this.xPosition) {
                    if ((bl.getyPosition() <= this.yPosition) && ((bl.getyPosition() + bl.getHeight()) >= this.yPosition)) {
                        bl.hit(this);

                        return true;
                    }
                }
            }
        }

        if (autor == 0) {//player autor

            for (SpawnPoint sp : GamePanel.getEnemySpawns()) {

                for (Enemy enemy : sp.getEnemies()) {
                    if (enemy.isAlive()) {
                        if (enemy.getxPosition() <= this.xPosition && (enemy.getxPosition() + enemy.getWight()) >= this.xPosition) {
                            if (enemy.getyPosition() <= this.yPosition && (enemy.getyPosition() + enemy.getHeight()) >= this.yPosition) {
                                enemy.hit(this);
                                return true;
                            }
                        }
                    }
                }
            }
        } else if (autor == 1) {//enemy autor
            if (GamePanel.base.getRectangle().getX() <= this.xPosition && (GamePanel.base.getRectangle().getX() + BASE_SIZE_WIDTH) >= this.xPosition) {
                if (GamePanel.base.getRectangle().getY() <= this.yPosition && (GamePanel.base.getRectangle().getY() + BASE_SIZE_HEIGHT) >= this.yPosition) {
                    GamePanel.base.hit(this.getDamage());
                    return true;
                }
            }
            if (GamePanel.player.getRectangle().getX() <= this.xPosition && (GamePanel.player.getRectangle().getX() + PLAYER_SIZE_WIDTH) >= this.xPosition) {
                if (GamePanel.player.getRectangle().getY() <= this.yPosition && (GamePanel.player.getRectangle().getY() + PLAYER_SIZE_HEIGHT) >= this.yPosition) {
                    GamePanel.player.hit(this.getDamage());
                    return true;
                }
            }
        }

        //if multiplayer mode on
        if (GamePanel.isServer || GamePanel.isClient) {
            if (GamePanel.isServer) {
                if (autor == 2) {//eсли это пулька выпущена клиентом
                    if (GamePanel.player.getRectangle().getX() <= this.xPosition && (GamePanel.player.getRectangle().getX() + PLAYER_SIZE_WIDTH) >= this.xPosition) {
                        if (GamePanel.player.getRectangle().getY() <= this.yPosition && (GamePanel.player.getRectangle().getY() + PLAYER_SIZE_HEIGHT) >= this.yPosition) {
                            GamePanel.player.hit(this.getDamage());
                            return true;
                        }
                    }
                } else if (autor == 0) {//если эту пульку выпустил сервер
                    if (GamePanel.player2.getRectangle().getX() <= this.xPosition && (GamePanel.player2.getRectangle().getX() + PLAYER_SIZE_WIDTH) >= this.xPosition) {
                        if (GamePanel.player2.getRectangle().getY() <= this.yPosition && (GamePanel.player2.getRectangle().getY() + PLAYER_SIZE_HEIGHT) >= this.yPosition) {
                            GamePanel.player2.hit(this.getDamage());
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private void controlAlive() {
        if (yPosition < 0 || xPosition < 0 || yPosition > PANEL_HEIGHT || xPosition > PANEL_WIDTH) this.isAlive = false;
    }

    public void update() {
        if (isAlive) {
            yPosition += dy * speed;
            xPosition += dx * speed;

            if (controlCollider()) this.isAlive = false;
            controlAlive();
        }

    }

    public void draw(Graphics2D g) {

        if (isAlive) {
//            g.drawImage(op.filter(scaled, null),
//                    (int) (xPosition - scaled.getWidth() / 2), (int) (yPosition - scaled.getHeight() / 2), null);\
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(3));
            g.fillOval((int) xPosition, (int) yPosition, 3, 3);
            g.drawOval((int) xPosition, (int) yPosition, 3, 3);
        }

    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getDamage() {
        return damage;
    }
}
