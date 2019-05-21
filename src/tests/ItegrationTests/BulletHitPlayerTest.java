package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Bullet;
import start.GameObjects.Player;
import start.GamePanel;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class BulletHitPlayerTest {

    GamePanel gp = new GamePanel(new JFrame());
    Player player = new Player(gp, 100, 100, 1);
    Bullet bullet = new Bullet(100, 200, new Point(125, 125), 1, gp);

    @Test
    public void bulletHitPlayerTest() {
        gp.player = player;
        player.setHealth(100);

        while (true) {
            if (!bullet.up()) {
//                System.out.println(bullet.getxPosition() + " " + bullet.getyPosition());
            } else break;
        }
        assertEquals(90, player.getHealth());
    }
}
