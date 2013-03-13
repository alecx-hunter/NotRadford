package game.entity;

import game.Game;
import game.entity.projectile.CircleProjectile;
import game.handlers.InputHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    private InputHandler input;
    private BufferedImage healthIcon;
    private double shootTimer;

    public Player(int x, int y, Game game) {
        super(x, y, game);
        WIDTH = 56;
        HEIGHT = 56;
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));
        speed = 4;
        health = 3;

        shootTimer = 500;

        init();

        try {
            healthIcon = ImageIO.read(new File("src/res/images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInput(InputHandler input) {
        this.input = input;
    }

    public void sendProjectile(Direction d) {
        game.addEntity(new CircleProjectile(getCenterX(), getCenterY(), game, this, d, 7, 3, 8));
    }

    public void update(double diff) {
        super.update(diff);

        if (shootTimer <= diff) {
            if (input.keys[KeyEvent.VK_UP])
                sendProjectile(Direction.UP);
            else if (input.keys[KeyEvent.VK_DOWN])
                sendProjectile(Direction.DOWN);
            else if (input.keys[KeyEvent.VK_LEFT])
                sendProjectile(Direction.LEFT);
            else if (input.keys[KeyEvent.VK_RIGHT])
                sendProjectile(Direction.RIGHT);
            shootTimer = 500;

        } else shootTimer -= diff;

        int directions = 0;
        if (input.keys[KeyEvent.VK_W]) directions++;
        if (input.keys[KeyEvent.VK_S]) directions++;
        if (input.keys[KeyEvent.VK_A]) directions++;
        if (input.keys[KeyEvent.VK_D]) directions++;

        if (input.keys[KeyEvent.VK_W])
            move(Direction.UP, directions > 1);
        if (input.keys[KeyEvent.VK_S])
            move(Direction.DOWN, directions > 1);
        if (input.keys[KeyEvent.VK_A])
            move(Direction.LEFT, directions > 1);
        if (input.keys[KeyEvent.VK_D])
            move(Direction.RIGHT, directions > 1);
    }

    public void render(Graphics2D g) {
        super.render(g);

        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY(), WIDTH, HEIGHT);

        for (int i = 0; i < health; i++)
            g.drawImage(healthIcon, i * 20 + 10, 10, 16, 14, null);
    }

}
