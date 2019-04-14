package start;

import start.GameObjects.Block;
import start.GameObjects.Bullet;
import start.GameObjects.Player;
import start.GameObjects.Turel;
import start.Listeners.Listeners;
import start.Listeners.MouseListener;
import start.Logic.Constants;
import start.Logic.MapGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, Constants {

    private Thread thread = new Thread(this);

    private BufferedImage image;
    private Graphics2D graphics;
    public static GameBackground background;
    public static Player player;
    public static Turel turel;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Block> blocks;
    public static BufferedImage TankPicture;
    public static BufferedImage TankTowerPicture;
    public static BufferedImage BulletPicture;
    private MapGenerator mp;

    private long timerFPS;
    private double millisToFPS;
    private int sleepTime;
    private double nanotime = System.nanoTime();

    //Constructor
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setFocusable(true);
        addKeyListener(new Listeners());
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
        mp = new MapGenerator(this);

    }


    //Methods
    public void start() {
        requestFocus();
        thread.start();
    }

    public void run() {

        millisToFPS = 1000 / FPS;

        image = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBackground();
        bullets = new ArrayList<Bullet>();
        blocks = new ArrayList<Block>();

        String imagePath = "src/main/resources/Entity/myTank2.png";
        String imagePath2 = "src/main/resources/Entity/TankTower3.png";
        String pathToPNG = "src/main/resources/Entity/Bullet_b (копия).png";

        try {
            TankPicture = ImageIO.read(new File(imagePath));
            TankTowerPicture = ImageIO.read(new File(imagePath2));
            BulletPicture = ImageIO.read(new File(pathToPNG));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new Player();
        turel = new Turel(player.x, player.y);
        mp.buildMap();
        mp.generateMap();
        while (true) {
            timerFPS = System.nanoTime();

            GameUpdate();
            GameRender();
            GameDraw();


            timerFPS = (System.nanoTime() - timerFPS) / 1000000;
            if (millisToFPS > timerFPS) {
                sleepTime = (int) (millisToFPS - timerFPS);
            } else sleepTime = 1;

            try {
                thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timerFPS = 0;
            sleepTime = 1;
        }
    }



    public void GameUpdate() {

        // Background update
        background.update();

        //Player update
        player.update();

        if (player.M1pressed == true) {

            if ((System.nanoTime() - nanotime) / 1000000 > player.getReload()) {
                GamePanel.bullets.add(new Bullet(player.x + 25, player.y + 25, player.dir));
                nanotime = System.nanoTime();
            }


        }
        //Bullet update
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();
            if (!bullets.get(i).isAlive()) {
                bullets.remove(i);
                i--;
            }
        }

        //Walls update
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isAlive()) {
                blocks.remove(i);
                i--;
            }
        }

        turel.update();
    }

    public void GameRender() {
        // Background draw
        background.draw(graphics);
        // Player draw
        player.draw(graphics);
        //Bullet draw
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics);

        }
        //Blocks draws
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(graphics);

        }
        turel.draw(graphics);


    }

    private void GameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);

        g2.dispose();
    }
}
