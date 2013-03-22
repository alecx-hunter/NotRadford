package game.graphics;

import game.logging.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public BufferedImage getSprite(int position) {
        if (position < 0 || position >= sprites.length)
            return null;
        return sprites[position];
    }

}
