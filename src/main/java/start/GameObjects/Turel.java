package start.GameObjects;

import start.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Turel extends GameObject {

    private double xPosition;
    private double yPosition;
    private double angle;
    private Point direction;

    /**
     * angle between 2 points
     * Math.atan2(direction.y - this.yPos, direction.x - this.xPos)
     */


    public Turel(double x, double y) {
        this.xPosition = x;
        this.yPosition = y;
        this.direction = new Point(0, 0);
    }

    public void update() {
        xPosition = Player.x;
        yPosition = Player.y;
    }

    public void draw(Graphics2D g) {

        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(Math.toDegrees(
                Math.atan2(direction.y - (this.yPosition + 25), direction.x - (this.xPosition + 25))) + 90), 25, 50);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(GamePanel.TankTowerPicture, null), (int) xPosition, (int) yPosition - 25, null);
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }



}
