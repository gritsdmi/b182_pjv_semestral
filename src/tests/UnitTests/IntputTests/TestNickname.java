package UnitTests.IntputTests;

import org.junit.Test;
import start.GamePanel;
import start.InputTesting.InputNewPlayer;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class TestNickname {
    InputNewPlayer inputNewPlayer = new InputNewPlayer(new GamePanel(new JFrame()));

    @Test
    public void test_Nickname_isEmpty() {
        String nickName = "";
        inputNewPlayer.checkInput(nickName);
        assertEquals("Field is empty", inputNewPlayer.getMessage());
    }

    @Test
    public void test_Nickname_tooShort() {
        String nickName = "qwe";
        inputNewPlayer.checkInput(nickName);
        assertEquals("Name is too short", inputNewPlayer.getMessage());
    }

    @Test
    public void test_Nickname_tooLong() {
        String nickName = "qwertyuioas";
        inputNewPlayer.checkInput(nickName);
        assertEquals("Name is too long", inputNewPlayer.getMessage());
    }

    @Test
    public void test_Nickname_wrongFormat() {
        String nickName = "";
        inputNewPlayer.checkInput(nickName);
        assertEquals("Wrong format of name. Only letters or numbers are allowed", inputNewPlayer.getMessage());
    }

    @Test
    public void test_Nickname_isOk() {
        String nickName = "";
        inputNewPlayer.checkInput(nickName);
        assertEquals("Ok", inputNewPlayer.getMessage());
    }

}