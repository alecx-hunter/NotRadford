package game.graphics;

import java.awt.*;

/**
 * This class is only responsible for drawing an rough outline of
 * some text
 */
public class Text {

    public static void drawOutline(Graphics2D g, String text, Color color, int x, int y) {
        Color prev = g.getColor();
        g.setColor(color);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y + 1);
        g.setColor(prev);
    }

    public static void drawShadow(Graphics2D g, String text, Shadow position, int x, int y) {
        Color prev = g.getColor();
        g.setColor(Color.BLACK);

        switch(position) {
            case TOP_RIGHT:
                g.drawString(text, x + 1, y - 1);
                break;
            case TOP_LEFT:
                g.drawString(text, x - 1, y - 1);
                break;
            case BOTTOM_RIGHT:
                g.drawString(text, x + 1, y + 1);
                break;
            case BOTTOM_LEFT:
                g.drawString(text, x - 1, y + 1);
                break;
            default:
                break;
        }

        g.setColor(prev);
    }

}
