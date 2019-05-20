package start;


import start.Logic.SaveLoadController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("NES Tanks");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(frame);

//        frame.add(mainPanel, BorderLayout.EAST);
        frame.add(gamePanel, BorderLayout.WEST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

//        JLabel lb = new JLabel("asadasdasdas", JLabel.CENTER);
//        frame.add(lb);
//        lb.setVisible(true);
        gamePanel.start();
        SaveLoadController slc = new SaveLoadController(gamePanel);
        slc.parseSavedPlayers();

//        test();

    }

    static void test() {
        URL url = null;
        BufferedReader in = null;
        String ipAddress = "";
        try {
            url = new URL("http://bot.whatismyipaddress.com");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ipAddress = in.readLine().trim();
            /* IF not connected to internet, then
             * the above code will return one empty
             * String, we can check it length and
             * if length is not greater than zero,
             * then we can go for LAN IP or Local IP
             * or PRIVATE IP
             */
            if (!(ipAddress.length() > 0)) {
                System.out.println("if");
                try {
                    InetAddress ip = InetAddress.getLocalHost();
                    System.out.println((ip.getHostAddress()).trim());
                    ipAddress = (ip.getHostAddress()).trim();
                } catch (Exception exp) {
                    ipAddress = "ERROR";
                }
            }
        } catch (Exception ex) {
            // This try will give the Private IP of the Host.
            try {
                InetAddress ip = InetAddress.getLocalHost();
                System.out.println((ip.getHostAddress()).trim());
                ipAddress = (ip.getHostAddress()).trim();
            } catch (Exception exp) {
                ipAddress = "ERROR";
            }
            //ex.printStackTrace();
        }
        System.out.println("IP Address: " + ipAddress);

        try {
            url = new URL("https://api.myip.com");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ipAddress = in.readLine().trim();
//            ipAddress = in.readLine();
            ipAddress = ipAddress.split(":")[1];
//            String answer = ipAddress[0].sp;

//            System.out.println("Another way: " + );
            System.out.println(ipAddress);
        } catch (Exception ex) {
            System.err.println("ip error");
        }
    }
}
