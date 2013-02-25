package game.entity.projectile;

import game.Game;
import game.entity.Entity;
import game.entity.Entity.Direction;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Projectiles {
	
	protected Point position;
	protected Game game;
	protected Rectangle bounds;
	protected Entity owner;
	protected Direction direction;
	
	protected int speed, power, width, height;
	public final int MAX_X = (Game.WIDTH * Game.SCALE) - width + 10;
	public final int MAX_Y = (Game.HEIGHT * Game.SCALE) - height + 10; 
	
	public Projectiles(int x, int y, Game game, Entity owner, Direction direction) {
		position = new Point(x, y);
		this.game = game;
		this.owner = owner;
		this.direction = direction;
	}
	
	public void move(Direction d) {
		switch(d) {
			case UP:
				position.y -= speed;
				break;
			case DOWN:
				position.y += speed;
				break;
			case RIGHT:
				position.x += speed;
				break;
			case LEFT:
				position.x -= speed;
				break;
		}
	}
	
	public void checkCollision() {
		if (position.x < 0 || position.x > MAX_X || position.y < 0 || position.y > MAX_Y)
			game.removeProjectile(this);
		
		bounds = new Rectangle(getX(), getY(), width, height);
		
		ArrayList<Entity> entities = game.getEntities();
		for (Entity e : entities) {
			if (e.isPlayer() && owner.isPlayer())
				continue;
			if (!e.isPlayer() && owner.isPlayer())
				if (e.getBounds().intersects(bounds)) {
					game.removeProjectile(this);
					dealDamage(e);
				}
			if (e.isPlayer() && !owner.isPlayer())
				if (e.getBounds().intersects(bounds)) {
					game.removeProjectile(this);
					dealDamage(e);
				}
		}
	}
	
	public void dealDamage(Entity e) {
		e.addHealth(-power);
	}
	
	public abstract void render(Graphics2D g);
	
	public abstract void update(double diff);
	
	/*
	 * Getters and Setters
	 */
	
	public int getX() {
		return position.x;
	}
	
	public int getY() {
		return position.y;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
