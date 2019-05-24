package start;

import start.GameObjects.*;
import start.Listeners.Listeners;
import start.Listeners.MouseListener;
import start.Logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, Constants, Serializable {


    private transient Thread thread = new Thread(this);
    private transient JFrame mainFrame;
    private transient BufferedImage image;


    private transient Graphics2D graphics;

    public static GameBackground background;
    public static MenuBackground menuBackground;
    public static Player player;
    public static Player player2;
    public static volatile ArrayList<Bullet> bullets;
    public static volatile ArrayList<Block> blocks;
    public static ArrayList<Block> busyBlocks;
    private ArrayList<Drop> drops;
    public static ArrayList<SpawnPoint> enemySpawns;
    private GameButton menuButton;
    private GameButton continueButton;
    private GameButton startButton;
    private GameButton playButton;
    private Random random = new Random();
    private String level;
    private String[] levels;
    private ArrayList<Point> freeSpacesMap;
    private boolean startWave;
    private double startWaveTimer;
    private int wave = 1;
    private SaveLoadController slc;
    private boolean printNewWave = false;
    private Point interfaceStrPosition;

    public static ArrayList<GameButton> buttons;
    private MapGenerator mp;
    private GameButton test;
    private transient Server server;
    private transient Client client;
    private String endingStr;
    public static boolean showEndingStr = false;
    private String interfaceStr;
    public static boolean isServer;
    public static boolean isClient;

    public static Base base;


    public static boolean menu;
    public static String name = "";

    public static String[] savedPlayers;
    private String IpAdress = "";
    public static boolean showNets = false;



    // stage types:
    //0 - first-start stage
    //1 - action(game) stage
    //2 - pause stage
    public static int stage;
    public static String curLevel;
    public static ArrayList<String> nets;


    private long timerFPS;
    private double millisToFPS;
    private int sleepTime;
    private double delayPlayerShot = System.nanoTime();
    private double delayPlayerShot2 = System.nanoTime();
    private double dropDelayTime = System.nanoTime();
    private GameButton[] newButtons;


    //Constructor
    public GamePanel(JFrame jFrame) {
//        loadCustomFonts();
        mainFrame = jFrame;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(PANEL_WIDTH + 150, PANEL_HEIGHT));
        setFocusable(true);
        addKeyListener(new Listeners());
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
        levels = new String[]{LEVEL_1, LEVEL_2, LEVEL_3};
        level = levels[0];
        mp = new MapGenerator(this);
        menu = true;
        buttons = new ArrayList<GameButton>();
        background = new GameBackground();

        startButton = new GameButton('s', this);
        menuButton = new GameButton('m', this);
        continueButton = new GameButton('c', this);
        playButton = new GameButton('p', this);
        image = new BufferedImage(PANEL_WIDTH + 150, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        enemySpawns = new ArrayList<>();
        drops = new ArrayList<>();
        blocks = new ArrayList<>();
        bullets = new ArrayList<>();
        busyBlocks = new ArrayList<>();
        isServer = false;
        isClient = false;

        slc = new SaveLoadController(this);
        savedPlayers = slc.parseSavedPlayers();
    }


    //Methods

    public void start() {
        requestFocus();
        thread.start();
    }


    public void run() {
        ChangeStage(-1);

        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE
        // WHILE TRUE

        while (true) {
            timerFPS = System.nanoTime();
            switch (stage) {
                case 1:
                    if (isClient) {
                        GameUpdateClient();
                        paint(graphics);
                    } else {
                        GameUpdate();
                        paint(graphics);
                    }

                    break;
                case 2:

                    MenuPaint(graphics);
                    break;
                default:
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
        buttons = new ArrayList<GameButton>();
        drops = new ArrayList<Drop>();
        if (!isServer && !isClient) {
            wave = 0;
            player = new Player(this, 600, 600, 1);
            player.setTankPictures("src/main/resources/Entity/BluePixelTank.png", "src/main/resources/Entity/BluePixelTankTower.png");
//            base = new Base(new Point(400, 500));
        } else {
            if (isClient) {
                player2 = new Player(this, 600, 600, 2);
                player = new Player(this, 250, 600, 1);
                player2.setTankPictures("src/main/resources/Entity/BluePixelTank.png", "src/main/resources/Entity/BluePixelTankTower.png");
                player.setTankPictures("src/main/resources/Entity/RedPixelTank.png", "src/main/resources/Entity/RedPixelTankTower.png");
            } else {
                player = new Player(this, 600, 600, 1);
                player2 = new Player(this, 250, 600, 2);
                player.setTankPictures("src/main/resources/Entity/BluePixelTank.png", "src/main/resources/Entity/BluePixelTankTower.png");
                player2.setTankPictures("src/main/resources/Entity/RedPixelTank.png", "src/main/resources/Entity/RedPixelTankTower.png");
            }
        }



        startButton = new GameButton('s', this);
        menuButton = new GameButton('m', this);
        continueButton = new GameButton('c', this);
        playButton = new GameButton('p', this);


        mp.buildMap(level);
        if (!isServer) {
            enemySpawns = mp.getSpawnPoints();
        } else {
            enemySpawns = new ArrayList<>();
        }
        freeSpacesMap = mp.getFreeSpaces();
    }

    public void generateSavedGame(ArrayList<ArrayList> loadedData) {
        millisToFPS = 1000 / FPS;
        image = new BufferedImage(PANEL_WIDTH + 150, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        background = new GameBackground();
        menuBackground = new MenuBackground();

        //parsing loaded data
        player = (Player) loadedData.get(0).get(0);
        blocks = (ArrayList<Block>) loadedData.get(1);
        base = (Base) loadedData.get(2).get(0);
        bullets = (ArrayList<Bullet>) loadedData.get(3);
        enemySpawns = (ArrayList<SpawnPoint>) loadedData.get(4);
        freeSpacesMap = (ArrayList<Point>) loadedData.get(5);
        drops = (ArrayList<Drop>) loadedData.get(6);//may generate some problems with delays
        wave = (Integer) loadedData.get(7).get(0);

        player.setTankPictures("src/main/resources/Entity/BluePixelTank.png", "src/main/resources/Entity/BluePixelTankTower.png");
        for (SpawnPoint sp : enemySpawns) {
            for (Enemy enemy : sp.getEnemies()) {
                enemy.setEnemyTankPic("src/main/resources/Entity/GrayPixelTank.png");
            }
        }


        startButton = new GameButton('s', this);
        menuButton = new GameButton('m', this);
        continueButton = new GameButton('c', this);
        playButton = new GameButton('p', this);

        menu = false;


    }

    public void ChangeStage(int newStage) {
        switch (newStage) {
            case -1:
                savedPlayers = slc.parseSavedPlayers();
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                menu = true;
                stage = 2;
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                test = new GameButton('s', this);
                newButtons = test.createPlayersButtons();
                for (int i = 0; i < newButtons.length; i++) {
                    buttons.add(newButtons[i]);
                }

                interfaceStr = "Hi, new player, welcome to our game";
                interfaceStrPosition = new Point(100, 100);
                break;
            case 0:
                menu = true;
                stage = 2;
                background.setColor(Color.ORANGE);
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(playButton);
                buttons.add(new GameButton('i', this));
                buttons.add(new GameButton('g', this));
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                if (isServer) {
                    try {
                        server.end();
                        isServer = false;
                    } catch (IOException e) {

                    }

                }
                if (isClient) {
                    try {
                        client.end();
                        isServer = false;
                    } catch (IOException e) {

                    }

                }
                isClient = false;
                isServer = false;
                showEndingStr = false;
                System.out.println(name);
                interfaceStr = "Welcome back, " + name + "!";
                interfaceStrPosition = new Point(100, 100);
                break;

            case 1:

                stage = 1;

                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(menuButton);

                background.setDim(PANEL_WIDTH, PANEL_HEIGHT);
                interfaceStr = "";
                break;
            case 2:

                stage = 2;
                buttons.remove(menuButton);
                buttons.add(startButton);

                buttons.add(continueButton);
                buttons.add(new GameButton('z', this));
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                break;
            case 3:

                stage = 2;
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                test = new GameButton('s', this);
                newButtons = test.createLevelButtons(levels.length);
                for (int i = 0; i < newButtons.length; i++) {
                    buttons.add(newButtons[i]);
                }
                if (!isServer && !isClient) {
                    buttons.add(new GameButton('x', this));
                }

                if (isServer) {
                    interfaceStr = name + ", Choose level for multiplayer";
                    interfaceStrPosition = new Point(50, 400);
                } else {
                    interfaceStr = name + ", Choose level or load your game:)";
                    interfaceStrPosition = new Point(50, 400);
                }

                break;
            case 4:
                menu = true;
                background.setColor(Color.ORANGE);
                background.setEpilepticmode(false);
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                showEndingStr = true;
                System.out.println("DIED");
                System.out.println("UMER");
                stage = 2;
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(new GameButton('n', this));
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                break;
            case 5:
                while (buttons.size() > 0) {
                    buttons.remove(0);
                }
                buttons.add(new GameButton('q', this));
                buttons.add(new GameButton('f', this));
                background.setDim(PANEL_WIDTH + 150, PANEL_HEIGHT);
                interfaceStr = name + ", host game or connect to another server!";
                interfaceStrPosition = new Point(50, 400);
                break;

        }
    }

    public void StartServer() {
        server = new Server(this);

        try {
            server.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public boolean StartClient() {
        client = new Client(this);
        client.displayContent();

        if (client.isAllOk()) {
            client.start();
//            System.out.println("ok");
        } else {
            isClient = false;
//            System.out.println("fail");
            return false;
        }
        return true;
    }

    public void setLevel(String lvl) {
        level = lvl;
        generateGame(level);
        menu = false;
    }

    public void GameUpdateClient() {
        background.update();
        player.update();
        if (player.getHealth() <= 0) {
            ChangeStage(4);
        }
    }

    public void GameUpdate() {


        // Background update
        background.update();

        //Player update
        player.update();
        if (isServer || isClient) {
            player2.update();
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
        //Drops update

        for (int i = 0; i < drops.size(); i++) {
            drops.get(i).update();
            if (drops.get(i).checkTimer()) {
                drops.remove(i);
            }
            try {

                if (drops.get(i).isDead()) {
                    drops.remove(i);
                    i--;
                }
            } catch (IndexOutOfBoundsException e) {
//                System.err.println("drop/time exception fixed");
            }
        }

        //Enemies update
        for (SpawnPoint sp : enemySpawns) {
            sp.update();
            for (int i = 0; i < sp.getEnemies().size(); i++) {
                sp.getEnemies().get(i).update();
                if (!sp.getEnemies().get(i).isAlive() || !sp.getEnemies().get(i).isEnemyOnMap()) {
                    sp.getEnemies().remove(i);
                    i--;
                }
            }
        }

        if (!isServer) SpawnDrop();

        if (!player.isAlive()) {
            endingStr = "You lose at " + wave + " wave";
            ChangeStage(4);
        }
        if (isServer) {

        }
        if (startWave) {
            checkWaveStarter();
        }
        if ((System.nanoTime() - startWaveTimer) / 1000000 > 2000) {
            printNewWave = false;
        }
    }

    public void startNewWave() {
        startWave = true;

    }

    public void checkWaveStarter() {
        if (enemySpawns.get(0).getCurrentAlive() == 0 && enemySpawns.get(1).getCurrentAlive() == 0) {
            wave++;

            printNewWave = true;
            startWaveTimer = System.nanoTime();
            for (SpawnPoint sp : enemySpawns) {
                sp.setCapacity(wave);
                startWave = false;
            }
        }

    }

    public boolean delay(double del) {
        if ((System.nanoTime() - delayPlayerShot) / 1000000 > del) {
            delayPlayerShot = System.nanoTime();
            return true;
        } else {
            return false;
        }
    }

    public boolean delay2(double del) {
        if ((System.nanoTime() - delayPlayerShot2) / 1000000 > del) {
            delayPlayerShot2 = System.nanoTime();
            return true;
        } else {
            return false;
        }
    }

    private void SpawnDrop() {
        if ((System.nanoTime() - dropDelayTime) / 1000000 > 2000) {
            dropDelayTime = System.nanoTime();

            int randomFree = random.nextInt(freeSpacesMap.size() - 1);
            Point newDropPoint = freeSpacesMap.get(randomFree);
            int randomType = random.nextInt(4);
            drops.add(new Drop(newDropPoint.x, newDropPoint.y, randomType, player));
        }
    }

    /**
     * Method deleting all buttons from panel
     */
    public void deleteButtons() {
        while (buttons.size() > 0) {
            buttons.remove(0);
        }
    }

    /**
     * Method draws menu
     *
     * @param g
     */

    public void MenuPaint(Graphics2D g) {
        Graphics2D g2d = g;
        g2d.setFont(new Font("sa", 1, 20));
        background.draw(g2d);
        g2d.setColor(Color.BLACK);
        //Buttons draw
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g2d);

        }

        if (showNets) {
            int i = 200;
//            for (String str : nets) {
            for (int j = 0; j < nets.size(); j++) {
                if (j == 0 || j == 1 || j == 2 || j == 3) {

                    g2d.drawString(nets.get(j), 100, i);
                    i += 50;
                }
            }
        }
        if (showEndingStr) {
            g2d.drawString(endingStr, 200, 600);
        }
        g2d.setColor(Color.BLACK);
        g2d.drawString(interfaceStr, interfaceStrPosition.x, interfaceStrPosition.y);
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.drawImage(image, 0, 0, this);

        g2.dispose();
    }

    /**
     * Main pain method. Call another draw methods
     *
     * @param g Graphics2d
     */

    public void paint(Graphics2D g) {

        Graphics2D g2d = g;
        background.draw(g2d);
        menuBackground.draw(g2d);
        // Player draw
        player.draw(g2d);
        g2d.setFont(new Font("sa", 1, 20));
        g2d.drawString(name, 810, 600);
//        if (isServer) g2d.drawString(IpAdress, 100, 100);

        if (isServer || isClient) {
            player2.draw(g2d);
        } else {
            base.draw(g2d);
        }

        //Bullet draw
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g2d);
        }

        //Blocks draw
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getAutor() == -1) blocks.get(i).draw(g2d);
        }

        //Buttons draw
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(g2d);

        }
        //Drops draw
        for (int i = 0; i < drops.size(); i++) {
            drops.get(i).draw(g2d);
        }

        //new Enemies draw
        for (SpawnPoint sp : enemySpawns) {
            for (Enemy enemy : sp.getEnemies()) {
                enemy.draw(g2d);
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5));
        if (!isServer && !isClient) {
            g2d.drawString("WAVE  " + wave, 810, 300);
        }

        if (printNewWave) {
            g2d.setColor(Color.PINK);
            g2d.setFont(new Font("big", 1, 50));
            g2d.drawString("WAVE " + wave, 300, 200);
            g2d.setFont(new Font("sa", 1, 20));
            g2d.setColor(Color.BLACK);

        }

        Graphics2D g2 = (Graphics2D) this.getGraphics();

        g2.drawImage(image, 0, 0, this);

        g2.dispose();
    }

    public static ArrayList<SpawnPoint> getEnemySpawns() {
        return enemySpawns;
    }

    public ArrayList<Drop> getDrops() {
        return drops;
    }

    public ArrayList<Player> getPlayerAsArrayList() {
        ArrayList<Player> ret = new ArrayList<>();
        ret.add(player);
        return ret;
    }

    public BufferedImage getImage() {
        return image;
    }

    public ArrayList<Point> getFreeSpacesMap() {
        return freeSpacesMap;
    }

    public ArrayList<Base> getBaseAsArrayList() {
        ArrayList<Base> ret = new ArrayList<>();
        ret.add(base);
        return ret;
    }

    public ArrayList<Integer> getWaveAsArrayList() {
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(wave);
        return ret;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    void setIpAdress(String ip) {
        this.IpAdress = ip;
    }

    public void setInterfaceStr(String interfaceStr) {
        this.interfaceStr = interfaceStr;
    }

    public void setInterfaceStrPosition(Point interfaceStrPosition) {
        this.interfaceStrPosition = interfaceStrPosition;
    }

    void setEndingStr(String endingStr) {
        this.endingStr = endingStr;
    }

}
