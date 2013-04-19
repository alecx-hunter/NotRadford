package game.ai;

import game.Game;
import game.entity.Direction;
import game.entity.Enemy;
import game.graphics.levels.Level;

import java.awt.*;
import java.util.Random;

/**
 * Very simple AI for an Enemy. It shoots based on a pretty simple algorithm
 * to determine which direction would be best to shoot. It also includes some
 * code for movement, which is just choosing a random direction to move in.
 * Once the destination is reached it simply chooses another random direction
 * to move.
 */
public class BeginnerAI extends EnemyAI {

    public BeginnerAI(Enemy enemy, Level level) {
        super(enemy, level);

        shootTimer = 1200;
    }

    public void shoot() {
        int dx = me.getCenterX() - me.getPlayer().getCenterX();
        int dy = me.getCenterY() - me.getPlayer().getCenterY();

        Random r = new Random();
        if (Math.abs(dx) < Math.abs(dy)) {
            if (dy < 0) {
                if (r.nextInt(2) == 0) me.shoot(Direction.DOWN);
                else me.shoot(Direction.UP);
            }
            else {
                if (r.nextInt(2) == 0) me.shoot(Direction.UP);
                else me.shoot(Direction.DOWN);
            }
        }
        // This includes the case where they are equal
        else {
            if (dx < 0) {
                if (r.nextInt(2) == 0) me.shoot(Direction.RIGHT);
                else me.shoot(Direction.LEFT);
            }
            else {
                if (r.nextInt(2) == 0) me.shoot(Direction.LEFT);
                else me.shoot(Direction.RIGHT);
            }
        }
    }

    /**
     * Called on each game update. Contains the AI for this Enemy, consisting
     * of movement and shooting projectiles
     * @param diff Time in milliseconds since last update
     */
    public void update(double diff) {
        if (shootTimer <= diff) {
            shoot();
            shootTimer = 1200;
        } else shootTimer -= diff;

        if (path.isEmpty()) {
            int directionDiff = 160;
            Random random = new Random();
            switch (random.nextInt(4)) {
                // UP
                case 0:
                    if (Game.bounds.contains(me.getX(), me.getY() - directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX(), me.getY() - directionDiff, me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getY() - directionDiff);
                    break;
                // RIGHT
                case 1:
                    if (Game.bounds.contains(me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getX() + directionDiff, me.getCenterY());
                    break;
                // DOWN
                case 2:
                    if (Game.bounds.contains(me.getX(), me.getY() + directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX(), me.getY() + directionDiff, me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getY() + directionDiff);
                    break;
                // LEFT
                case 3:
                    if (Game.bounds.contains(me.getX() - directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX() - directionDiff, me.getY(), me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getX() - directionDiff, me.getCenterY());
                    break;
            }
        } else
            me.traversePath(path);
    }
}
