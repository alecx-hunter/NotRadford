package game.graphics.levels;

import game.Game;
import game.entity.Enemy;
import game.graphics.SpriteSheet;
import game.logging.Log;
import game.pathfinding.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

/**
 * Contains all of the game tiles that are displayed on screen.
 * This class is mainly used when searching for the best path.
 */
public abstract class Level {

	protected static final int WIDTH = Game.WIDTH + 1;
	protected static final int HEIGHT = Game.HEIGHT + 1;
	public Tile[][] tiles;
    protected Image fullImage;
    protected Game game;
    protected Enemy enemy;
	
	public Level(Game game) {
		tiles = new Tile[WIDTH][HEIGHT];
        game.removeProjectiles();
		
		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++)
				tiles[x][y] = new Tile(x, y);
        this.game = game;
        loadLevel();
        combineTiles();
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
        g.drawImage(fullImage, 0, 0, Game.WIDTH*Game.SCALE + 10, Game.HEIGHT*Game.SCALE + 10, null);
    }

    /**
     * Adds an image to the level with the specified path and coordinates
     * @param image The image to add to this level
     * @param x X coordinate of the image
     * @param y Y coordinate of the image
     * @param traversable Whether this should be traversable or not
     */
    public void addObject(BufferedImage image, int x, int y, boolean traversable) {
        int xTiles = image.getWidth() / Tile.WIDTH;
        int yTiles = image.getHeight() / Tile.HEIGHT;

        for (int i = x; i < x + xTiles; i++)
            for (int j = y; j < y + yTiles; j++) {
                if (i >= WIDTH || j >= HEIGHT)
                    continue;
                BufferedImage sub = image.getSubimage((i - x)*Tile.WIDTH, (j - y)*Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
                tiles[i][j].setImage(sub);
                tiles[i][j].setTraversable(traversable);
            }
    }

    /**
     * Determines if any points in the rectangle are traversable or not
     * @param r The area to check
     * @return True if all tiles in the rectangle are traversable, false otherwise
     */
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

    /**
     * Used to "combine" each tile's image into a single images which is then
     * rendered onto the screen.
     */
    public void combineTiles() {
        fullImage = new BufferedImage(Game.WIDTH*Game.SCALE + 10, Game.HEIGHT*Game.SCALE + 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = fullImage.getGraphics();
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                g.drawImage(tiles[x][y].getImage(), x * 8, y * 8, Tile.WIDTH, Tile.HEIGHT, null);
    }

    public boolean isCompleted() {
        return enemy.getHealth() == 0;
    }

    public abstract void loadLevel();

    public abstract void loadNext();
}
