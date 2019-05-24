package start;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CustomFonts {
    private static ArrayList<CustomFonts> customFontList = new ArrayList<CustomFonts>();
    private static String fontPath;

    public CustomFonts(String font) {
        CustomFonts.fontPath = "src/main/resources/Fonts/" + font;
        registerFont();
    }

    private void registerFont() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Fonts/kongtext.ttf")));
            System.out.println("tru");
        } catch (Exception e) {
            System.err.println("font err");
        }
    }

    public static void addFont(CustomFonts font) {
        customFontList.add(font);
    }
}
