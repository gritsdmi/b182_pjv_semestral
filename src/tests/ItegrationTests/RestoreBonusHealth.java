package ItegrationTests;

import org.junit.Test;
import start.GameObjects.Drop;
import start.GameObjects.Player;
import start.GamePanel;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class RestoreBonusHealth {

    @Test
    public void restore_health_bonus_and_bomb_bonus_test() {
        Player player = new Player(new GamePanel(new JFrame()), 300, 300, 1);
        Drop bonusRestore = new Drop(100, 100, 2, player);
        Drop bonusBomb = new Drop(100, 100, 0, player);
        player.setHealth(100);
        assertEquals(100, player.getHealth());
        player.takeBounus(bonusBomb);
        assertEquals(70, player.getHealth());
        player.takeBounus(bonusRestore);
        assertEquals(100, player.getHealth());


    }
}
