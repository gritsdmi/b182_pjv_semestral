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
        String tooLongInput = "12345678915253234242423423432424242356789876545325345525223456789123456";
        client.checkInput(tooLongInput);
        assertEquals("Too long", client.getErrorMessage());
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
        String tooLongInput = "192.168.91.1";
        client.checkInput(tooLongInput);
        assertEquals("IPv4 OK", client.getErrorMessage());
    }

    @Test
    public void test_IPv6_isOk() {
        String tooLongInput = "2001:718:2:cf:343b:12d6:9870:114f";
        client.checkInput(tooLongInput);
        assertEquals("IPv6 OK", client.getErrorMessage());
    }
}