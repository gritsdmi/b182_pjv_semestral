package start;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    public static int WIDTH = 800;
    public static int HEIGHT = 800;

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

    //Constructor
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
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
        mp.generateMap();
        mp.BildBorder();

        System.out.println(blocks.size());
        while (true) {
            GameUpdate();

            GameRender();
            GameDraw();
            try {
                thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println(Player.x + "  " + Player.y);
//            System.out.println("ban down "+Block.banDown + " ban left "+ Block.banLeft+ " ban top "+ Block.banTop+" ban right "+ Block.banRight);
        }
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

        turel.update();


//        System.out.println(MouseInfo.getPointerInfo().getLocation().toString());

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
