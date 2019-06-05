package start.Logic;

import start.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputNewPlayer extends JDialog {

    //Fields

    private String enteredName;
    private String errorMessage = "";
    private boolean allOk = false;

    //Constuctor

    public InputNewPlayer(GamePanel gp) {
        super(gp.getMainFrame(), "New Player", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        enteredName = "";
    }

    //Method

    /**
     * Method checks user`s input
     *
     * @param input
     * @return true if input string is valid
     */
    public boolean checkInput(String input) {
        if (input.length() > 3 && input.length() < 8) {
            errorMessage = "Ok";
            return true;
        } else {
            if (input.length() == 0) {
                errorMessage = "Field is empty";
                return false;
            }
            if (input.length() <= 3) errorMessage = "Name is too short";
            if (input.length() >= 8) errorMessage = "Name is too long";
        }
        return false;
    }

    /**
     * Method display window of new player registration
     * includes inputfields and buttons
     */
    void displayContent() {
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
                    allOk = true;
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
                    allOk = true;
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

    String getEnteredName() {
        return enteredName;
    }

    boolean isAllOk() {
        return allOk;
    }

    public String getMessage() {
        return errorMessage;
    }

    public void setMessage(String mes) {
        this.errorMessage = mes;
    }
}
