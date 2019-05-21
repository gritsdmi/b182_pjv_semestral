package start.Logic;

import start.GameObjects.Enemy;
import start.GamePanel;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * class represents enemy's spawn point located on Map
 *
 * @see MapGenerator
 */
public class SpawnPoint implements Constants, Serializable {

    private MapGenerator mp;
    private Point spawnPoint = new Point(0, 0);
    private ArrayList<Enemy> enemies;
    private double nanotime;
    private int capacity;
    private boolean isActive;
    private double time;
    private GamePanel gp;

//    private int actualEnemiesCount;


    public SpawnPoint(MapGenerator mp, int capacity, Point spawnPoint, GamePanel gp) {
        this.mp = mp;
        this.capacity = capacity;
        this.spawnPoint.setLocation(spawnPoint);
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
                enemies.add(new Enemy(new Point(spawnPoint)));
//                System.out.println(enemies.size());
//                System.out.println("new enemy spawned" + spawnPoint.toString());
                capacity--;
                controlCapacity();
            }
        }
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

    public Point getSpawnPoint() {
        return spawnPoint;
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



}
