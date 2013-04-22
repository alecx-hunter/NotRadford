package game.graphics.levels;

import game.Game;
import game.ai.AI;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.projectile.ProjectileType;
import game.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level1 extends Level {

    public Level1(Game game) {
        super(game);

        SpriteSheet sprites = new SpriteSheet("/res/images/htay.png", 4, 4);
        enemy = new Enemy(600, 600, game, this, AI.BEGINNER, ProjectileType.CHALK);
        enemy.setSprites(sprites);
        enemy.setSprite(sprites.getSprite(0));
        enemy.setMaxHealth(23);
        enemy.restoreHealth();
        game.addEntity(enemy);
    }

    public void loadLevel() {
        SpriteSheet sprites = new SpriteSheet("/res/images/desk2.png", 1, 1);
        Image desk = sprites.getSprite(0);
        sprites = new SpriteSheet("/res/images/floorTile.png", 1, 1);
        Image floor = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 2)
            for (int y = 0; y < HEIGHT; y += 2)
                addObject((BufferedImage)floor, x, y, true);
        for (int x = 10; x < WIDTH - 20; x += 20)
            for (int y = 20; y <= 60; y += 20)
            addObject((BufferedImage)desk, x, y, false);
        sprites = new SpriteSheet("/res/images/back-wall.png", 1, 1);
        Image wall = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 8)
            addObject((BufferedImage)wall, x, 0, false);
        sprites = new SpriteSheet("/res/images/chalkboard.png", 1, 1);
        Image board = sprites.getSprite(0);
        addObject((BufferedImage)board, 50, 1, false);
    }

    public void loadNext() {
        game.setLevel(new Level2(game));
        Player p = game.getPlayer();
        p.setMaxHealth(p.getMaxHealth() + 1);
        p.addHealth(p.getMaxHealth() - p.getHealth());
    }

}
