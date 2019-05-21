package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Drop;
import start.GameObjects.Player;
import start.GamePanel;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class ShieldBonusTest {

    @Test
    public void shield_bonus_test() {
        Player player = new Player(new GamePanel(new JFrame()), 300, 300, 1);
        Drop bonusShield = new Drop(100, 100, 3, player);

        player.setHealth(100);
        assertEquals(100, player.getHealth());
        player.takeBounus(bonusShield);
        player.takeDamage(50);
        assertEquals(100, player.getHealth());


    }
}
