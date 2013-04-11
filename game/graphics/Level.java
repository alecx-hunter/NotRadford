package game.graphics;

import game.Game;
import game.logging.Log;
import game.pathfinding.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Contains all of the game tiles that are displayed on screen.
 * This class is mainly used when searching for the best path.
 */
public class Level {

	private static final int WIDTH = Game.WIDTH;
	private static final int HEIGHT = Game.HEIGHT;
	public Tile[][] tiles;
	
	public Level() {
		tiles = new Tile[WIDTH][HEIGHT];
		
		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++)
				tiles[x][y] = new Tile(x, y);
	}

    /**
     * Resets all the parents back to null.
     * This is needed for the pathfinding.
     */
    public void resetParents() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                tiles[x][y].setParent(null);
    }

    /**
     * Renders the entire level by rendering each tile
     * @param g The graphics object to draw to
     */
    public void render(Graphics2D g) {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                tiles[x][y].render(g);
    }

    /**
     * Adds an image to the level with the specified path and coordinates
     * @param image The image to add to this level
     * @param x X coordinate of the image
     * @param y Y coordinate of the image
     * @param solid Whether this should be traversable or not
     */
    public void addObject(BufferedImage image, int x, int y, boolean solid) {
        int xTiles = image.getWidth() / Tile.WIDTH;
        int yTiles = image.getHeight() / Tile.HEIGHT;

        for (int i = x; i < x + xTiles; i++)
            for (int j = y; j < y + yTiles; j++) {
                BufferedImage sub = image.getSubimage((i - x)*Tile.WIDTH, (j - y)*Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
                tiles[i][j].setImage(sub);
                tiles[i][j].setTraversable(solid);
            }
    }

    public boolean isTraversable(Rectangle r) {
        if (r.x / Tile.WIDTH < 0 || r.y / Tile.HEIGHT < 0 || r.x / Tile.WIDTH >= WIDTH || r.y / Tile.HEIGHT >= HEIGHT)
            return false;
        ArrayList<Tile> tilesunder = new ArrayList<Tile>();
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                if (r.intersects(tiles[i][j].getBounds()))
                    tilesunder.add(tiles[i][j]);

        for (Tile t : tilesunder)
            if (!t.isTraversable())
                return false;
        return true;
    }
	
}
