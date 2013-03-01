package game.handlers;

import game.Game;
import game.entity.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

	public boolean[] keys;
	
	public InputHandler(Game game, Player player) {
		game.addKeyListener(this);
		keys = new boolean[KeyEvent.KEY_LAST];
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

}
