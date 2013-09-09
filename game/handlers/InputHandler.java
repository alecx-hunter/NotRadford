package game.handlers;

import game.Game;
import game.entity.Player;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * This class handles all the basic keyboard and mouse input
 */
public class InputHandler implements KeyListener, MouseListener {

    public HashMap<Integer, Long> keys;
    public Point clicked;
	
	public InputHandler(Game game, Player player) {
		game.addKeyListener(this);
        game.addMouseListener(this);

        keys = new HashMap<Integer, Long>();

        clicked = new Point();
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

    /**
     * Adds the key that was pressed as well as what time
     * it was pressed into the map.
     */
	public void keyPressed(KeyEvent e) {
        keys.put(e.getKeyCode(), System.currentTimeMillis());
	}

    /**
     * Removes the key from the map
     */
	public void keyReleased(KeyEvent e) {
        keys.remove(e.getKeyCode());
	}

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        clicked = e.getPoint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
