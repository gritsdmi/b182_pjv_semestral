package UnitTests.GameTests;

import org.junit.Test;
import start.GameObjects.Block;

import static org.junit.Assert.assertEquals;

public class BlockTest {
    Block block = new Block(1, 100, 100);

    @Test
    public void test_player_takeDamage() {
        block.setHealth(100);
        assertEquals(100, block.getHealth());
        block.takeDamage(50);
        assertEquals(50, block.getHealth());

    }


    @Test
    public void test_player_die() {

        block.setHealth(100);
        assertEquals(true, block.isAlive());
        block.setHealth(0);
        assertEquals(false, block.isAlive());

    }
}