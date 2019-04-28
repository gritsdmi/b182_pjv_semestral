package start;


import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("NES Tanks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel();

//        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(gamePanel, BorderLayout.WEST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        JLabel lb = new JLabel("asadasdasdas", JLabel.CENTER);
//        frame.add(lb);
//        lb.setVisible(true);
        gamePanel.start();

    }
}
