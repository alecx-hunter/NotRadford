package game.pathfinding;

public class Tile implements Comparable<Tile> {

    public int x, y;
    private int moveCost;
    private Tile parent;
    private boolean traversable;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        parent = null;
        traversable = true;
        moveCost = 0;
    }

    public int compareTo(Tile p) {
        return p.moveCost > moveCost ? -1 : p.moveCost < moveCost ? 1 : 0;
    }

    public void setParent(Tile t) {
        parent = t;
    }

    public Tile getParent() {
        return parent;
    }

    public void calcCost(int x, int y) {
        moveCost = Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    public int getCost() {
        return moveCost;
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public boolean isTraversable() {
        return traversable;
    }

}
