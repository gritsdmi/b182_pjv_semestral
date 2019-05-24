package start;

import com.google.common.net.InetAddresses;
import start.GameObjects.Block;
import start.GameObjects.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client extends JDialog implements Runnable {

    //Fields

    private BufferedWriter out;
    private BufferedReader in;
    private BufferedReader reader;
    private Socket clientSocket;
    private int hp;
    private ObjectInputStream ois;
    public static ArrayList<Block> b;
    private String serversIp = "";
    private boolean allOk = false;
    private String errorMessage = "";
    private GamePanel gp;

    //Constructor

    public Client(GamePanel gp) {
        super(gp.getMainFrame(), "Entering ip", true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.gp = gp;
    }


    /**
     * Method checks user`s input
     *
     * @param input
     * @return true if input string is valid
     */
    public boolean checkInput(String input) {
        if (input.length() > 3 && input.length() < 50) {
            if (InetAddresses.isInetAddress(input)) {
                if (input.length() <= 16) errorMessage = "IPv4 OK";
                else errorMessage = "IPv6 OK";
            } else {
                errorMessage = "Wrong format";
            }
            return true;
        } else {
            if (input.length() == 0) {
                errorMessage = "Field is empty";
                return false;
            }
            if (input.length() <= 3) errorMessage = "Too short";
            if (input.length() >= 50) errorMessage = "Too much";
        }
        return false;
    }

    /**
     * Method display window of new player registration
     * includes inputfields and buttons
     */
    public void displayContent() {
        JPanel jp = new JPanel(new FlowLayout());

        TextField tf = new TextField(20);
        JLabel welkomeLabel = new JLabel("Enter server's ip");
        JButton okButton = new JButton("Ok");
        JLabel errorLabel = new JLabel("");


        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(tf.getText())) {
                    serversIp = tf.getText();
                    System.out.println(serversIp);
                    allOk = true;
                    setVisible(false);
                    dispose();
                } else {
                    errorLabel.setText(errorMessage);
                }
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(tf.getText())) {
                    serversIp = tf.getText();
                    System.out.println(serversIp);
                    setVisible(false);
                    dispose();
                    allOk = true;

                } else {
                    errorLabel.setText(errorMessage);
                }
            }
        });


        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });


        jp.add(welkomeLabel);
        jp.add(tf);
        jp.add(okButton);
        jp.add(backButton);
        jp.add(errorLabel);


        setContentPane(jp);
        setSize(new Dimension(600, 300));
        setLocationRelativeTo(null);
        setResizable(true);
        requestFocus();
        setVisible(true);
    }

    public void start() {
        System.out.println("client start");
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
                clientSocket = new Socket(serversIp, 8080);

                reader = new BufferedReader(new InputStreamReader(System.in));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                ois = new ObjectInputStream(clientSocket.getInputStream());

                Point coor = new Point(0, 0);
                while (true) {

                    out.write(GamePanel.player.getX() + " " + GamePanel.player.getY() + " " + GamePanel.player.isM1pressed() + " " + GamePanel.player.getTurelDirection().x + " " + GamePanel.player.getTurelDirection().y + "\n");
                    out.flush();


                    b = (ArrayList<Block>) ois.readObject();
                    GamePanel.blocks = b;
                    GamePanel.bullets = (ArrayList<Bullet>) ois.readObject();
                    coor.setLocation((Point) ois.readObject());

                    GamePanel.player2.setPosition(coor);
                    hp = (int) ois.readObject();

                    if (hp != GamePanel.player.getHealth()) {
                        GamePanel.player.takeDamage(GamePanel.player.getHealth() - hp);
                    }
                    GamePanel.player2.turelSetDirection((Point) ois.readObject());
                    if (GamePanel.player.getHealth() <= 0) {
                        System.out.println("break server");
                        gp.setEndingStr("You lose");
                        break;
                    }


                }
//
            } finally {
                System.out.println("Client closed...");
                clientSocket.close();
                out.close();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            if (GamePanel.player.isAlive()) {
                gp.setEndingStr("You win");
            } else {
                gp.setEndingStr("You lose");
            }
            gp.ChangeStage(4);
//            System.err.println(e);
        }
    }

    void end() throws IOException {
        System.out.println("Client closed...");

        clientSocket.close();
        out.close();
        ois.close();
    }

    boolean isAllOk() {
        return allOk;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
