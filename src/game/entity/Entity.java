package game.entity;

import game.Game;
import game.handlers.DamageEvent;
import game.pathfinding.Path;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Entity {
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	public int WIDTH = 16;
	public int HEIGHT = 16;
	protected final int MAX_X = (Game.WIDTH * Game.SCALE) - WIDTH + 10;
	protected final int MAX_Y = (Game.HEIGHT * Game.SCALE) - HEIGHT + 10;
	
	protected Point position;
	protected int health;
	protected int speed;
	protected Rectangle bounds;
	protected Game game;
	protected ArrayList<DamageEvent> damageEvents;
	
	public Entity(int x, int y, Game game) {
		position = new Point(x, y);
		this.game = game;
		damageEvents = new ArrayList<DamageEvent>();
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getX() {
		return position.x;
	}
	
	public int getY() {
		return position.y;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Point getCenterPosition() {
		return new Point(position.x + (WIDTH/2), position.y + (HEIGHT/2));
	}
	
	public int getCenterX() {
		return getCenterPosition().x;
	}
	
	public int getCenterY() {
		return getCenterPosition().y;
	}
	
	public void move(Direction d, boolean multipleMovement) {
		int mod = multipleMovement ? 2 : 1;
		switch(d) {
			case UP:
				position.y = position.y - (speed/mod) < 0 ? 0 : position.y - (speed/mod);
				break;
			case DOWN:
				position.y = position.y + (speed/mod) > MAX_Y ? MAX_Y : position.y + (speed/mod);
				break;
			case RIGHT:
				position.x = position.x + (speed/mod) > MAX_X ? MAX_X : position.x + (speed/mod);
				break;
			case LEFT:
				position.x = position.x - (speed/mod) < 0 ? 0 : position.x - (speed/mod);
				break;
		}
	}
	
	public void traversePath(Path path) {
		Point p;
		int directions = 0;
		if ((p = path.getNext()) != null) {
			if (getX() < p.x) directions++;
			if (getX() > p.x) directions++;
			if (getY() < p.y) directions++;
			if (getY() > p.y) directions++;
			
			if (getX() < p.x && getX() + speed < MAX_X)
				move(Direction.RIGHT, directions > 1);
			if (getX() > p.x && getX() - speed >= 0)
				move(Direction.LEFT, directions > 1);
			if (getY() < p.y && getY() + speed < MAX_Y)
				move(Direction.DOWN, directions > 1);
			if (getY() > p.y && getY() - speed >= 0)
				move(Direction.UP, directions > 1);
		}
	}
	
	public double distanceTo(Entity e) {
		return Math.sqrt(Math.pow(Math.abs(e.getCenterX() - getCenterX()), 2.0) + Math.pow(Math.abs(e.getCenterY() - getCenterY()), 2.0));
	}
	
	public void addHealth(int amount) {
		if (amount < 0)
			damageEvents.add(new DamageEvent(this, Math.abs(amount)));
		health += amount;
		if (health < 0)
			health = 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public abstract void update(double diff);
	
	public abstract void render(Graphics2D g);

	public boolean isPlayer() {
		return getClass() == Player.class;
	}
}
