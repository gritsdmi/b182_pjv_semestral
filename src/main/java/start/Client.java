package start;

import start.GameObjects.Block;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client implements Runnable {
    private int id;
    private BufferedWriter out;
    private BufferedReader in;
    private BufferedReader reader;
    private Socket clientSocket;
    public static ArrayList<Block> b;

    private GamePanel gp;

    public Client(GamePanel gp) {
        this.gp = gp;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
//                clientSocket = new Socket("172.16.176.140", 8080); // этой строкой мы запрашиваем
                clientSocket = new Socket("172.68.214.176", 8080); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

                while (true) {


                    out.write(GamePanel.player.getX() + " " + GamePanel.player.getY() + "\n");
                    out.flush();


                    b = (ArrayList<Block>) ois.readObject();
                    System.out.println(b);
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
                in.close();
                out.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
    }
}
