package game.pathfinding;

import game.Game;
import game.graphics.Level;

import java.awt.Point;
import java.util.ArrayList;

public class Path {

	private ArrayList<Position> path;
	private ArrayList<Position> closed;
	private Heap<Position> open;
    private Position end;
    private Level level;
	private Position[][] points;
	
	public Path(Level l) {
		path = new ArrayList<Position>();
		open = new Heap<Position>();
		closed = new ArrayList<Position>();		
		points = new Position[Game.WIDTH*Game.SCALE][Game.HEIGHT*Game.SCALE];
	
		level = l;		
	}
	
	public int getPathLength() {
		return path == null ? 0 : path.size();
	}
	
	public Point getNext() {
		if (path == null || path.size() == 0)
			return null;
		Position p = path.remove(path.size() - 1);
		return new Point(p.x, p.y);
	}
	
	public void generatePath(int startx, int starty, int endx, int endy) {
        long startTime = System.currentTimeMillis();
		path.clear();
		open.clear();
		closed.clear();

        Position start = new Position(startx, starty);
		end = new Position(endx, endy);
        Position current = new Position(start.x, start.y);
		
		for (int x = 0; x < Game.WIDTH*Game.SCALE; x++)
			for (int y = 0; y < Game.HEIGHT*Game.SCALE; y++) {
				points[x][y] = new Position(x, y);
				points[x][y].moveCost = moveCost(new Point(x, y));
			}
		start = points[startx][starty];
		end = points[endx][endy];
		current = new Position(start.x, start.y);
		
		closed.add(start);
		
		while (true) {
			if (current.x == end.x && current.y == end.y)
				break;
			searchOpenTiles(current.x, current.y);
			
			Position temp = open.remove();
			if (temp == null)
				break;
			closed.add(temp);
			
			current.x = temp.x;
			current.y = temp.y;
			
		}
		
		Position p = points[current.x][current.y];
		while ((p = p.getParent()) != null) {
			//System.out.println(p);
			path.add(p);
		}
        System.out.println("It took " + (System.currentTimeMillis() - startTime) + "ms to find the path");
		
	}
	
	private int moveCost(Point p) {
		return (Math.abs(p.x - end.x) + Math.abs(p.y - end.y));
	}
	
	private boolean isValidSearchPosition(Point p) {
		return level.isTraversable(p) && !closed.contains(points[p.x][p.y]) && !open.contains(points[p.x][p.y]);
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
			
			points[top.x][top.y].setParent(points[x][y]);
			open.insert(points[top.x][top.y]);
		}
		
		if (right.x < Game.WIDTH * Game.SCALE && isValidSearchPosition(right)) {
			
			points[right.x][right.y].setParent(points[x][y]);
			open.insert(points[right.x][right.y]);
		}
		
		if (bottom.y < Game.HEIGHT * Game.SCALE && isValidSearchPosition(bottom)) {
			
			points[bottom.x][bottom.y].setParent(points[x][y]);
			open.insert(points[bottom.x][bottom.y]);
		}
		
		if (left.x >= 0 && isValidSearchPosition(left)) {
			
			points[left.x][left.y].setParent(points[x][y]);
			open.insert(points[left.x][left.y]);
		}
		
		if (topRight.x < Game.WIDTH * Game.SCALE && topRight.y >= 0 && isValidSearchPosition(topRight)) {
			
			points[topRight.x][topRight.y].setParent(points[x][y]);
			open.insert(points[topRight.x][topRight.y]);
		}
		
		if (bottomRight.x < Game.WIDTH * Game.SCALE && bottomRight.y < Game.HEIGHT * Game.SCALE && isValidSearchPosition(bottomRight)) {
			points[bottomRight.x][bottomRight.y].setParent(points[x][y]);
			open.insert(points[bottomRight.x][bottomRight.y]);
		}
		
		if (bottomLeft.x >= 0 && bottomLeft.y < Game.WIDTH * Game.SCALE && isValidSearchPosition(bottomLeft)) {
			points[bottomLeft.x][bottomRight.y].setParent(points[x][y]);
			open.insert(points[bottomLeft.x][bottomRight.y]);
		}
		
		if (topLeft.x >= 0 && topLeft.y >= 0 && isValidSearchPosition(topLeft)) {
			points[topLeft.x][topLeft.y].setParent(points[x][y]);
			open.insert(points[topLeft.x][topLeft.y]);
		}
	}
	
	public class Position implements Comparable<Position> {
		public int x;
		public int y;
		private Position parent;
		private int moveCost;
		
		public Position(Point p) {
			x = p.x;
			y = p.y;
			parent = null;
		}
		
		public Position(int x, int y) {
			this.x = x; 
			this.y = y;
			parent = null;
		}
		
		public Position getParent() {
			return parent;
		}
		
		public void setParent(Position position) {
			//System.out.println(this + " -> " + position);
			parent = position;
		}
		
		public int compareTo(Position p) {
			return p.moveCost > moveCost ? -1 : p.moveCost < moveCost ? 1 : 0;
		}
		
		public String toString() {
			return String.format("(%d, %d)", x, y);
		}
	}
}
