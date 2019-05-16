package start;

import start.GameObjects.Block;
import start.GameObjects.Bullet;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {
    private int id;
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
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("10.4.11.56", 8080);
                System.out.println("asdasd");
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
//                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
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
//                String word = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
//                out.write(word + "\n"); // отправляем сообщение на сервер
//                out.flush();
//                String serverWord = in.readLine(); // ждём, что скажет сервер
//                System.out.println(serverWord); // получив - выводим на экран
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                out.close();
                ois.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
    }

    public void end() throws IOException {
        System.out.println("Клиент был закрыт...");
        clientSocket.close();
        out.close();
        ois.close();
    }
}
