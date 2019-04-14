package start.Listeners;

import start.GameObjects.Player;
import start.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements MouseMotionListener, java.awt.event.MouseListener {

    private GamePanel gp;


    public void mouseDragged(MouseEvent e) {

        Player.dir = e.getPoint();
        GamePanel.turel.setDirection(e.getPoint());

    }

    public void mouseMoved(MouseEvent e) {
        if (Player.M1pressed == false) {
            GamePanel.turel.setDirection(e.getPoint());
            Player.dir = e.getPoint();
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Player.M1pressed = true;


        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        Player.M1pressed = false;
    }

    public void mouseEntered(MouseEvent e) {


    }

    public void mouseExited(MouseEvent e) {

    }
}
