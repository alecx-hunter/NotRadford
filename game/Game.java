package game;

import game.ai.AI;
import game.entity.Enemy;
import game.entity.Entity;
import game.entity.Player;
import game.graphics.levels.Level;
import game.graphics.levels.Level1;
import game.graphics.Screen;
import game.graphics.SpriteSheet;
import game.handlers.InputHandler;
import game.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 120;
    public static final int HEIGHT = 120 / 12 * 9;
    public static final int SCALE = 8;

    public static final Rectangle bounds = new Rectangle(0, 0, WIDTH * SCALE, HEIGHT * SCALE);

    private Player player;
    private ArrayList<Entity> entities;
    private Screen screen;
    private Level level;

    private boolean isRunning;
    private BufferedImage image = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
    public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private State state;

    public Game() {
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.setTitle("Not Radford");
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        init();

        start();
    }

    public void init() {
        entities = new ArrayList<Entity>();
        level = new Level1(this);

        player = new Player(0, 100, this);
        entities.add(player);

        InputHandler input = new InputHandler(this, player);
        player.setInput(input);
        screen = new Screen(this, input);

        state = State.MAIN_SCREEN;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public void removeProjectiles() {
        for (int i = 0; i < entities.size(); i++)
            if (entities.get(i).isProjectile())
                entities.remove(i);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void reset() {
        init();
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
        System.exit(0);
    }

    public void run() {
        state = State.MAIN_SCREEN;

        long last = System.nanoTime();
        double ns = 1000000000.0 / 60.0;
        double timeDiff = 0;

        while (isRunning) {
            long now = System.nanoTime();
            timeDiff += (now - last) / ns;
            last = now;

            while (timeDiff >= 1) {
                // Multiplying by this lets us get the time difference in milliseconds
                update(timeDiff * 16.65);
                timeDiff--;
            }

            paint();
        }
    }

    public void update(double diff) {
        switch (state) {
            case MAIN_SCREEN:
                break;
            case PLAYING:
                if (level.isCompleted()) {
                    state = State.LOADING;
                    return;
                }
                for (int i = 0; i < entities.size(); i++)
                    entities.get(i).update(diff);
                break;
            case LOADING:
                if (level.isCompleted())
                    level.loadNext();
                else
                    state = State.PLAYING;
                break;
        }
    }

    public void paint() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.clearRect(0, 0, getWidth(), getHeight());

        switch (state) {
            case MAIN_SCREEN:
                screen.render(g);
                break;
            case PLAYING:
                level.render(g);

                for (int i = 0; i < entities.size(); i++)
                    entities.get(i).render(g);
                break;
            case LOADING:
                screen.render(g);
                break;
        }

        g.dispose();

        bs.show();
    }
}
