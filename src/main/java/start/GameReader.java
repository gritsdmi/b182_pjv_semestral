package start;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class GameReader implements Runnable {
    private Socket inputSocket;
    private BufferedReader reader;
    private Scanner scanner;
    public static String string;

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            inputSocket = new Socket();
            inputSocket.connect(new InetSocketAddress("172.16.176.140", 8180));
            scanner = new Scanner(inputSocket.getInputStream());

//                reader = new BufferedReader(new
//                        InputStreamReader(inputSocket.getInputStream()));
            while (true) {
                if (scanner.hasNextLine()) {
//                        System.out.println(scanner.nextLine());
                    string = scanner.nextLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
