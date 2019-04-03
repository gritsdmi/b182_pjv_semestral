package start;

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

    float BULLET_SPEED = 10;

    byte WALL_TYPE_BORDER = 0;
    byte WALL_TYPE_BRICK = 1;

    int FPS = 30;

}
