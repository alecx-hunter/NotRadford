package game.ai;


import game.entity.Enemy;
import game.entity.Entity;

public abstract class EnemyAI {

    protected Enemy me;
    protected double shootTimer;

    public enum AI {
        BEGINNER
    }

    public EnemyAI(Enemy enemy) {
        me = enemy;
    }

    public void shoot() {
        int dx = me.getCenterX() - me.getPlayer().getCenterX();
        int dy = me.getCenterY() - me.getPlayer().getCenterY();

        if (Math.abs(dx) < Math.abs(dy)) {
            if (dy < 0)
                me.shoot(Entity.Direction.DOWN);
            else
                me.shoot(Entity.Direction.UP);
        }
        // This includes the case where they are equal
        else {
            if (dx < 0)
                me.shoot(Entity.Direction.RIGHT);
            else
                me.shoot(Entity.Direction.LEFT);
        }
    }

    public abstract void update(double diff);

}
