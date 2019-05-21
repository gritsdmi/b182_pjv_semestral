package UnitTests.IntputTests;

import org.junit.Test;
import start.Client;
import start.GamePanel;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class testingIP {
    Client client = new Client(new GamePanel(new JFrame()));

    @Test
    public void test_IP_tooLong() {
        String tooLongInput = "123456789123456789123456";
        client.checkInput(tooLongInput);
        assertEquals("Too much", client.getErrorMessage());
    }


    @Test
    public void test_IP_tooShort() {
        String tooLongInput = "123";
        client.checkInput(tooLongInput);
        assertEquals("Too short", client.getErrorMessage());
    }

    @Test
    public void test_IP_isEpmty() {
        String tooLongInput = "";
        client.checkInput(tooLongInput);
        assertEquals("Field is empty", client.getErrorMessage());
    }

    @Test
    public void test_IP_wrongFormat() {
        String tooLongInput = "adadwadaw";
        client.checkInput(tooLongInput);
        assertEquals("Wrong format", client.getErrorMessage());
    }

    @Test
    public void test_IPv4_isOk() {
        String tooLongInput = "adadwadaw";
        client.checkInput(tooLongInput);
        assertEquals("IPv4 OK", client.getErrorMessage());
    }

    @Test
    public void test_IPv6_isOk() {
        String tooLongInput = "adadwadaw";
        client.checkInput(tooLongInput);
        assertEquals("IPv6 OK", client.getErrorMessage());
    }
}