package start.Logic;

import start.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputNewPlayer extends JDialog {

    private String enteredName;
    private String errorMessage = "";

    public InputNewPlayer(GamePanel gp) {
        super(gp.getMainFrame(), "New Player", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enteredName = "";
        displayContent();
    }

    private boolean checkInput(String input) {
        if (input.length() > 3 && input.length() < 20) {
            return true;
        } else {
            if (input.length() <= 3) errorMessage = "Not enough";
            if (input.length() >= 20) errorMessage = "Too much";
        }
        return false;
    }

    private void displayContent() {
        JPanel jp = new JPanel(new FlowLayout());

        TextField tf = new TextField(20);
        JLabel welkomeLabel = new JLabel("Enter your name");
        JButton okButton = new JButton("Ok");
        JLabel errorLabel = new JLabel("");


        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(tf.getText())) {
                    enteredName = tf.getText();
                    setVisible(false);
                    dispose();
                } else {
                    //TODO error message
                    errorLabel.setText(errorMessage);
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
                    errorLabel.setText(errorMessage);
                }
            }
        });

        jp.add(welkomeLabel);
        jp.add(tf);
        jp.add(okButton);
        jp.add(errorLabel);


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
