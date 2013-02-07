package game.entity;

import game.Game;
import game.input.InputHandler;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
	
	private Point pos;
	private Rectangle bounds;
	private int speed;
	private InputHandler input;
	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	private static final int MAX_X = (Game.WIDTH * Game.SCALE) - WIDTH + 10;
	private static final int MAX_Y = (Game.HEIGHT * Game.SCALE) - HEIGHT + 10;
	
	public Player(int x, int y) {
		pos = new Point(x, y);
		
		bounds = new Rectangle(pos.x, pos.y, WIDTH, HEIGHT);
		speed = 4;
	}
	
	public void setInput(InputHandler input) {
		this.input = input;
	}
	
	public void update() {
		if (input.keys[KeyEvent.VK_W] || input.keys[KeyEvent.VK_UP])
			moveUp();
		if (input.keys[KeyEvent.VK_S] || input.keys[KeyEvent.VK_DOWN])
			moveDown();
		if (input.keys[KeyEvent.VK_A] || input.keys[KeyEvent.VK_LEFT])
			moveLeft();
		if (input.keys[KeyEvent.VK_D] || input.keys[KeyEvent.VK_RIGHT])
			moveRight();
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getX() {
		return pos.x;
	}
	
	public int getY() {
		return pos.y;
	}
	
	public void moveRight() {
		pos.x = pos.x + speed > MAX_X ? MAX_X : pos.x + speed;
	}
	
	public void moveLeft() {
		pos.x = pos.x - speed < 0 ? 0 : pos.x - speed;
	}
	
	public void moveUp() {
		pos.y = pos.y - speed < 0 ? 0 : pos.y - speed;
	}
	
	public void moveDown() {
		pos.y = pos.y + speed > MAX_Y ? MAX_Y : pos.y + speed;
	}

}
