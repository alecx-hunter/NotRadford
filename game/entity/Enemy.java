package game.entity;

import game.Game;
import game.ai.AI;
import game.ai.BeginnerAI;
import game.ai.EnemyAI;
import game.entity.projectile.CircleProjectile;
import game.graphics.Level;
import game.pathfinding.Path;

import java.awt.*;

public class Enemy extends Entity {

    private double searchTimer;
    private Path path;
    private EnemyAI enemyAI;

    public Enemy(int x, int y, Game game, Level level, AI ai) {
        super(x, y, game);

        WIDTH = 56;
        HEIGHT = 56;
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
        speed = 3;
        health = 100;
        searchTimer = 500;

        path = new Path(level);

        switch (ai) {
            case BEGINNER:
                enemyAI = new BeginnerAI(this);
                break;
        }
        init();
    }

    public Player getPlayer() {
        return game.getPlayer();
    }

    public void shoot(Direction d) {
        game.addEntity(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 8, 1, 9));
    }

    public void update(double diff) {
        super.update(diff);

        /*if (searchTimer <= diff) {
            path.generatePath(position.x, position.y, target.getX(), target.getY());

            searchTimer = 500;
        } else searchTimer -= diff;
        if (path != null && distanceTo(target) > 25.0)
            traversePath(path);*/
        if (enemyAI != null)
            enemyAI.update(diff);

        if (health == 0)
            game.removeEntity(this);
    }

    public void render(Graphics2D g) {
        super.render(g);
        g.setColor(Color.BLACK);

        g.fillRect(getX(), getY(), WIDTH, HEIGHT);
    }
}
