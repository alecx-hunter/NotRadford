package game.handlers;

import game.entity.Entity;
import game.graphics.Text;

import java.awt.*;

public class DamageEvent {

    private final Entity hitUnit;
    private int y;
    private final int damage;
    private int ticks;

    public DamageEvent(Entity hit, int damage) {
        hitUnit = hit;
        y = hitUnit.getY();
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
        y -= 2;
        ticks--;
    }

    public int getTicks() {
        return ticks;
    }

    public void render(Graphics2D g) {
        String text = "-" + String.valueOf(damage);

        Font prev = g.getFont();
        g.setFont(new Font(prev.getFontName(), Font.PLAIN, 16));

        Text.drawOutline(g, text, Color.BLACK, hitUnit.getCenterX(), y);
        g.setColor(Color.RED);
        g.drawString(text, hitUnit.getCenterX(), y);
        g.setFont(prev);
    }

}
