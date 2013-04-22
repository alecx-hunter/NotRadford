package game.ai;

import game.Game;
import game.entity.Direction;
import game.entity.Enemy;
import game.graphics.levels.Level;

import java.awt.*;
import java.util.Random;

public class IntermediateAI extends EnemyAI {

    private boolean left;

    public IntermediateAI(Enemy enemy, Level level) {
        super(enemy, level);

        shootTimer = 500;
        left = true;
    }
    public void shoot() {
        int dx = me.getCenterX() - me.getPlayer().getCenterX();
        int dy = me.getCenterY() - me.getPlayer().getCenterY();

        Random r = new Random();
        if (Math.abs(dx) < Math.abs(dy)) {
            if (dy < 0) {
                if (r.nextInt(4) < 3) me.shoot(Direction.DOWN);
                else me.shoot(Direction.UP);
            }
            else {
                if (r.nextInt(4) < 3) me.shoot(Direction.UP);
                else me.shoot(Direction.DOWN);
            }
        }
        // This includes the case where they are equal
        else {
            if (dx < 0) {
                if (r.nextInt(4) < 3) me.shoot(Direction.RIGHT);
                else me.shoot(Direction.LEFT);
            }
            else {
                if (r.nextInt(4) < 3) me.shoot(Direction.LEFT);
                else me.shoot(Direction.RIGHT);
            }
        }
    }

    public void update(double diff) {
        if (shootTimer <= diff) {
            shoot();
            shootTimer = 500;
        } else shootTimer -= diff;

        if (path.isEmpty()) {
            int directionDiff = 300;

            if (Game.bounds.contains(left ? me.getX() - directionDiff : me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                    level.isTraversable(new Rectangle(left ? me.getX() - directionDiff : me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT)))
                    path.generatePath(me.getX(), me.getY(), left ? me.getX() - directionDiff : me.getX() + directionDiff, me.getY());
            left = !left;

        } else
            me.traversePath(path);
    }
}
