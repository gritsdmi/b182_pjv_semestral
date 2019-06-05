package UnitTests.GameTests;

import org.junit.Test;
import start.GameObjects.Player;
import start.GamePanel;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    Player player = new Player(new GamePanel(new JFrame()), 300, 300, 1);

    @Test
    public void test_player_takeDamage() {
        player.setHealth(100);
        assertEquals(100, player.getHealth());
        player.takeDamage(50);
        assertEquals(50, player.getHealth());

    }


    @Test
    public void test_player_die() {

        player.setHealth(100);
        assertEquals(true, player.isAlive());
        player.setHealth(0);
        assertEquals(false, player.isAlive());

    }
}