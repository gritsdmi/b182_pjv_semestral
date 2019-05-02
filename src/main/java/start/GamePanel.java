package start;

import start.GameObjects.*;
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
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, Constants {

    public Thread getThread() {
        return thread;
    }

    private Thread thread = new Thread(this);

    private BufferedImage image;
    private Graphics2D graphics;

    public static GameBackground background;
    public static MenuBackground menuBackground;
    public static Player player;
    public static Turel turel;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Block> blocks;
    public static ArrayList<Enemy> enemies;
    private ArrayList<Drop> drops;
    private GameButton menuButton;
    private GameButton continueButton;
    private GameButton startButton;
    private GameButton playButton;
    private Random random = new Random();
    private String level;
    private String[] levels;
    private ArrayList<Point> freeSpacesMap;


    public static ArrayList<GameButton> buttons;
    public static BufferedImage TankPicture;
    public static BufferedImage TankTowerPicture;
    public static BufferedImage BulletPicture;
    private MapGenerator mp;
    private volatile boolean exit = false;
    private GameButton test;


    // stage types:
    //0 - first-start stage
    //1 - action(game) stage
    //2 - pause stage
    public static int stage;






    private long timerFPS;
    private double millisToFPS;
    private int sleepTime;
    private double nanotime = System.nanoTime();
    private double dropDelayTime = System.nanoTime();


    //Constructor
    public GamePanel() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH + 150, PANEL_HEIGHT));
        setFocusable(true);
        addKeyListener(new Listeners());
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
        levels = new String[]{LEVEL_1, LEVEL_2};
        level = levels[0];
        mp = new MapGenerator(this);
    }


    //Methods

    public void start() {
        requestFocus();
        thread.start();
    }


    public void run() {
        generateGame(level);

        ChangeStage(0);

        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE


        while (true) {
//            System.out.println(player.isShield());

            timerFPS = System.nanoTime();
            switch (stage) {
                case 1:
                    GameUpdate();
                    paint(graphics);

                    break;
                case 2:

                    MenuPaint(graphics);
                    break;
            }

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
        }


    }


    public void generateGame(String map) {
        millisToFPS = 1000 / FPS;

        image = new BufferedImage(PANEL_WIDTH + 150, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBackground();
        menuBackground = new MenuBackground();
        bullets = new ArrayList<Bullet>();
        blocks = new ArrayList<Block>();
        enemies = new ArrayList<Enemy>();
        buttons = new ArrayList<GameButton>();
        drops = new ArrayList<Drop>();
        player = new Player(this);
        startButton = new GameButton('s', this);
        menuButton = new GameButton('m', this);
        continueButton = new GameButton('c', this);
        playButton = new GameButton('p', this);
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


        turel = new Turel(player.x, player.y);
        enemies.add(new Enemy(new Point(500, 299)));

        mp.buildMap(level);
        freeSpacesMap = mp.getFreeSpaces();
    }

    public void ChangeStage(int newStage) {
        switch (newStage) {
            case 0:
                stage = 2;
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(playButton);

                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                break;

            case 1:

                stage = 1;

                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(menuButton);

                background.setDim(PANEL_WIDTH, PANEL_HEIGHT);
                break;
            case 2:

                stage = 2;
                buttons.remove(menuButton);
                buttons.add(startButton);

                buttons.add(continueButton);
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                break;
            case 3:

                stage = 2;
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                test = new GameButton('s', this);
                GameButton[] newButtons = test.createLevelButtons(levels.length);
                for (int i = 0; i < newButtons.length; i++) {
                    buttons.add(newButtons[i]);
                }



        }
    }

    public void setLevel(String lvl) {
        level = lvl;
        generateGame(level);
//        revalidate();


    }


    public void GameUpdate() {


        // Background update
        background.update();

        //Player update
        player.update();

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
        //Drops update

        for (int i = 0; i < drops.size(); i++) {
            drops.get(i).update();
            if (drops.get(i).isDead()) {
                drops.remove(i);
                i--;
            }
        }

        //Enemies update
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
            if (!enemies.get(i).isAlive()) {
                enemies.remove(i);
                i--;
            }
        }
        SpawnDrop();
        turel.update();
    }

    public boolean delay(double del) {
        if ((System.nanoTime() - nanotime) / 1000000 > del) {
            nanotime = System.nanoTime();
            return true;
        } else {
            return false;
        }
    }

    public void SpawnDrop() {
        if ((System.nanoTime() - dropDelayTime) / 1000000 > 2000) {
            dropDelayTime = System.nanoTime();

            int randomFree = random.nextInt(freeSpacesMap.size() - 1);
            Point newDropPoint = freeSpacesMap.get(randomFree);
            int randomType = random.nextInt(4);
            drops.add(new Drop(newDropPoint.x, newDropPoint.y, randomType, player));
        }
    }


    public void MenuPaint(Graphics2D g) {
        Graphics2D g2d = g;
        background.draw(g2d);
        //Buttons draw
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g2d);

        }
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.drawImage(image, 0, 0, this);

        g2.dispose();
    }

    public void paint(Graphics2D g) {

        Graphics2D g2d = g;
        background.draw(g2d);
        menuBackground.draw(g2d);
        // Player draw
        player.draw(g2d);
        //Bullet draw
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g2d);

        }
        //Blocks draw
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(g2d);

        }
        //Buttons draw
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g2d);

        }
        //Drops draw
        for (int i = 0; i < drops.size(); i++) {
            drops.get(i).draw(g2d);

        }
        //Enemies draw
        for (Enemy enemy : enemies) {
            enemy.draw(g2d);
        }
        turel.draw(g2d);
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.drawImage(image, 0, 0, this);

        g2.dispose();
    }

}
