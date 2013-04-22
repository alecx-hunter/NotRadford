package game.entity.projectile;

import game.Game;
import game.entity.Direction;
import game.entity.Entity;

import java.awt.*;

public class FProjectile extends Projectile {
    public FProjectile(int x, int y, Game game, Entity owner, Direction direction, int speed) {
        super(x, y, game, owner, direction);

        this.speed = speed;
        this.power = 1;

        WIDTH = 16;
        HEIGHT = 20;

        init();
    }

    public void render(Graphics2D g) {
        super.render(g);

        Font f = g.getFont();
        g.setColor(Color.RED);
        g.setFont(f.deriveFont(18.0f).deriveFont(Font.BOLD));
        g.drawString("F", getX(), getY());
        g.setFont(f);
    }

    public void update(double diff) {
        super.update(diff);

        move(direction);
        checkCollision();
    }
}
