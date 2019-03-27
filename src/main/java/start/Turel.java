package start;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Turel {
    private double x;
    private double y;
    private double angle;
    private Point direction;

    /**
     * угол между точками
     * Math.atan2(direction.y - this.yPos, direction.x - this.xPos)
     */


    public Turel(double x, double y) {
        this.x = x;
        this.y = y;
        this.direction = new Point(0, 0);
    }

    public void update() {
        x = Player.x;
        y = Player.y;
    }

    public void draw(Graphics2D g) {

//        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(Player.rotation), 25, 50);
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(Math.toDegrees(Math.atan2(direction.y - (this.y + 25), direction.x - (this.x + 25))) + 90), 25, 50);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
//        System.out.println("at" + at);

// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(GamePanel.TankTowerPicture, null), (int) x, (int) y - 25, null);
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }



}
