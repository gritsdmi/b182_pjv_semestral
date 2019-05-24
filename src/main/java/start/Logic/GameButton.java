package start.Logic;

import start.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.Serializable;

public class GameButton implements Constants, Serializable {

    //Fields

    private char type;
    private Color color;
    private int x;
    private int y;
    private GamePanel gp;
    private int width;
    private int height;
    private String str;
    private int number;
    private boolean isEmpty;
    private BufferedReader in;

    //Constructor

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
                str = "Pause";
                break;

            case 's':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 300;
                width = 150;
                height = 70;
                str = "New game";
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
                y = 350;
                width = 150;
                height = 70;
                str = "Play";
                break;
            case 'l':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2 + 150;
                width = 150;
                height = 70;
                break;
            case 'n':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 200;
                width = 250;
                height = 100;
                str = "New game";
                break;
            case 'i':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 600;
                width = 150;
                height = 70;
                str = "Multiplayer";
                break;
            case 'q':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 600;
                width = 150;
                height = 70;
                str = "Client";
                break;
            case 'f':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 100;
                width = 150;
                height = 70;
                str = "Server";
                break;
            case 'z':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2;
                y = 400;
                width = 150;
                height = 70;
                str = "Save game";
                break;
            case 'x':
                color = Color.GRAY;
                x = PANEL_WIDTH / 2 + 350;
                y = 200;
                width = 150;
                height = 70;
                str = "Load game";
                break;
            case 'j':
                color = Color.GRAY;
                y = 300;
                width = 200;
                height = 150;

                break;
            case 'g':
                color = Color.GRAY;
                x = 0;
                y = 730;
                width = 150;
                height = 70;
                str = "Back";
                break;
            case 'd':
                color = Color.darkGray;
                x = 0;
                y = 450;
                width = 100;
                height = 50;
                str = "Delete";
                break;
        }
    }

    /**
     * Method creates array of GameButtons which represents level buttons of length:
     *
     * @param amount
     * @return this array
     */
    public GameButton[] createLevelButtons(int amount) {
        GameButton[] levelButtons = new GameButton[amount];
        int heightN = 150;
        int lvl = 1;
        for (int i = 0; i < levelButtons.length; i++) {
            levelButtons[i] = new GameButton('l', gp);
            levelButtons[i].y = heightN;
            heightN += 100;
            levelButtons[i].str = "level " + lvl;
            levelButtons[i].number = lvl;
            lvl++;
        }

        return levelButtons;
    }

    /**
     * Method creates array of GameButtons which represents players buttons
     * @return this array
     */
    public GameButton[] createPlayersButtons() {
        GameButton[] playersButtons = new GameButton[6];
        for (int i = 0; i < GamePanel.savedPlayers.length; i++) {
            if (GamePanel.savedPlayers[i] != null) {
                playersButtons[i] = new GameButton('j', gp);
                playersButtons[i].isEmpty = false;
                playersButtons[i].str = GamePanel.savedPlayers[i];

            } else {
                playersButtons[i] = new GameButton('j', gp);
                playersButtons[i].isEmpty = true;
                playersButtons[i].str = "EMPTY";
            }
            playersButtons[i].x = 75 + i * 300;
            playersButtons[i].number = i + 1;

        }

        playersButtons[3] = new GameButton('d', gp);
        playersButtons[3].number = 1;
        playersButtons[3].x = 125;
        playersButtons[4] = new GameButton('d', gp);
        playersButtons[4].number = 2;
        playersButtons[4].x = 125 + 1 * 300;
        playersButtons[5] = new GameButton('d', gp);
        playersButtons[5].number = 3;
        playersButtons[5].x = 125 + 2 * 300;
        return playersButtons;
    }

    /**
     * Method simulates buttons "hover" effect , takes mouse coords
     *
     * @param mouseX
     * @param mouseY
     */
    public void checkMouse(int mouseX, int mouseY) {
        if ((mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY <= y + height)) {
            color = color.darker();

        } else {
            color = color.GRAY;
        }
    }

    /**
     * Method simulates buttons "click" effect , takes mouse coords
     * @param mouseX
     * @param mouseY
     *
     * if clicked calls needed stage of gamePanel depending on type
     */
    public void checkMouseClick(int mouseX, int mouseY) {
        if ((mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY <= y + height)) {
            SaveLoadController slc = new SaveLoadController(gp);
            switch (type) {
                case 'm':
                    if (GamePanel.isClient || GamePanel.isServer) {
                        gp.ChangeStage(0);
                    } else {
                        gp.ChangeStage(2);
                    }
                    break;
                case 's':
                    gp.ChangeStage(0);

                    break;
                case 'c':
                    gp.ChangeStage(1);
                    break;
                case 'p':

                    gp.ChangeStage(3);

                    break;
                case 'n':
                    gp.ChangeStage(0);

                    break;
                case 'l':
                    if (GamePanel.isServer) {
                        switch (number) {
                            case 1:
                                GamePanel.curLevel = LEVEL_1;
                                break;
                            case 2:
                                GamePanel.curLevel = LEVEL_2;
                                ;
                                break;
                            case 3:
                                GamePanel.curLevel = LEVEL_3;
                                break;

                        }
                        gp.deleteButtons();
                    } else {
                        switch (number) {
                            case 1:
                                gp.setLevel(LEVEL_1);
                                break;
                            case 2:
                                gp.setLevel(LEVEL_2);
                                break;
                            case 3:
                                gp.setLevel(LEVEL_3);
                                break;
                        }
                        gp.ChangeStage(1);
                    }

                    break;
                case 'j':
                    if (isEmpty == false) {
                        gp.ChangeStage(0);
                        GamePanel.name = str;
                    } else {
                        InputNewPlayer inp = new InputNewPlayer(gp);
                        inp.displayContent();
                        if (inp.isAllOk()) {
                            GamePanel.name = inp.getEnteredName();
                            gp.ChangeStage(0);
                            slc.writeNewPlayer(inp.getEnteredName(), number);
                        }
                    }
                    break;
                case 'i':

                    gp.ChangeStage(5);

                    break;
                case 'q':
                    if (gp.StartClient()) {

                        System.out.println("client started");
                        GamePanel.isClient = true;
                        gp.setLevel(LEVEL_1);
                        gp.ChangeStage(1);
                    }
                    break;

                case 'f':
                    gp.StartServer();
                    GamePanel.isServer = true;
                    gp.ChangeStage(3);
                    break;
                case 'z'://used for saving game button
                    InputNewPlayer inp = new InputNewPlayer(gp);
                    inp.displayContent();
                    if (inp.isAllOk()) {
                        if (slc.saveGame(inp.getEnteredName())) System.out.println("Game saved ok");
                    }
                    break;
                case 'x'://used for loading game button
                    SavedGamesController sgc = new SavedGamesController(gp, slc);
                    if (sgc.isLevelSelect()) {
                        slc.loadGameFromFile(sgc.getSelectedSave());
                        gp.generateSavedGame(slc.getLoadedData());
                        gp.ChangeStage(1);
                    }

                    break;
                case 'g':
                    gp.ChangeStage(-1);
                    break;
                case 'd':
                    slc.deletePlayer(number);
                    gp.ChangeStage(-1);
                    break;

            }
        }
    }

    public void update() {

    }

    /**
     * Method draws object in the game by
     * @param g
     */
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        if (type == 'j') {
            g.drawString(str, x + 50, y + 80);
        } else {
            g.drawString(str, x + 10, y + 40);
        }

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
