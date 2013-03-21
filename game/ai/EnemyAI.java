package game.ai;


import game.entity.Direction;
import game.entity.Enemy;
import game.graphics.Level;
import game.pathfinding.Path;

public abstract class EnemyAI {

    protected Enemy me;
    protected double shootTimer;
    protected Level level;
    protected Path path;

    public EnemyAI(Enemy enemy, Level level) {
        me = enemy;
        this.level = level;

        path = new Path(level);
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

    public abstract void update(double diff);

}
