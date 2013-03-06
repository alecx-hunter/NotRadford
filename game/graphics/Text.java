package game.graphics;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

public class Text {

    public static void drawOutline(Graphics2D g, String text, Color color, int x, int y) {
        g.setColor(color);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y + 1);
    }

}
