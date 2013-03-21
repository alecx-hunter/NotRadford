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
                    if (me.getCenterY() - directionDiff >= 0 &&
                            level.tiles[me.getCenterX()][me.getCenterY() - directionDiff].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getCenterY() - directionDiff);
                    break;
                // RIGHT
                case 1:
                    if (me.getCenterX() + directionDiff <= Game.WIDTH * Game.SCALE &&
                            level.tiles[me.getCenterX() + directionDiff][me.getCenterY()].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX() + directionDiff, me.getCenterY());
                    break;
                // DOWN
                case 2:
                    if (me.getCenterY() + directionDiff <= Game.HEIGHT * Game.SCALE &&
                            level.tiles[me.getCenterX()][me.getCenterY() + directionDiff].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX(), me.getCenterY() + directionDiff);
                    break;
                // LEFT
                case 3:
                    if (me.getCenterX() - directionDiff >= 0 &&
                            level.tiles[me.getCenterX() - directionDiff][me.getCenterY()].isTraversable())
                        path.generatePath(me.getCenterX(), me.getCenterY(), me.getCenterX() - directionDiff, me.getCenterY());
                    break;
            }
        } else
            me.traversePath(path);
    }
}
