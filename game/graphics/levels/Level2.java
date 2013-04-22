package game.graphics.levels;

import game.Game;
import game.ai.AI;
import game.entity.Enemy;
import game.entity.Player;
import game.entity.projectile.ProjectileType;
import game.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Level2 extends Level {

    public Level2(Game game) {
        super(game);

        game.getPlayer().moveTo(0, 100);

        SpriteSheet sprites = new SpriteSheet("/res/images/chase.png", 4, 4);
        enemy = new Enemy(750, 625, game, this, AI.INTERMEDIATE, ProjectileType.F);
        enemy.setSprites(sprites);
        enemy.setSprite(sprites.getSprite(0));
        enemy.setMaxHealth(3);
        enemy.restoreHealth();
        game.addEntity(enemy);

    }

    public void loadLevel() {
        SpriteSheet sprites = new SpriteSheet("/res/images/floorTile.png", 1, 1);
        Image floor = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 2)
            for (int y = 0; y < HEIGHT; y += 2)
                addObject((BufferedImage)floor, x, y, true);
        sprites = new SpriteSheet("/res/images/back-wall.png",1, 1);
        Image wall = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 8)
            addObject((BufferedImage)wall, x, 0, false);
        sprites = new SpriteSheet("/res/images/desk2.png", 1, 1);
        Image desk = sprites.getSprite(0);
        for (int y = 30; y <= 30 + 5*6; y += 5)
            addObject((BufferedImage)desk, 45, y, false);
        for (int y = 30; y <= 30 + 5*6; y += 5)
            addObject((BufferedImage)desk, 55, y, false);
        sprites = new SpriteSheet("/res/images/clock.png", 1, 1);
        Image clock = sprites.getSprite(0);
        addObject((BufferedImage)clock, 42, 1, false);
    }

    public void loadNext() {
        game.setLevel(new Level3(game));
        Player p = game.getPlayer();
        p.setMaxHealth(p.getMaxHealth() + 1);
        p.addHealth(p.getMaxHealth() - p.getHealth());
    }
}
