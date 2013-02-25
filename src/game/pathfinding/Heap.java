package game.pathfinding;

import java.util.Arrays;

public class Heap<T extends Comparable<T>> {
	
	private Object[] tNodes;
	
	public Heap() {
		tNodes = new Object[0];
	}
	
	public void insert(T node) {
		tNodes = Arrays.copyOf(tNodes, tNodes.length + 1);
		tNodes[tNodes.length - 1] = node;
		trickleUp(tNodes.length - 1);
	}
		
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
	
	/*
	 *				  0
	 *	   	    1	            2
	 *		3      4         5	     6
	 *    7   8  9   10   11   12 13  14		
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
	
	private void swap(int pos1, int pos2) {
		Object temp = tNodes[pos1];
		tNodes[pos1] = tNodes[pos2];
		tNodes[pos2] = temp;
	}
	
	public void print() {
		
		int levels = calcLevels();
		String str = "";
		int pos = 0;
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
		
	private int calcLevels() {
		int totalAllowed = 0;
		int level = 0;
		while (totalAllowed < tNodes.length) {
			totalAllowed += (int)Math.pow(2, level);
			level++;
		}
		return level;
	}
	
	public int size() {
		return tNodes.length;
	}
	
	public void clear() {
		tNodes = new Object[0];
	}
	
	@SuppressWarnings("unchecked")
	public boolean contains(T t) {
		for (Object o : tNodes)
			if (((T)o).equals(t))
				return true;
		return false;
	}

}
