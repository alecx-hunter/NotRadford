package game.entity;

import game.Game;
import game.graphics.Level;
import game.handlers.DamageEvent;
import game.pathfinding.Path;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Enemy extends Entity {
	
	private double searchTimer;
	private Player target;
	private Path path;

	public Enemy(int x, int y, Game game, Player player, Level level) {
		super(x, y, game);
		
		WIDTH = 20;
		HEIGHT = 20;
		bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
		speed = 3;
		health = 10;
		searchTimer = 60;
		
		target = player;
		path = new Path(level);
	}

	public void update(double diff) {
		bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
		
		if (searchTimer <= diff) {
			path.generatePath(position.x, position.y, target.getX(), target.getY());
			searchTimer = 60;
		} else searchTimer -= diff;
		if (path != null && distanceTo(target) > 25.0)
			traversePath(path);
		
		for (DamageEvent de : damageEvents)
			de.tick();
		
		for (int i = 0; i < damageEvents.size(); i++)
			if (damageEvents.get(i).getTicks() < 0)
				damageEvents.remove(i);
		
		if (health == 0)
			game.removeEntity(this);
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		
		g.fillRect(getX(), getY(), HEIGHT, WIDTH);
		
		for (DamageEvent de : damageEvents)
			de.render(g);
	}
}
