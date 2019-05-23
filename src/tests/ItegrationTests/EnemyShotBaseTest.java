package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Base;
import start.GameObjects.Enemy;
import start.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EnemyShotBaseTest {

    @Test
    public void testEnemyShotBase() {
        GamePanel gp = new GamePanel(new JFrame());
        gp.base = new Base(new Point(300, 300), gp);
        Enemy enemy = new Enemy(new Point(300, 500), gp);
//        Bullet bul = new Bullet(new Point(enemy.getActualPosition()),new Point())
        gp.bullets = new ArrayList<>();

        assertEquals(0, gp.bullets.size());

        enemy.setMood(0);
        assertEquals(0, enemy.getMood());

        enemy.upb();
        assertEquals(1, enemy.getMood());


        assertEquals(1, gp.bullets.size());


    }
}
