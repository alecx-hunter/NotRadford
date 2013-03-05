package game.graphics;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;

public class Text {

    public static BufferedImage getOutline(Graphics2D g, String text, Color color) {
        TextLayout tl = new TextLayout(text, g.getFont(), g.getFontRenderContext());
        Shape s = tl.getOutline(null);

        BufferedImage img = new BufferedImage(s.getBounds().width, s.getBounds().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(color);
        g2.draw(s);
        g2.dispose();
        return img;
    }

}
