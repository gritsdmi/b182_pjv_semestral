package start.Listeners;

import start.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listeners implements KeyListener {


    public void keyPressed(KeyEvent e) {
        if (!GamePanel.menu) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_W) {

                GamePanel.player.setDirection(1);


            }
            if (key == KeyEvent.VK_A) {

                GamePanel.player.setDirection(4);

            }

            if (key == KeyEvent.VK_S) {
                GamePanel.player.setDirection(3);

            }

            if (key == KeyEvent.VK_D) {
                GamePanel.player.setDirection(2);

            }
            if (key == KeyEvent.VK_SPACE) {
            }
        }



    }


    public void keyReleased(KeyEvent e) {
        if (!GamePanel.menu) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_W) {
                GamePanel.player.setDirection(0);
            }
            if (key == KeyEvent.VK_A) {

                GamePanel.player.setDirection(0);
            }

            if (key == KeyEvent.VK_S) {

                GamePanel.player.setDirection(0);
            }

            if (key == KeyEvent.VK_D) {
                GamePanel.player.setDirection(0);
            }
            if (key == KeyEvent.VK_SPACE) {
            }
        }



    }


    public void keyTyped(KeyEvent e) {

    }

}
