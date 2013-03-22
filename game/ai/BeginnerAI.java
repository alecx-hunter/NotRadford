package game.ai;

import game.Game;
import game.entity.Enemy;
import game.graphics.Level;

import java.util.Random;

public class BeginnerAI extends EnemyAI {

    private double shootTimer;

    public BeginnerAI(Enemy enemy, Level level) {
        super(enemy, level);

        shootTimer = 800;
    }

    public void update(double diff) {
        if (shootTimer <= diff) {
            shoot();
            shootTimer = 800;
        } else shootTimer -= diff;

        if (path.isEmpty()) {
            int directionDiff = 160;
            Random random = new Random();
            switch (random.nextInt(4)) {
                // UP
                case 0:
                    if (Game.bounds.contains(me.getX(), me.getY() - directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.tiles[me.getX()][me.getY() - directionDiff].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getY() - directionDiff);
                    break;
                // RIGHT
                case 1:
                    if (Game.bounds.contains(me.getX() + directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.tiles[me.getX() + directionDiff][me.getY()].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getX() + directionDiff, me.getCenterY());
                    break;
                // DOWN
                case 2:
                    if (Game.bounds.contains(me.getX(), me.getY() + directionDiff, me.WIDTH, me.HEIGHT) &&
                            level.tiles[me.getX()][me.getY() + directionDiff].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getY() + directionDiff);
                    break;
                // LEFT
                case 3:
                    if (Game.bounds.contains(me.getX() - directionDiff, me.getY(), me.WIDTH, me.HEIGHT) &&
                            level.tiles[me.getX() - directionDiff][me.getY()].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getX() - directionDiff, me.getCenterY());
                    break;
            }
        } else
            me.traversePath(path);
    }
}
