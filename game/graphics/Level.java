package game.graphics;

import game.Game;
import game.pathfinding.Tile;

import java.awt.Point;

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

    public void resetParents() {
        for (int x = 0; x < WIDTH; x++)
            for (int y = 0; y < HEIGHT; y++)
                tiles[x][y].setParent(null);
    }
	
}
