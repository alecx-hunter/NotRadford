package game.handlers;

import game.Game;
import game.entity.Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class DamageEvent {

	private Entity hitUnit;
	private Point point;
	private int damage;
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
		g.setColor(Color.RED);

		String text = "-" + String.valueOf(damage);
		g.drawString(text, hitUnit.getX(), point.y);

	}
	
}
