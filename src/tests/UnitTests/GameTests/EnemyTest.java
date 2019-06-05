package UnitTests.GameTests;

import org.junit.Test;
import start.GameObjects.Enemy;

import static org.junit.Assert.assertEquals;

public class EnemyTest {
    Enemy enemy = new Enemy();

    @Test
    public void test_enemy_takeDamage() {
        enemy.setHealth(100);
        assertEquals(100, enemy.getHealth());
        enemy.takeDamage(50);
        assertEquals(50, enemy.getHealth());

    }


    @Test
    public void test_enemy_die() {

        enemy.setHealth(100);
        assertEquals(true, enemy.isAlive());
        enemy.setHealth(0);
        assertEquals(false, enemy.isAlive());

    }
}