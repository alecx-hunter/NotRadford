package game.entity.projectile;

import game.Game;
import game.entity.Direction;
import game.entity.Entity;

import java.awt.*;

public class CircleProjectile extends Projectile {

    public CircleProjectile(int x, int y, Game game, Entity owner, Direction direction, int size, int power, int speed) {
        super(x, y, game, owner, direction);

        WIDTH = size;
        HEIGHT = size;
        this.power = power;
        this.speed = speed;

        init();
    }

    public void render(Graphics2D g) {
        super.render(g);

        g.setColor(Color.RED);
        g.fillOval(getX(), getY(), WIDTH, HEIGHT);
    }

    public void update(double diff) {
        super.update(diff);

        move(direction);
        checkCollision();
    }

}
