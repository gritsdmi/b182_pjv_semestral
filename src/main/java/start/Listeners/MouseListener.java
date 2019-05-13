package start.Listeners;

import start.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements MouseMotionListener, java.awt.event.MouseListener {

    private GamePanel gp;


    public void mouseDragged(MouseEvent e) {

        GamePanel.player.setDir(e.getPoint());
        GamePanel.player.turelSetDirection(e.getPoint());

    }

    public void mouseMoved(MouseEvent e) {
        if (!GamePanel.player.isM1pressed()) {
            GamePanel.player.turelSetDirection(e.getPoint());
            GamePanel.player.setDir(e.getPoint());
        }
        for (int i = 0; i < GamePanel.buttons.size(); i++) {
            GamePanel.buttons.get(i).checkMouse(e.getX(), e.getY());
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        if ((e.getButton() == MouseEvent.BUTTON1) && (e.getX() <= 800)) {
//            Player.M1pressed = true;
            GamePanel.player.setM1pressed(true);




        }

    }

    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < GamePanel.buttons.size(); i++) {
            GamePanel.buttons.get(i).checkMouseClick(e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
//        Player.M1pressed = false;
        GamePanel.player.setM1pressed(false);


    }

    public void mouseEntered(MouseEvent e) {


    }

    public void mouseExited(MouseEvent e) {

    }
}
