package game.pathfinding;

import game.Game;
import game.graphics.Level;

import java.awt.Point;
import java.util.ArrayList;

public class Path {

    private ArrayList<Tile> path;
    private ArrayList<Tile> closed;
    private Heap<Tile> open;
    private Level level;
	
	public Path(Level l) {
		path = new ArrayList<Tile>();
		open = new Heap<Tile>();
		closed = new ArrayList<Tile>();
	
		level = l;		
	}
	
	public Point getNext() {
		if (path == null || path.size() == 0)
			return null;
		Tile t = path.remove(path.size() - 1);
		return new Point(t.x, t.y);
	}
	
	public void generatePath(int startx, int starty, int endx, int endy) {
		path.clear();
		open.clear();
		closed.clear();

        Tile start = new Tile(startx, starty);
        Tile end = new Tile(endx, endy);
        Tile current = new Tile(start.x, start.y);

        long startTime = System.currentTimeMillis();
		for (int x = 0; x < Game.WIDTH*Game.SCALE; x++)
			for (int y = 0; y < Game.HEIGHT*Game.SCALE; y++) {
				level.tiles[x][y].calcCost(endx, endy);
			}

        System.out.println("It took " + (System.currentTimeMillis() - startTime) + "ms to find the path");
		start = level.tiles[startx][starty];
		end = level.tiles[endx][endy];
		current = new Tile(start.x, start.y);
		
		closed.add(start);
		
		while (true) {
			if (current.x == end.x && current.y == end.y)
				break;
			searchOpenTiles(current.x, current.y);
			
			Tile temp = open.remove();
			if (temp == null)
				break;
			closed.add(temp);
			
			current.x = temp.x;
			current.y = temp.y;
			
		}
		
		Tile t = level.tiles[current.x][current.y];
		while ((t = t.getParent()) != null)
			path.add(t);

        level.resetParents();
	}
	
	private boolean isValidSearchPosition(Point p) {
		return level.tiles[p.x][p.y].isTraversable() && !closed.contains(level.tiles[p.x][p.y]) && !open.contains(level.tiles[p.x][p.y]);
	}
	
	private void searchOpenTiles(int x, int y) {
		Point top = new Point(x, y - 1);
		Point right = new Point(x + 1, y);
		Point bottom = new Point(x, y + 1);
		Point left = new Point(x - 1, y);
		
		Point topRight = new Point(x + 1, y - 1);
		Point bottomRight = new Point(x + 1, y + 1);
		Point bottomLeft = new Point(x - 1, y + 1);
		Point topLeft = new Point(x - 1, y - 1);

		if (top.y >= 0 && isValidSearchPosition(top)) {
			
			level.tiles[top.x][top.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[top.x][top.y]);
		}
		
		if (right.x < Game.WIDTH * Game.SCALE && isValidSearchPosition(right)) {

            level.tiles[right.x][right.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[right.x][right.y]);
		}
		
		if (bottom.y < Game.HEIGHT * Game.SCALE && isValidSearchPosition(bottom)) {

            level.tiles[bottom.x][bottom.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[bottom.x][bottom.y]);
		}
		
		if (left.x >= 0 && isValidSearchPosition(left)) {

            level.tiles[left.x][left.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[left.x][left.y]);
		}
		
		if (topRight.x < Game.WIDTH * Game.SCALE && topRight.y >= 0 && isValidSearchPosition(topRight)) {

            level.tiles[topRight.x][topRight.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[topRight.x][topRight.y]);
		}
		
		if (bottomRight.x < Game.WIDTH * Game.SCALE && bottomRight.y < Game.HEIGHT * Game.SCALE && isValidSearchPosition(bottomRight)) {
            level.tiles[bottomRight.x][bottomRight.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[bottomRight.x][bottomRight.y]);
		}
		
		if (bottomLeft.x >= 0 && bottomLeft.y < Game.WIDTH * Game.SCALE && isValidSearchPosition(bottomLeft)) {
            level.tiles[bottomLeft.x][bottomRight.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[bottomLeft.x][bottomRight.y]);
		}
		
		if (topLeft.x >= 0 && topLeft.y >= 0 && isValidSearchPosition(topLeft)) {
            level.tiles[topLeft.x][topLeft.y].setParent(level.tiles[x][y]);
			open.insert(level.tiles[topLeft.x][topLeft.y]);
		}
	}
}
