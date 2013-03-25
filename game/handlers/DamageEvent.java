package game.handlers;

import game.entity.Entity;
import game.graphics.Shadow;
import game.graphics.Text;

import java.awt.*;

/**
 * This class is used to display numbers when
 * the player or enemy is hit with a projectile.
 *
 * Whenever a player or enemy is hit, a DamageEvent
 * is created for that Entity and added to a list, which
 * are displayed above them.
 */
public class DamageEvent {

    private final Entity hitUnit;
    private int y;
    private final int damage, x;
    private int ticks;

    public DamageEvent(Entity hit, int damage) {
        hitUnit = hit;
        y = hitUnit.getY();
        x = hitUnit.getCenterX();
        this.damage = damage;
        ticks = 30;
    }

    public int getDamage() {
        return damage;
    }

    public Entity getHitUnit() {
        return hitUnit;
    }

    /**
     * Each update of an Entity this method must be called.
     *
     * It translates the text up two pixels and decrements
     * the tick amount.
     *
     * After this is called, there should be a check to see
     * if ticks have reached 0. If it has, remove the event
     * from the list.
     */
    public void tick() {
        y -= 2;
        ticks--;
    }

    /**
     * @return The number of ticks left
     */
    public int getTicks() {
        return ticks;
    }

    /**
     * Renders the damage text onto the screen
     * @param g The graphics object to draw onto
     */
    public void render(Graphics2D g) {
        String text = "-" + String.valueOf(damage);

        Font prev = g.getFont();
        g.setFont(new Font(prev.getFontName(), Font.BOLD, 16));

        g.setColor(Color.RED);
        Text.drawShadow(g, text, Shadow.BOTTOM_RIGHT, x, y);
        g.drawString(text, x, y);
        g.setFont(prev);
    }

}
