package game.entity;

import game.Game;
import game.ai.*;
import game.entity.projectile.ChalkProjectile;
import game.entity.projectile.CircleProjectile;
import game.entity.projectile.FProjectile;
import game.entity.projectile.ProjectileType;
import game.graphics.levels.Level;

import java.awt.*;

/**
 * The Enemy entity will be the object that will be fighting
 * against the player. Depending on how the Enemy is constructed,
 * it can have a varying amount of difficulty.
 */
public class Enemy extends Entity {

    private EnemyAI enemyAI;
    private ProjectileType projectile;

    /**
     * Constructor
     * @param x X coordinate
     * @param y Y coordinate
     * @param game The game
     * @param level Current Level
     * @param ai Which level of AI should be used
     */
    public Enemy(int x, int y, Game game, Level level, AI ai, ProjectileType projectile) {
        super(x, y, game);

        WIDTH = 56;
        HEIGHT = 56;
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
        speed = 3;
        maxHealth = 25;

        switch (ai) {
            case BEGINNER:
                enemyAI = new BeginnerAI(this, level);
                break;
            case INTERMEDIATE:
                enemyAI = new IntermediateAI(this, level);
                break;
            case ADVANCED:
                enemyAI = new AdvancedAI(this, game);
                break;
        }

        this.projectile = projectile;
        init();
    }

    /**
     * Provides access to the Player
     * @return The player
     */
    public Player getPlayer() {
        return game.getPlayer();
    }

    /**
     * Adds a projectile in the game in the given direction
     * @param d Direction the projectile should move in
     */
    public void shoot(Direction d) {
        switch (projectile) {

            case CIRCLE:
                game.addEntity(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 8, 1, 9));
                break;
            case CHALK:
                game.addEntity(new ChalkProjectile(getCenterX(), getCenterY(), game, this, d, 9));
                break;
            case F:
                game.addEntity(new FProjectile(getCenterX(), getCenterY(), game, this, d, 9));
                break;
        }
    }

    /**
     * Must call the parents update method to update it's bounds and any
     * damage that may be occurring.
     * @param diff The time difference in milliseconds since the last update.
     */
    public void update(double diff) {
        super.update(diff);

        if (enemyAI != null)
            enemyAI.update(diff);

        if (health == 0)
            game.removeEntity(this);
    }

    /**
     * Must call the parents render method before to keep track
     * of damage events.
     * @param g The Graphics object to draw on
     */
    public void render(Graphics2D g) {
        super.render(g);

        g.setColor(Color.RED);
        g.fillRect(getX() - 2, getY() - 13, WIDTH + 4, 10);
        g.setColor(Color.GREEN);
        g.fillRect(getX(), getY() - 11, (int)((double)health/(double)maxHealth * WIDTH), 6);
    }
}
