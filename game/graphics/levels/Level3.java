package game.graphics.levels;

import game.Game;
import game.ai.AI;
import game.entity.Enemy;
import game.entity.projectile.ProjectileType;
import game.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level3 extends Level {

    public Level3(Game game) {
        super(game);

        game.getPlayer().moveTo(0, 80);

        SpriteSheet sprites = new SpriteSheet("/res/images/ray.png", 4, 4);
        enemy = new Enemy(750, 650, game, this, AI.ADVANCED, ProjectileType.CIRCLE);
        enemy.setSprites(sprites);
        enemy.setSprite(sprites.getSprite(0));
        enemy.setMaxHealth(45);
        enemy.restoreHealth();
        game.addEntity(enemy);

    }

    public void loadLevel() {
        SpriteSheet sprites = new SpriteSheet("/res/images/floorTile.png", 1, 1);
        Image floor = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 2)
            for (int y = 0; y < HEIGHT; y += 2)
                addObject((BufferedImage)floor, x, y, true);

        sprites = new SpriteSheet("/res/images/back-wall.png", 1, 1);
        Image wall = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 8)
            addObject((BufferedImage)wall, x, 0, false);

        sprites = new SpriteSheet("/res/images/door.png", 1, 1);
        Image door = sprites.getSprite(0);
        addObject((BufferedImage)door, 106, 1, false);

        sprites = new SpriteSheet("/res/images/raydesk.png", 1, 1);
        Image raydesk = sprites.getSprite(0);
        addObject((BufferedImage)raydesk, 95, 68, false);

        sprites = new SpriteSheet("/res/images/greydesk.png", 1, 1);
        Image desk = sprites.getSprite(0);
        for (int x = 1; x < 1 + 16*3; x += 16)
            for (int y = 20; y < 20 + 20*3; y += 20)
                addObject((BufferedImage)desk, x, y, false);
        for (int x = 72; x < 72 + 16*3; x += 16)
            for (int y = 20; y < 20 + 20*2; y += 20)
                addObject((BufferedImage)desk, x, y, false);
    }

    public void loadNext() {
    }
}
