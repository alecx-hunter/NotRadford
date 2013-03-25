package game.graphics;

import game.Game;
import game.pathfinding.Tile;

/**
 * Contains all of the game tiles that are displayed on screen.
 * This class is mainly used when searching for the best path.
 */
public class Level {

	private static final int WIDTH = Game.WIDTH*Game.SCALE;
	private static final int HEIGHT = Game.HEIGHT*Game.SCALE;
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
	
}
