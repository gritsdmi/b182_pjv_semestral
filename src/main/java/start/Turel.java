package start;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Turel {
    private double x;
    private double y;


    public void update() {
        x = Player.x;
        y = Player.y;
    }

    public void draw(Graphics2D g) {

        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(Player.rotation), 5, 5);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

// Drawing the rotated image at the required drawing locations
        g.drawImage(op.filter(GamePanel.TankTowerPicture, null), (int) x, (int) y - 25, null);
    }
}
