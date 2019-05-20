package start;

import start.GameObjects.Block;
import start.GameObjects.Bullet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server implements Runnable {
    private BufferedReader in;
    private ServerSocket serverSocket;
    private GamePanel gp;
    private int hp;
    private Point angle;
    private Socket socket;
    Point myCoords;
    private ObjectOutputStream oos;
    ArrayList<Block> bl;
    ArrayList<Bullet> bu;
    private ArrayList<String> adress;

    public Server(GamePanel gp) {
        this.gp = gp;
        adress = new ArrayList<>();
    }

    public void start() throws UnknownHostException {
        System.out.println("server start");
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            try {

                serverSocket = new ServerSocket(8080);
                Enumeration e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface n = (NetworkInterface) e.nextElement();
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        adress.add(i.getHostAddress());
                        System.out.println(i.getHostAddress());
                    }
                }
                gp.setIpAdress(adress.get(3));
                socket = serverSocket.accept();
                System.out.println("Accepted");
                try {

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    oos = new ObjectOutputStream(socket.getOutputStream());
                    while (true) {
                        String word = in.readLine();
                        String[] coords = word.split(" ");
                        GamePanel.player2.setPosition(new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
                        GamePanel.player2.setDir(new Point(Integer.parseInt(coords[3]), Integer.parseInt(coords[4])));
                        GamePanel.player2.turelSetDirection(new Point(Integer.parseInt(coords[3]), Integer.parseInt(coords[4])));
                        if (coords[2].equals("true")) {
                            GamePanel.player2.shoot(2);
                        }

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

                        angle = GamePanel.player.getTurelDirection();
                        oos.writeObject(angle);
                        oos.flush();
                        oos.reset();

                        if (GamePanel.player.getHealth() <= 0 || !GamePanel.isServer) {
                            System.out.println("break server");
                            break;
                        }

                    }
                } finally {
                    socket.close();
                    in.close();
                    oos.close();
                }


            } finally {
                System.out.println("Server closed!");
                serverSocket.close();

            }
        } catch (Exception e) {
            System.err.println("Error");

            gp.ChangeStage(0);
        }
    }

    public void end() throws IOException {
//
        serverSocket.close();
    }
}
