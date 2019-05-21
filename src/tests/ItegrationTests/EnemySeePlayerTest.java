package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Enemy;
import start.GameObjects.Player;
import start.GamePanel;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.assertEquals;

public class EnemySeePlayerTest {//enemy attack player

//    private GamePanel gp = new GamePanel(new JFrame());
//
//    private Bullet bullet = new Bullet(100,200,new Point(125,125),1);

    @Test
    public void test_enemySeePlayer() {

        GamePanel gp = new GamePanel(new JFrame());
        Player player = new Player(gp, 100, 100, 1);
        gp.player = player;

        Enemy enemy = new Enemy(new Point(100, 200), gp);
        enemy.setMood(0);
        assertEquals(0, enemy.getMood());
        enemy.upC();
        assertEquals(1, enemy.getMood());

    }


}
