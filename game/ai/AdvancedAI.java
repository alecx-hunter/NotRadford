package game.ai;

import game.Game;
import game.entity.Direction;
import game.entity.Enemy;

import java.awt.Rectangle;
import java.util.Random;

public class AdvancedAI extends EnemyAI {

    public AdvancedAI(Enemy enemy, Game game) {
        super(enemy, game.getLevel());

        shootTimer = 800;
    }

    public void shoot() {
        int dx = me.getCenterX() - me.getPlayer().getCenterX();
        int dy = me.getCenterY() - me.getPlayer().getCenterY();

        if (Math.abs(dx) < Math.abs(dy)) {
            if (dy < 0)
                me.shoot(Direction.DOWN);
            else
                me.shoot(Direction.UP);
        }
        // This includes the case where they are equal
        else {
            if (dx < 0)
                me.shoot(Direction.RIGHT);
            else
                me.shoot(Direction.LEFT);
        }
    }

    public void update(double diff) {
        if (shootTimer <= diff) {
            shoot();
            shootTimer = 500;
        } else shootTimer -= diff;

        if (path.isEmpty()) {
            int directionDiff = 200;
            Random random = new Random();
            switch (random.nextInt(4)) {
                // UP
                case 0:
                    if (Game.bounds.contains(me.getX(), me.getY() - directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX(), me.getY() - directionDiff, me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getX(), me.getY(), me.getX(), me.getY() - directionDiff);
                    break;
                // RIGHT
                case 1:
                    if (Game.bounds.contains(me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getX(), me.getY(), me.getX() + directionDiff, me.getY());
                    break;
                // DOWN
                case 2:
                    if (Game.bounds.contains(me.getX(), me.getY() + directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX(), me.getY() + directionDiff, me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getX(), me.getY(), me.getX(), me.getY() + directionDiff);
                    break;
                // LEFT
                case 3:
                    if (Game.bounds.contains(me.getX() - directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.isTraversable(new Rectangle(me.getX() - directionDiff, me.getY(), me.WIDTH, me.HEIGHT)))
                        path.generatePath(me.getX(), me.getY(), me.getX() - directionDiff, me.getY());
                    break;
            }
        } else
            me.traversePath(path);
    }
}
