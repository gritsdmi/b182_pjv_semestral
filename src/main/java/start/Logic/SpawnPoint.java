package start.Logic;

import start.GameObjects.Enemy;

import java.awt.*;
import java.util.ArrayList;

/**
 * class represents enemy's spawn point located on Map
 *
 * @see MapGenerator
 */
public class SpawnPoint implements Constants {

    private final MapGenerator mp;
    private Point spawnPoint = new Point(0, 0);
    private ArrayList<Enemy> enemies;
    private double nanotime;
    private int capacity;
    private boolean isActive;

//    private int actualEnemiesCount;


    public SpawnPoint(MapGenerator mp, int capacity, Point spawnPoint) {
        this.mp = mp;
        this.capacity = capacity;
        this.spawnPoint.setLocation(spawnPoint);
        this.enemies = new ArrayList<>();
//        actualEnemiesCount = enemies.size();
        this.nanotime = System.nanoTime();
//        System.out.println("Spawn created" + spawnPoint.toString());
        isActive = true;
    }

    public void update() {
        if (isActive) {
            if (computeSpawningDelay(ENEMY_SPAWN_DELAY) && enemies.size() < ENEMY_MAX_COUNT_ON_MAP) {
                enemies.add(new Enemy(new Point(spawnPoint)));
//                System.out.println("new enemy spawned" + spawnPoint.toString());
                capacity--;
                controlCapacity();
            }
        }
    }

    private boolean controlCapacity() {
        if (capacity < 1) isActive = false;
        else isActive = true;
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

    public Point getSpawnPoint() {
        return spawnPoint;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public boolean isActive() {
        return isActive;
    }


}
