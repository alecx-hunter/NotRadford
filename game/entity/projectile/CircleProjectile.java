package game.entity.projectile;

import game.Game;
import game.entity.Direction;
import game.entity.Entity;
import game.logging.Log;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.net.URL;

public class CircleProjectile extends Projectile {

    public CircleProjectile(int x, int y, Game game, Entity owner, Direction direction, int size, int power, int speed) {
        super(x, y, game, owner, direction);

        WIDTH = size;
        HEIGHT = size;
        this.power = power;
        this.speed = speed;

        init();

        try {
            Clip clip = AudioSystem.getClip();
            sound = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/res/sounds/pew.wav"));
            clip.open(sound);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
