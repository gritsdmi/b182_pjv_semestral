package start;

import start.GameObjects.Block;
import start.GameObjects.Bullet;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {
    private int id;
    private BufferedReader in;
    private BufferedWriter out;
    private ServerSocket serverSocket;
    private GamePanel gp;
    private int hp;
    Point myCoords;
    ArrayList<Block> bl;
    ArrayList<Bullet> bu;

    public Server(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
                serverSocket = new ServerSocket(8080);

                Socket socket = serverSocket.accept();
                System.out.println("prinal");
                try {

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // и отправлять
//                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    oos.flush();
                    while (true) {
                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                        String[] coords = word.split(" ");
                        GamePanel.player2.setPosition(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
                        GamePanel.player2.setDir(new Point(Integer.parseInt(coords[3]), Integer.parseInt(coords[4])));
                        GamePanel.player2.turelSetDirection(new Point(Integer.parseInt(coords[3]), Integer.parseInt(coords[4])));
                        if (coords[2].equals("true")) {
                            GamePanel.player2.shoot(2);
                        }

                        // не долго думая отвечает клиенту
                        bl = (ArrayList<Block>) GamePanel.blocks.clone();
                        oos.writeObject(bl);
                        oos.flush();
                        oos.reset();


                        bu = (ArrayList<Bullet>) GamePanel.bullets.clone();
                        oos.writeObject(bu);
                        oos.flush();
                        oos.reset();

                        myCoords = new Point(GamePanel.player.getPosition());
                        oos.writeObject(myCoords);
                        oos.flush();
                        oos.reset();

                        hp = GamePanel.player2.getHealth();
                        oos.writeObject(hp);
                        oos.flush();
                        oos.reset();

                        if (GamePanel.player.getHealth() <= 0) {
                            break;
                        }

                    }
                } finally { // в любом случае сокет будет закрыт
                    System.out.println("dfjkhgkdf");
                    socket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
//                    out.close();
                }


            } finally {
                System.out.println("Сервер закрыт!");
                serverSocket.close();

            }
        } catch (IOException e) {
            gp.ChangeStage(0);
//            System.err.println("OSHIBKA");
        }
    }
}
