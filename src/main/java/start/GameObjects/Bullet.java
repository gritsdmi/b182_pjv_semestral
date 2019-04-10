package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Bullet implements Constants {
    //Fields
    private double x;
    private double y;
    private int speed = BULLET_SPEED;

    private Point dir;
    private double dx;
    private double dy;
    private double angle;
    private final double scale = 1.0;

    private static BufferedImage imgBuff = GamePanel.BulletPicture;
    private AffineTransformOp op;
    private BufferedImage scaled;
    private boolean isAlive;
    private int damage;

    //Constructor
    public Bullet(double x, double y, Point direction) {
        this.x = x;
        this.y = y;
        this.dir = direction;

        this.angle = Math.toRadians(Math.toDegrees(Math.atan2(dir.y - this.y, dir.x - this.x)));
        this.dy = Math.sin(angle);
        this.dx = Math.cos(angle);
        this.isAlive = true;
        damage = 1;

        //creating and preparing image to next transformation, scaling if it needed
        scaled = new BufferedImage(imgBuff.getWidth() * (int) (scale), imgBuff.getHeight() * (int) (scale), BufferedImage.TYPE_INT_ARGB);

        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        scaled = scaleOp.filter(imgBuff, scaled);


        //rotate's image transformation
        at = AffineTransform.getRotateInstance(
                Math.toRadians(Math.toDegrees(Math.atan2(direction.y - (this.y), direction.x - (this.x))) + 90),
                scaled.getWidth() / 2, scaled.getHeight() / 2);
        this.op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
    }


    //TODO подправить проверку колайдера(проверять не [0,0] пиксель пули)
    private boolean controlCollider() {//четвертый пиксель пули
        for (Block bl : GamePanel.blocks) {
            if (bl.isAlive()) {
                if (bl.getX() <= this.x && (bl.getX() + bl.getWidth()) >= this.x) {
                    if (bl.getY() <= this.y + 4 && (bl.getY() + bl.getHeight()) >= this.y + 4) {
                        bl.hit(this);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void controlAlive() {
        if (y < 0 || x < 0 || y > PANEL_HEIGHT || x > PANEL_WIDTH) this.isAlive = false;
    }

    public void update() {
        if (isAlive) {
            y += dy * speed;
            x += dx * speed;
            if (controlCollider()) this.isAlive = false;
            controlAlive();
        }

    }

    public void draw(Graphics2D g) {

        if (isAlive) {
            g.drawImage(op.filter(scaled, null), (int) (x - scaled.getWidth() / 2), (int) (y - scaled.getHeight() / 2), null);
        }

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getDamage() {
        return damage;
    }
}
