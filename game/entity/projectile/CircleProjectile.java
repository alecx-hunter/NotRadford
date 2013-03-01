package game.entity.projectile;

import game.Game;
import game.entity.Entity;
import game.entity.Entity.Direction;
import game.entity.projectile.*;

import java.awt.Color;
import java.awt.Graphics2D;

public class CircleProjectile extends Projectiles {

	public CircleProjectile(int x, int y, Game game, Entity owner, Direction direction, int size, int power, int speed) {
		super(x, y, game, owner, direction);
		
		width = size;
		height = size;
		this.power = power;
		this.speed = speed;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval(getX(), getY(), width, height);
	}

	public void update(double diff) {
		move(direction);
		checkCollision();
	}

}
