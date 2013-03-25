package game.pathfinding;

import java.util.Arrays;

/**
 * This is a Heap data structure. It is used during path finding
 * to get the best choice for the next tile.
 *
 * The class was made Generic, but the class contained in the heap
 * must implement the Comparable interface in order to be able to
 * sort the heap when nodes are added and removed.
 */
public class Heap<T extends Comparable<T>> {
	
	private Object[] tNodes;
	
	public Heap() {
		tNodes = new Object[0];
	}

    /**
     * Inserts an object of type T into the heap and then
     * sorts it.
     * @param node The object to add to the heap
     */
	public void insert(T node) {
		tNodes = Arrays.copyOf(tNodes, tNodes.length + 1);
		tNodes[tNodes.length - 1] = node;
		trickleUp(tNodes.length - 1);
	}

    /**
     * Removes the top-most node and returns it's
     * value. The top-most node will be the object
     * with the lowest value.
     * @return The top of the tree
     */
	@SuppressWarnings("unchecked")
	public T remove() {
		if (tNodes.length == 0)
			return null;
		swap(0, tNodes.length - 1);
		T node = (T)tNodes[tNodes.length - 1];
		tNodes = Arrays.copyOf(tNodes,  tNodes.length - 1);
		trickleDown(0);
		return node;
	}

    /**
     * Recursively moves the object at the given position
     * up the tree until it is where it should be.
     * @param pos The position to start at.
     */
	@SuppressWarnings("unchecked")
	private void trickleUp(int pos) {
		if (pos == 0)
			return;
		int newPos = (pos - 1)/2;
		if (((T)tNodes[pos]).compareTo((T)tNodes[newPos]) < 0)
			swap(pos, newPos);
		else
			return;
		trickleUp(newPos);
	}

    /**
     * Moves the object at the given position down the tree
     * until it is in it's correct place.
     * @param pos
     */
	@SuppressWarnings("unchecked")
	private void trickleDown(int pos) {
		int right = (pos+1)*2;
		int left = (pos*2)+1;
		int large = 0;
		
		if (right >= tNodes.length && left >=tNodes.length)
			return;
		if (right >= tNodes.length) {
			if (((T)tNodes[left]).compareTo((T)tNodes[pos]) < 0)
				swap(left, pos);
			else
				return;
		}
		
		if (right < tNodes.length && left < tNodes.length)
			large = ((T)tNodes[right]).compareTo((T)tNodes[left]) < 0 ? right : left;
		swap(pos, large);
		trickleDown(large);
	}

    /**
     * Swaps two nodes
     */
	private void swap(int pos1, int pos2) {
		Object temp = tNodes[pos1];
		tNodes[pos1] = tNodes[pos2];
		tNodes[pos2] = temp;
	}

    /**
     * Prints out a visual representation of the Heap
     */
	public void print() {
		
		int levels = calcLevels();
		String str = "";
		int pos;
		int indent = 0;
		int spaces = 4;
		
		for (int i = levels - 1; i >= 0; i--) {
			String line = "";
			for (int s = 0; s < indent; s++)
				line += " ";
			indent = (indent+1)*2;
			pos = (int)Math.pow(2, i) - 1;
			for (int j = pos; j < pos + (int)Math.pow(2, i); j++) {
				if (j >= tNodes.length)
					break;
				line += String.format("%-"+spaces+"s", tNodes[j]);
			}
			str = "\n" + line + str;
			spaces *= 2;
		}
		System.out.println(str + "\n");
	}

    /**
     * Calculates how many layers of the heap there are
      * @return How many layers or levels are in the Heap
     */
	private int calcLevels() {
		int totalAllowed = 0;
		int level = 0;
		while (totalAllowed < tNodes.length) {
			totalAllowed += (int)Math.pow(2, level);
			level++;
		}
		return level;
	}

    /**
     * @return The size or length of the Heap
     */
	public int size() {
		return tNodes.length;
	}

    /**
     * Clears out all the nodes
     */
	public void clear() {
		tNodes = new Object[0];
	}

    /**
     * Searches for the given object. Could be improved vastly
     * @param t The object to look for
     * @return True if the object is found, false if not
     */
	@SuppressWarnings("unchecked")
	public boolean contains(T t) {
		for (Object o : tNodes)
			if (o.equals(t))
				return true;
		return false;
	}

}
