package start.Logic;

public interface Constants {

    float SCALE = 2;

    int PANEL_WIDTH = (int) (400 * SCALE);
    int PANEL_HEIGHT = (int) (400 * SCALE);

    int PLAYER_SIZE_HEIGHT = (int) (20 * SCALE * SCALE);
    int PLAYER_SIZE_WIDTH = (int) (20 * SCALE * SCALE);
    int PLAYER_SHOT_DELAY = 1;
    int PLAYER_AVOID_DISTANCE = 5;

    byte PLAYER_ACTUAL_DIRECTION_UP = 1;
    byte PLAYER_ACTUAL_DIRECTION_RIGHT = 2;
    byte PLAYER_ACTUAL_DIRECTION_LEFT = -2;
    byte PLAYER_ACTUAL_DIRECTION_DOWN = -1;

    int BULLET_SPEED = 10;
    int PLAYER_MOVING_SPEED = 5;

    byte WALL_TYPE_BORDER = 0;
    byte WALL_TYPE_BRICK = 1;
    byte WALL_TYPE_TEST = 2;

    int FPS = 30;

    String LEVEL_1 = "src/main/resources/Maps/Level_1.txt";
    String LEVEL_2 = "src/main/resources/Maps/Level_2.txt";

    int ENEMY_MOVING_SPEED = 3;
    int ENEMY_FIRE_DISTANCE = 100;
    int ENEMY_HEALTH = 2;

}
