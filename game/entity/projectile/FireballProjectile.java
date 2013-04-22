package game.entity.projectile;

import game.Game;
import game.entity.Direction;
import game.entity.Entity;
import game.graphics.SpriteSheet;

import java.awt.*;

public class FireballProjectile extends Projectile {

    private Image img;

    public FireballProjectile(int x, int y, Game game, Entity owner, Direction direction, int speed) {
        super(x, y, game, owner, direction);

        this.speed = speed;
        this.power = 2;

        SpriteSheet sprites = new SpriteSheet("/res/images/fireballs.png", 2, 2);
        switch (direction) {
            case UP:
                img = sprites.getSprite(3);
                break;
            case DOWN:
                img = sprites.getSprite(0);
                break;
            case LEFT:
                img = sprites.getSprite(2);
                break;
            case RIGHT:
                img = sprites.getSprite(1);
                break;
        }

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
