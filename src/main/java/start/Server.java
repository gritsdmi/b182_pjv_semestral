package start;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Server implements Runnable {
    private int id;
    private BufferedReader in;
    private BufferedWriter out;
    private ServerSocket serverSocket;
    private BufferedImage image;
    private GamePanel gp;
    private OutputStream outputStream;

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
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    outputStream = socket.getOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] size;
                    while (true) {
                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
//                        System.out.println(word);
                        String[] coords = word.split(" ");
                        GamePanel.player2.setX(Double.parseDouble(coords[0]));
                        GamePanel.player2.setY(Double.parseDouble(coords[1]));

                        // не долго думая отвечает клиенту


                        image = gp.getImage();


                        ImageIO.write(image, "jpg", byteArrayOutputStream);

                        size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                        outputStream.write(size);
                        outputStream.write(byteArrayOutputStream.toByteArray());
                        outputStream.flush();
//                        out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");
//                        out.flush(); // выталкиваем все из буфера


                    }
                } finally { // в любом случае сокет будет закрыт
                    System.out.println("dfjkhgkdf");
                    socket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
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
