package game.pathfinding;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is mostly used during pathfinding. The class
 * must implement Comparable to be able to be stored into the
 * Heap data type. Each tile's "movecost" is calculated when
 * a path is being generated.
 */

public class Tile implements Comparable<Tile> {

    /**
     * X and Y coordinates of this tile
     */
    public int x, y;
    /**
     * This "cost" this tile has to get to the end point
     */
    private int moveCost;
    /**
     * The tiles parent
     * Used when traversing backwards to find the path
     */
    private Tile parent;
    /**
     * Whether Entities can traverse this tile or not
     */
    private boolean traversable;
    /**
     * Width of this Tile
     */
    public static final int WIDTH = 8;
    /**
     * Height of this Tile
     */
    public static final int HEIGHT = 8;
    /**
     * Image to be displayed on this tile
     */
    private Image image;

    /**
     * Must pass in the x and y coordinates for this tile upon creating it
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        parent = null;
        traversable = true;
        moveCost = 0;

        image = null;
    }

    /**
     * Used to compare two tiles. Needed for the comparable interface
     * @param t The Tile to compare to
     * @return Returns -1 if this Tile's moveCost is less than the one passed in.
     * Returns 1 if this Tile's moveCost is greater than the one passed in.
     * Returns 0 if the tiles' moveCost are the same
     */
    public int compareTo(Tile t) {
        return t.moveCost > moveCost ? -1 : t.moveCost < moveCost ? 1 : 0;
    }

    public void setParent(Tile t) {
        parent = t;
    }

    /**
     * @return The parent of this Tile
     */
    public Tile getParent() {
        return parent;
    }

    /**
     * The cost to move from the given tile to this tile
     * @param x X coordinate of starting point
     * @param y Y coordinate of starting point
     */
    public void calcCost(int x, int y) {
        moveCost = Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
    }

    /**
     * @return True if Entities can pass over this tile, false otherwise
     */
    public boolean isTraversable() {
        return traversable;
    }

    /**
     * Changes the tile to be traversable or not
     * @param solid Whether or not objects can pass through this tile
     */
    public void setTraversable(boolean solid) {
        traversable = solid;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Renders this tile onto the screen
     * @param g The graphics object to draw on
     */
    public void render(Graphics2D g) {
        g.drawImage(image, x*WIDTH, y*HEIGHT, null);
    }

    /**
     * toString method
     * @return String representation of this Tile designated by it's coordinates.
     * Example: (5, 1)
     */
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

}
