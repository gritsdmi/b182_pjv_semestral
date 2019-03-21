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

    //Constructor
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(new Listeners());
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
        String imagePath = "/home/pavel/IdeaProjects/Test1/src/myTank2.png";
        String imagePath2 = "/home/pavel/IdeaProjects/Test1/src/TankTower3.png";
        try {
            TankPicture = ImageIO.read(new File(imagePath));
            TankTowerPicture = ImageIO.read(new File(imagePath2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player = new Player();
        turel = new Turel();
        blocks.add(new Block(Color.RED, 250, 250));
        blocks.add(new Block(Color.RED, 350, 250));
        blocks.add(new Block(Color.RED, 350, 200));
        BildBorder();

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
            System.out.println(Player.x + "  " + Player.y);
//            System.out.println("ban down "+Block.banDown + " ban left "+ Block.banLeft+ " ban top "+ Block.banTop+" ban right "+ Block.banRight);
        }
    }

    public void BildBorder() {
        int bx = 0;
        int by = 0;
        while (bx != WIDTH - 50) {
            blocks.add(new Block(Color.BLACK, bx, by));
            bx = bx + 50;
        }
        while (by != HEIGHT - 50) {
            blocks.add(new Block(Color.BLACK, bx, by));
            by = by + 50;
        }
        while (bx != 0) {
            blocks.add(new Block(Color.BLACK, bx, by));
            bx = bx - 50;
        }
        while (by != 0) {
            blocks.add(new Block(Color.BLACK, bx, by));
            by = by - 50;
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
            if (bullets.get(i).remove()) {
                bullets.remove(i);
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
