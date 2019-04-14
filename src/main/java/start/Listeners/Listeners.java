package start.Listeners;

import start.GameObjects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listeners implements KeyListener {


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            Player.up = true;
            Player.direction = 1;


        }
        if (key == KeyEvent.VK_A) {
            Player.left = true;
            Player.direction = 4;

        }

        if (key == KeyEvent.VK_S) {
            Player.down = true;
            Player.direction = 3;

        }

        if (key == KeyEvent.VK_D) {
            Player.right = true;
            Player.direction = 2;

        }
        if (key == KeyEvent.VK_SPACE) {
            Player.isFiring = true;
        }


    }


    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_W) {
            Player.up = false;
            Player.direction = 0;
        }
        if (key == KeyEvent.VK_A) {
            Player.left = false;
            Player.direction = 0;
        }

        if (key == KeyEvent.VK_S) {
            Player.down = false;
            Player.direction = 0;
        }

        if (key == KeyEvent.VK_D) {
            Player.right = false;
            Player.direction = 0;
        }
        if (key == KeyEvent.VK_SPACE) {
            Player.isFiring = false;
        }


    }


    public void keyTyped(KeyEvent e) {

    }

}
