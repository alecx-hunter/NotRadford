package game.pathfinding;

import game.Game;
import game.graphics.Level;
import game.logging.Log;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class is used to generate paths from one point to another.
 * Path generation is created using the A* algorithm
 */

public class Path {

    /**
     * The final path
     */
    private ArrayList<Tile> path;

    /**
     * The target position
     */
    private Tile end;
    /**
     * The tiles that are part of the "closed" list
     */
    private ArrayList<Tile> closed;
    /**
     * The tiles that are available to be searched
     * Stored in a Heap for the fastest searching
     */
    private Heap<Tile> open;
    /**
     * The level the path is being searched on
     */
    private Level level;

    /**
     * Must pass in a Level to create a Path
     *
     * @param l The level the path should generate
     */
    public Path(Level l) {
        path = new ArrayList<Tile>();
        open = new Heap<Tile>();
        closed = new ArrayList<Tile>();

        level = l;
    }

    /**
     * Used to "pop" individual tiles off the final stack
     * @return The next Point to move to
     */
    public Point getNext() {
        if (path == null || path.size() == 0)
            return null;
        Tile t = path.remove(path.size() - 1);
        return new Point(t.x, t.y);
    }

    /**
     * Used to find out if a path has already been generated or not
     *
     * @return Returns true if the path is null or contains 0 elements, false otherwise
     */
    public boolean isEmpty() {
        return path == null || path.size() == 0;
    }

    public void generatePath(int startx, int starty, int endx, int endy) {
        path.clear();
        open.clear();
        closed.clear();

        Tile start = level.tiles[startx / Tile.WIDTH][starty / Tile.HEIGHT];
        end = level.tiles[endx / Tile.WIDTH][endy / Tile.HEIGHT];
        Tile current = new Tile(start.x, start.y);

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
            level.tiles[top.x][top.y].calcCost(end.x, end.y);
            open.insert(level.tiles[top.x][top.y]);
        }

        if (right.x < Game.WIDTH && isValidSearchPosition(right)) {
            level.tiles[right.x][right.y].setParent(level.tiles[x][y]);
            level.tiles[right.x][right.y].calcCost(end.x, end.y);
            open.insert(level.tiles[right.x][right.y]);
        }

        if (bottom.y < Game.HEIGHT && isValidSearchPosition(bottom)) {
            level.tiles[bottom.x][bottom.y].setParent(level.tiles[x][y]);
            level.tiles[bottom.x][bottom.y].calcCost(end.x, end.y);
            open.insert(level.tiles[bottom.x][bottom.y]);
        }

        if (left.x >= 0 && isValidSearchPosition(left)) {
            level.tiles[left.x][left.y].setParent(level.tiles[x][y]);
            level.tiles[left.x][left.y].calcCost(end.x, end.y);
            open.insert(level.tiles[left.x][left.y]);
        }

        if (topRight.x < Game.WIDTH && topRight.y >= 0 && isValidSearchPosition(topRight)) {
            level.tiles[topRight.x][topRight.y].setParent(level.tiles[x][y]);
            level.tiles[topRight.x][topRight.y].calcCost(end.x, end.y);
            open.insert(level.tiles[topRight.x][topRight.y]);
        }

        if (bottomRight.x < Game.WIDTH && bottomRight.y < Game.HEIGHT && isValidSearchPosition(bottomRight)) {
            level.tiles[bottomRight.x][bottomRight.y].setParent(level.tiles[x][y]);
            level.tiles[bottomRight.x][bottomRight.y].calcCost(end.x, end.y);
            open.insert(level.tiles[bottomRight.x][bottomRight.y]);
        }

        if (bottomLeft.x >= 0 && bottomLeft.y < Game.HEIGHT && isValidSearchPosition(bottomLeft)) {
            level.tiles[bottomLeft.x][bottomRight.y].setParent(level.tiles[x][y]);
            level.tiles[bottomLeft.x][bottomLeft.y].calcCost(end.x, end.y);
            open.insert(level.tiles[bottomLeft.x][bottomRight.y]);
        }

        if (topLeft.x >= 0 && topLeft.y >= 0 && isValidSearchPosition(topLeft)) {
            level.tiles[topLeft.x][topLeft.y].setParent(level.tiles[x][y]);
            level.tiles[topLeft.x][topLeft.y].calcCost(end.x, end.y);
            open.insert(level.tiles[topLeft.x][topLeft.y]);
        }
    }
}
