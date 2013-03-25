package game.graphics;

import java.awt.*;

/**
 * This class is only responsible for drawing an rough outline of
 * some text
 */
public class Text {

    /**
     * Draws an outline of 1 pixel around the given text
     * @param g Graphics object to draw on
     * @param text The text that will be outlined.
     * @param color The color of the outline
     * @param x X coordinate of the text
     * @param y Y coordinate of the text
     */
    public static void drawOutline(Graphics2D g, String text, Color color, int x, int y) {
        Color prev = g.getColor();
        g.setColor(color);
        g.drawString(text, x - 1, y - 1);
        g.drawString(text, x - 1, y + 1);
        g.drawString(text, x + 1, y - 1);
        g.drawString(text, x + 1, y + 1);
        g.setColor(prev);
    }

    /**
     * Draws a shadow at the given position
     * Positions: Top-left, top-right, bottom-left, bottom-right
     * @param g The Graphics object to draw on
     * @param text The text to have the shadow applied to
     * @param position Shadow enum for which direction the shadow should be
     * @param x X coordinate of text
     * @param y Y coordinate of text
     */
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
