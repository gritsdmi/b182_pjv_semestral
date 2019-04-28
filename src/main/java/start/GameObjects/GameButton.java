package start.GameObjects;

import start.GamePanel;
import start.Logic.Constants;

import java.awt.*;

public class GameButton implements Constants {

    private char type;
    private Color color;
    private int x;
    private int y;
    private GamePanel gp;
    private int width;
    private int height;
    private String str;


    public GameButton(char type, GamePanel gp) {
        this.gp = gp;
        this.type = type;
        switch (type) {
            case 'm':
                color = Color.GRAY;
                x = PANEL_WIDTH + 2;
                y = 0;
                width = 149;
                height = 70;
                str = "Pouse";
                break;

            case 's':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 300;
                width = 150;
                height = 70;
                str = "Start new game";
                break;
            case 'c':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 200;
                width = 150;
                height = 70;
                str = "Continue";
                break;
            case 'p':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 300;
                width = 150;
                height = 70;
                str = "Play";
                break;
        }
    }

    public void checkMouse(int mouseX, int mouseY) {
        if ((mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY <= y + height)) {
            color = color.darker();

        } else {
            color = color.GRAY;
        }
    }

    public void checkMouseClick(int mouseX, int mouseY) {
        if ((mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY <= y + height)) {
            switch (type) {
                case 'm':
                    gp.ChangeStage(2);
                    break;
                case 's':
                    gp.restart();
                    break;
                case 'c':
                    gp.ChangeStage(1);
                    break;
                case 'p':
                    gp.ChangeStage(1);
                    break;
            }


        }
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawString(str, x + 20, y + 30);
    }

}
