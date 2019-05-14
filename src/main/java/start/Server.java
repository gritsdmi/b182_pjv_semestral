package start;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int id;
    private BufferedReader in;
    private BufferedWriter out;
    private ServerSocket serverSocket;
    private GamePanel gp;

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

                        // не долго думая отвечает клиенту
                        oos.writeObject(GamePanel.blocks);
//                        System.out.println(GamePanel.blocks.size());
                        oos.flush();
                        oos.reset();
                        oos.writeObject(GamePanel.bullets);
//                        System.out.println(GamePanel.blocks.size());
                        oos.flush();
                        oos.reset();



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
            System.err.println("OSHIBKA");
        }
    }
}
