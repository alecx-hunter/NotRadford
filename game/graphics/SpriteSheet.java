package game.graphics;

import game.logging.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Loads in a sprite sheet and stores each sprite into
 * a BufferedImage array that can be used to display them.
 */
public class SpriteSheet {

    private BufferedImage[] sprites;

    public SpriteSheet(String path, int rows, int columns) {

        sprites = new BufferedImage[rows * columns];
        try {
            BufferedImage sheet = ImageIO.read(new File(getClass().getResource(path).getFile()));
            int width = sheet.getWidth() / columns;
            int height = sheet.getHeight() / rows;

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
            Log.error("Image was not found!");
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
