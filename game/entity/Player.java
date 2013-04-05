package game.entity;

import game.Game;
import game.entity.projectile.CircleProjectile;
import game.graphics.SpriteSheet;
import game.handlers.InputHandler;
import game.logging.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

/**
 * The "human controlled" character in the game.
 * It takes input from the keyboard and manipulates the
 * player to move them around and shoot projectiles at
 * enemies.
 */
public class Player extends Entity {

    private InputHandler input;
    private Image healthIcon, greyHealthIcon;
    private double shootTimer;

    public Player(int x, int y, Game game) {
        super(x, y, game);
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
        speed = 4;
        maxHealth = 5;

        shootTimer = 500;

        sprites = new SpriteSheet("/res/images/player.png", 4, 4);
        sprite = sprites.getSprite(0);
        HEIGHT = sprite.getHeight(null) * 2;
        WIDTH = sprite.getWidth(null) * 2;

        init();

        try {
            healthIcon = ImageIO.read(getClass().getResourceAsStream("/res/images/heart.png"));
            greyHealthIcon = ImageIO.read(getClass().getResourceAsStream("/res/images/greyHeart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Attaches the input to this object
     * @param input Input
     */
    public void setInput(InputHandler input) {
        this.input = input;
    }

    /**
     * Sends out a projectile in the given direction
     * @param d The direction the projectile should move in
     */
    public void sendProjectile(Direction d) {
        game.addEntity(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 7, 3, 8));
    }

    /**
     * Must call the parents update method for updating it's bounds and any
     * damage occurring.
     *
     * Uses the input from the keyboard to send out projectiles and move the
     * player around the game.
     * @param diff The time difference in milliseconds since the last update.
     */
    public void update(double diff) {
        super.update(diff);

        if (shootTimer <= diff) {
            if (input.keys.containsKey(KeyEvent.VK_UP))
                sendProjectile(Direction.UP);
            else if (input.keys.containsKey(KeyEvent.VK_DOWN))
                sendProjectile(Direction.DOWN);
            else if (input.keys.containsKey(KeyEvent.VK_LEFT))
                sendProjectile(Direction.LEFT);
            else if (input.keys.containsKey(KeyEvent.VK_RIGHT))
                sendProjectile(Direction.RIGHT);
            shootTimer = 500;

        } else shootTimer -= diff;

        int[] keys = { KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D };

        int latestKey = 0;
        long mostRecent = 0;

        for (int i = 0; i < keys.length; i++)
            if (input.keys.containsKey(keys[i]))
                if (mostRecent <
                        input.keys.get(keys[i])) {
                    mostRecent = input.keys.get(keys[i]);
                    latestKey = keys[i];
                }

        moving = false;

        switch (latestKey) {
            case KeyEvent.VK_W:
                move(Direction.UP, false);
                break;
            case KeyEvent.VK_S:
                move(Direction.DOWN, false);
                break;
            case KeyEvent.VK_D:
                move(Direction.RIGHT, false);
                break;
            case KeyEvent.VK_A:
                move(Direction.LEFT, false);
                break;
        }

        if (health == 0)
            game.reset();
    }

    /**
     * Must call the parents render method to keep track of
     * any damage events.
     * @param g The Graphics object to draw on
     */
    public void render(Graphics2D g) {
        super.render(g);

        for (int i = 0; i < maxHealth; i++)
            g.drawImage(greyHealthIcon, i * 20 + 10, 10, greyHealthIcon.getWidth(null), greyHealthIcon.getHeight(null), null);

        for (int i = 0; i < health; i++)
            g.drawImage(healthIcon, i * 20 + 10, 10, healthIcon.getWidth(null), healthIcon.getHeight(null), null);

    }

}
