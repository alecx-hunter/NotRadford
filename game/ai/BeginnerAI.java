package game.ai;

import game.entity.Enemy;

public class BeginnerAI extends EnemyAI {

    private double shootTimer;

    public BeginnerAI(Enemy enemy) {
        super(enemy);

        shootTimer = 800;
    }

    public void update(double diff) {
        if (shootTimer <= diff) {
            shoot();
            shootTimer = 800;
        } else shootTimer -= diff;
    }
}
