package game.entity;

import game.Game;
import game.entity.projectile.CircleProjectile;
import game.handlers.InputHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {
	
	private InputHandler input;
	private BufferedImage healthIcon;
	private double shootTimer;
	
	public Player(int x, int y, Game game) {
		super(x, y, game);
		WIDTH = 16;
		HEIGHT = 16;
		bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
		speed = 4;
		health = 3;
		
		shootTimer = 20;
		
		try {
			healthIcon = ImageIO.read(new File("res/images/heart.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setInput(InputHandler input) {
		this.input = input;
	}
	
	public void sendProjectile(Direction d) {
		game.addProjectile(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 7, 1, 8));
	}
	
	public void update(double diff) {
		bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
		
		if (shootTimer <= diff) {
			if (input.keys[KeyEvent.VK_UP])
				sendProjectile(Direction.UP);
			else if (input.keys[KeyEvent.VK_DOWN])
				sendProjectile(Direction.DOWN);
			else if (input.keys[KeyEvent.VK_LEFT])
				sendProjectile(Direction.LEFT);
			else if (input.keys[KeyEvent.VK_RIGHT])
				sendProjectile(Direction.RIGHT);
			shootTimer = 20;
		} else shootTimer -= diff;
		
		int directions = 0;
		if (input.keys[KeyEvent.VK_W]) directions++;
		if (input.keys[KeyEvent.VK_S]) directions++;
		if (input.keys[KeyEvent.VK_A]) directions++;
		if (input.keys[KeyEvent.VK_D]) directions++;
		
		if (input.keys[KeyEvent.VK_W])
			move(Direction.UP, directions > 1);
		if (input.keys[KeyEvent.VK_S])
			move(Direction.DOWN, directions > 1);
		if (input.keys[KeyEvent.VK_A])
			move(Direction.LEFT, directions > 1);
		if (input.keys[KeyEvent.VK_D])
			move(Direction.RIGHT, directions > 1);
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(getX(), getY(), HEIGHT, WIDTH);
		
		for (int i = 0; i < health; i++) 
			g.drawImage(healthIcon, i*20 + 10, 10, 16, 14, null);
	}
	
}
