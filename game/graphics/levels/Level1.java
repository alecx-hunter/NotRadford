package game.graphics.levels;

import game.Game;
import game.ai.AI;
import game.entity.Enemy;
import game.graphics.SpriteSheet;

import java.awt.image.BufferedImage;

public class Level1 extends Level {

    public Level1(Game game) {
        super(game);

        SpriteSheet sprites = new SpriteSheet("/res/images/htay.png", 4, 4);
        enemy = new Enemy(600, 500, game, this, AI.BEGINNER);
        enemy.setSprites(sprites);
        enemy.setSprite(sprites.getSprite(0));
        enemy.setPower(2);
        game.addEntity(enemy);
    }

    public void loadLevel() {
        SpriteSheet sprites = new SpriteSheet("/res/images/desk2.png", 1, 1);
        BufferedImage desk = sprites.getSprite(0);
        sprites = new SpriteSheet("/res/images/floorTile.png", 1, 1);
        BufferedImage floor = sprites.getSprite(0);
        for (int x = 0; x < WIDTH; x += 2)
            for (int y = 0; y < HEIGHT; y += 2)
                addObject(floor, x, y, true);
        addObject(desk, 55, 40, false);
        addObject(desk, 40, 40, false);
    }

}
