package start.Logic;

public interface Constants {

    float SCALE = 2;

    int PANEL_WIDTH = (int) (400 * SCALE);
    int PANEL_HEIGHT = (int) (400 * SCALE);

    int PLAYER_SIZE_HEIGHT = 50;
    int PLAYER_SIZE_WIDTH = 50;
    int PLAYER_SHOT_DELAY = 1;
    int PLAYER_AVOID_DISTANCE = 5;
    int PLAYER_HEALTH = 10000;

    byte PLAYER_ACTUAL_DIRECTION_UP = 1;
    byte PLAYER_ACTUAL_DIRECTION_RIGHT = 2;
    byte PLAYER_ACTUAL_DIRECTION_LEFT = -2;
    byte PLAYER_ACTUAL_DIRECTION_DOWN = -1;

    int BULLET_SPEED = 10;
    int PLAYER_MOVING_SPEED = 5;

    int WALL_TYPE_BORDER = 0;
    int WALL_TYPE_BRICK = 1;
    int WALL_TYPE_TEST = 2;
    int WALL_HEALTH = 50;

    int FPS = 30;

    String LEVEL_1 = "src/main/resources/Maps/Level_1.txt";
    String LEVEL_2 = "src/main/resources/Maps/Level_2.txt";

    int ENEMY_MOVING_SPEED = 2;
    int ENEMY_FIRE_DISTANCE = 300;
    int ENEMY_HEALTH = 80;
    int ENEMY_SHOOTING_DELAY = 800;
    int ENEMY_MOVING_OFFSET = 50;
    int ENEMY_SPAWN_DELAY = 3000;
    /*this value will multiplied by spawn point count*/
    int ENEMY_MAX_COUNT_ON_MAP = 3;


}
