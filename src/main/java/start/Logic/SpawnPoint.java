package start.Logic;

import start.GameObjects.Enemy;
import start.GamePanel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * class represents enemy's spawn point
 * Creates new enemies
 *
 * @see Enemy
 * @see MapGenerator
 */
public class SpawnPoint implements Constants, Serializable {

    private MapGenerator mp;
    private Point spawnPosition = new Point(0, 0);
    private ArrayList<Enemy> enemies;
    private double nanotime;
    private int capacity;
    private boolean isActive;
    private double time;
    private GamePanel gp;



    public SpawnPoint(MapGenerator mp, int capacity, Point spawnPoint, GamePanel gp) {
        this.mp = mp;
        this.capacity = capacity;
        this.spawnPosition.setLocation(spawnPoint);
        this.enemies = new ArrayList<>();
        this.nanotime = System.nanoTime();
        this.gp = gp;
        isActive = true;
    }

    public SpawnPoint(GamePanel gp) {
        this.gp = gp;
    }

    public void update() {
        if (isActive) {
            if (computeSpawningDelay(ENEMY_SPAWN_DELAY) && enemies.size() < ENEMY_MAX_COUNT_ON_MAP) {
                if (controlFreeCellOnSrawnPoint()) {
                    enemies.add(new Enemy(new Point(spawnPosition)));
                    capacity--;
                    controlCapacity();
                }
            }
        }
    }

    private boolean controlFreeCellOnSrawnPoint() {
        for (Enemy enemy : getEnemies()) {
            if (getSpawnRectangle().intersects(enemy.getRectangle())) {
                System.out.println("enemy stay here!");
                return false;
            }
        }
        return true;

    }

    private boolean controlCapacity() {
        if (capacity < 1) {
            isActive = false;
            time = System.nanoTime();
            gp.startNewWave(time);

        } else isActive = true;
        return isActive;
    }

    private boolean computeSpawningDelay(double del) {
        if ((System.nanoTime() - nanotime) / 1000000 > del) {
            nanotime = System.nanoTime();
            return true;
        } else {
            return false;
        }
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        isActive = true;
    }

    public Point getSpawnPosition() {
        return spawnPosition;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setEnemies(ArrayList<Enemy> ar) {
        this.enemies = ar;
    }

    public Rectangle getSpawnRectangle() {
        return new Rectangle(spawnPosition, new Dimension(50, 50));
    }



}
