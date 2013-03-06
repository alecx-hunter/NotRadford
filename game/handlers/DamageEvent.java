package game.handlers;

import game.Game;
import game.entity.*;
import game.graphics.Text;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class DamageEvent {

	private final Entity hitUnit;
	private Point point;
	private final int damage;
	private int ticks;
	
	public DamageEvent(Entity hit, int damage) {
		hitUnit = hit;
		point = new Point(hitUnit.getCenterX(), hitUnit.getCenterY());
		this.damage = damage;
		ticks = 30;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public Entity getHitUnit() {
		return hitUnit;
	}
	
	public void tick() {
		point.y -= 2;
		ticks--;
	}
	
	public int getTicks() {
		return ticks;
	}
	
	public void render(Graphics2D g) {
		String text = "-" + String.valueOf(damage);

        Font prev = g.getFont();
        g.setFont(new Font(prev.getFontName(), Font.PLAIN, 16));

        Text.drawOutline(g, text, Color.BLACK, hitUnit.getX(), point.y);
        g.setColor(Color.RED);
        g.drawString(text, hitUnit.getX(), point.y);
        g.setFont(prev);
	}
	
}
