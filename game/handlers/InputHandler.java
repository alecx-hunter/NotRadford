package game.handlers;

import game.Game;
import game.entity.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputHandler implements KeyListener, MouseListener {

	public boolean[] keys;
    public Point clicked;
	
	public InputHandler(Game game, Player player) {
		game.addKeyListener(this);
        game.addMouseListener(this);

		keys = new boolean[KeyEvent.KEY_LAST];
		for (int i = 0; i < keys.length; i++)
			keys[i] = false;

        clicked = new Point();
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
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
