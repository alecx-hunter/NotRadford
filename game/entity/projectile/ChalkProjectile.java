package game.entity.projectile;


import game.Game;
import game.entity.Direction;
import game.entity.Entity;
import game.graphics.SpriteSheet;

import java.awt.*;

public class ChalkProjectile extends Projectile {

    private Image img;

    public ChalkProjectile(int x, int y, Game game, Entity owner, Direction direction, int speed) {
        super(x, y, game, owner, direction);

        this.power = 1;
        this.speed = speed;

        SpriteSheet sprites = direction == Direction.LEFT || direction == Direction.RIGHT ? new SpriteSheet("/res/images/chalk.png", 1, 1) : new SpriteSheet("/res/images/chalk2.png", 1, 1);
        img = sprites.getSprite(0);
        WIDTH = img.getWidth(null);
        HEIGHT = img.getHeight(null);

        init();
    }

    public void render(Graphics2D g) {
        super.render(g);

        g.drawImage(img, getX(), getY(), null);
    }

    public void update(double diff) {
        super.update(diff);

        move(direction);
        checkCollision();
    }
}
