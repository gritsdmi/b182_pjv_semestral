package start;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;


public class Client implements Runnable {
    private int id;
    private BufferedWriter out;
    private BufferedReader in;
    private BufferedReader reader;
    private Socket clientSocket;
    private BufferedImage image;
    private byte[] sizeAr;
    private byte[] imageAr;

    public Client() {
        id = 0;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("172.16.176.140", 8080); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                InputStream inputStream = clientSocket.getInputStream();
                sizeAr = new byte[4];

                while (true) {


                    out.write(GamePanel.player.getX() + " " + GamePanel.player.getY() + " " + GamePanel.player.isM1pressed() + "\n");
                    out.flush();
                    inputStream.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    imageAr = new byte[size];
                    inputStream.read(imageAr);

                    image = ImageIO.read(new ByteArrayInputStream(imageAr));


//                    String serverWord = in.readLine();

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
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
