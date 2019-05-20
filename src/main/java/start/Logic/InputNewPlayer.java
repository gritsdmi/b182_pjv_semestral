package start.Logic;

import start.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputNewPlayer extends JDialog {

    private String enteredName;
//    private int position;

    public InputNewPlayer(GamePanel gp) {
        super(gp.getMainFrame(), "New Player", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enteredName = "";
        displayContent();
//        this.position = position;
    }

    private boolean checkInput(String input) {
        if (input.length() > 3 && input.length() < 10) return true;
        return false;
    }

    private void displayContent() {
        JPanel jp = new JPanel(new FlowLayout());

        TextField tf = new TextField(20);
        JLabel welkomeLabel = new JLabel("Enter your name");
        JButton okButton = new JButton("Ok");

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(tf.getText())) {
                    enteredName = tf.getText();
                    setVisible(false);
                    dispose();
                } else {
                    //TODO error message
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(tf.getText())) {
                    enteredName = tf.getText();
                    setVisible(false);
                    dispose();
                } else {
                    //TODO error message
                }
            }
        });

        jp.add(welkomeLabel);
        jp.add(tf);
        jp.add(okButton);


        setContentPane(jp);
        setSize(new Dimension(600, 300));
        setLocationRelativeTo(null);
        setResizable(true);
        requestFocus();
        setVisible(true);


    }

    public String getEnteredName() {
        return enteredName;
    }
}
