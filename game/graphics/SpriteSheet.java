package game.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Loads in a sprite sheet and stores each sprite into
 * a BufferedImage array that can be used to display them.
 */
public class SpriteSheet {

    private BufferedImage[] sprites;

    public SpriteSheet(String path, int rows, int columns) {

        sprites = new BufferedImage[rows * columns];
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(path));
            int width = img.getWidth() / columns;
            int height = img.getHeight() / rows;
            BufferedImage sheet = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = sheet.getGraphics();
            g.drawImage(img, 0, 0, null);

            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    sprites[(i * columns) + j] = sheet.getSubimage(
                            j * width,
                            i * height,
                            width,
                            height
                    );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the sprite at the provided position.
     * Position increments as follows. The values of course
     * changing depending on how many rows and columns are given
     * 0   1   2   3   4
     * 5   6   7   8   9
     * 10 11  12  13  14
     * @param position Position of the Sprite on the sheet
     * @return The sprite in the form of a BufferedImage
     */
    public BufferedImage getSprite(int position) {
        if (position < 0 || position >= sprites.length)
            return null;
        return sprites[position];
    }

}
