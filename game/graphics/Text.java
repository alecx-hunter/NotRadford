package game.graphics;

import java.awt.*;

/**
 * This class is only responsible for drawing an rough outline of
 * some text
 */
public class Text {

    public static void drawOutline(Graphics2D g, String text, Color color, int x, int y) {
        g.setColor(color);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y + 1);
    }

}
