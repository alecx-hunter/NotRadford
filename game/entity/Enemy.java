package game.entity;

import game.Game;
import game.ai.AI;
import game.ai.BeginnerAI;
import game.ai.EnemyAI;
import game.entity.projectile.CircleProjectile;
import game.graphics.Level;

import java.awt.*;

/**
 * The Enemy entity will be the object that will be fighting
 * against the player. Depending on how the Enemy is constructed,
 * it can have a varying amount of difficulty.
 */
public class Enemy extends Entity {

    private EnemyAI enemyAI;

    /**
     * Constructor
     * @param x X coordinate
     * @param y Y coordinate
     * @param game The game
     * @param level Current Level
     * @param ai Which level of AI should be used
     */
    public Enemy(int x, int y, Game game, Level level, AI ai) {
        super(x, y, game);

        WIDTH = 56;
        HEIGHT = 56;
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
        speed = 3;
        maxHealth = 100;

        switch (ai) {
            case BEGINNER:
                enemyAI = new BeginnerAI(this, level);
                break;
        }
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
        game.addEntity(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 8, 1, 9));
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
        g.setColor(Color.BLACK);

        g.fillRect(getX(), getY(), WIDTH, HEIGHT);
    }
}
