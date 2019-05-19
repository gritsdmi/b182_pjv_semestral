package start;

import start.GameObjects.Block;
import start.GameObjects.Bullet;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {
    private BufferedWriter out;
    private BufferedReader in;
    private BufferedReader reader;
    private Socket clientSocket;
    private int hp;
    private ObjectInputStream ois;
    public static ArrayList<Block> b;

    private GamePanel gp;

    public Client(GamePanel gp) {

        this.gp = gp;
    }

    public void start() {
        System.out.println("client start");
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
//                clientSocket = new Socket("172.16.176.140", 8080);
//                clientSocket = new Socket("147.32.31.197", 8080);
//                clientSocket = new Socket("172.68.214.176", 8080);
//                clientSocket = new Socket("10.1.5.223", 8080);
//                clientSocket = new Socket("2001:718:7:9:56bf:7358:4a6d:3ac0", 8080); // pasha server v NTK
//                clientSocket = new Socket("2001:718:7:204:15fe:49a5:204b:9ff1", 8080); // Dimaaaaaa server v NTK
//                clientSocket = new Socket("2001:718:2:cf:343b:12d6:9870:114f", 8080); // pasha server na strahove

//                clientSocket = new Socket("fe80:0:0:0:d118:3c8b:f53e:aeb2", 8080); // pasha server na strahove
                clientSocket = new Socket("fe80:0:0:0:d118:3c8b:f53e:aeb2", 8080); // pasha server

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
                    GamePanel.player.setHealth(hp);
                    GamePanel.player2.turelSetDirection((Point) ois.readObject());
                    if (GamePanel.player.getHealth() <= 0) {
                        System.out.println("break server");
                        break;
                    }


                }
//
            } finally {
                System.out.println("Client closedт...");
                clientSocket.close();
                out.close();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            gp.ChangeStage(0);
//            System.err.println(e);
        }
    }

    public void end() throws IOException {
        System.out.println("Client closedт...");

        clientSocket.close();
        out.close();
        ois.close();
    }
}
