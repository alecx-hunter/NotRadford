package game.graphics;

import game.Game;

import java.awt.Point;

public class Level {

	private static final int WIDTH = Game.WIDTH*Game.SCALE;
	private static final int HEIGHT = Game.HEIGHT*Game.SCALE;
	private boolean[][] tiles;
	
	public Level() {
		tiles = new boolean[WIDTH][HEIGHT];
		
		for (int x = 0; x < WIDTH; x++)
			for (int y = 0; y < HEIGHT; y++)
				tiles[x][y] = true;
	}
	public boolean isTraversable(Point p) {
		return tiles[p.x][p.y];
	}

	
}
