package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Enemy;
import start.GameObjects.Player;
import start.GamePanel;
import start.Logic.SpawnPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IntegrationTest_EnemySeePlayer {//enemy attack player

//    private GamePanel gp = new GamePanel(new JFrame());
//
//    private Bullet bullet = new Bullet(100,200,new Point(125,125),1);

    @Test
    public void test_enemySeePlayer() {

        GamePanel gp = new GamePanel(new JFrame());
        Player player = new Player(gp, 100, 100, 1);
        gp.player = player;

        ////////////////////
        SpawnPoint sp = new SpawnPoint(gp);


        Enemy enemy1 = new Enemy(new Point(200, 100));
        Enemy enemy2 = new Enemy(new Point(100, 200));
        Enemy enemy3 = new Enemy(new Point(200, 300));
        Enemy enemy4 = new Enemy(new Point(300, 200));

        ArrayList<Enemy> en = new ArrayList<>();
        en.add(enemy1);
        en.add(enemy2);
        en.add(enemy3);
        en.add(enemy4);

        sp.setEnemies(en);

        GamePanel.enemySpawns.add(sp);
//        System.out.println(GamePanel.getEnemySpawns().size());// spawna
        gp.GameUpdate();
        gp.GameUpdate();
        gp.GameUpdate();
        gp.GameUpdate();
        gp.GameUpdate();
//        System.out.println(gp.bullets.size());
//        System.out.println(GamePanel.enemySpawns.get(2).getEnemies().size());//4 enemy

        assertEquals(0, enemy1.getMood());
        assertEquals(0, enemy2.getMood());
        assertEquals(0, enemy3.getMood());
        assertEquals(0, enemy4.getMood());
//
        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(0).getMood());
        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(1).getMood());
        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(2).getMood());
        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(3).getMood());


        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(3).getPosition());
        assertEquals(0, GamePanel.getEnemySpawns().get(2).getEnemies().get(3).getMood());
        gp.GameUpdate();


//        enemy.update();
//        assertEquals(bullet,GamePanel.bullets.get(0));
    }


}
