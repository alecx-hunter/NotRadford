package game;

import game.entity.*;
import game.entity.projectile.*;
import game.handlers.*;
import game.graphics.*;
import game.logging.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 120;
    public static final int HEIGHT = 120 / 12 * 9;
    public static final int SCALE = 8;

    private Player player;
    private ArrayList<Projectiles> projectiles;
    private ArrayList<Entity> entities;

    private boolean isRunning;
    private BufferedImage image = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);
    public int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game() {
        setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        projectiles = new ArrayList<Projectiles>();
        entities = new ArrayList<Entity>();
        Level level = new Level();
        player = new Player(0, 0, this);
        entities.add(new Enemy(500, 500, this, player, level));
        entities.add(player);

        InputHandler input = new InputHandler(this, player);
        player.setInput(input);

        start();
    }

    public void addProjectile(Projectiles p) {
        projectiles.add(p);
    }

    public void removeProjectile(Projectiles p) {
        projectiles.remove(p);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
    }

    public void run() {
        long last = System.nanoTime();
        double ns = 1000000000.0/60.0;
        double timeDiff = 0;

        while(isRunning) {
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
        for (int i = 0; i < entities.size(); i++)
            entities.get(i).update(diff);
        for (int i = 0; i < projectiles.size(); i++)
            projectiles.get(i).update(diff);
    }

    public void paint() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics2D g = (Graphics2D)bs.getDrawGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.clearRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < entities.size(); i++)
            entities.get(i).render(g);
        for (int i = 0; i < projectiles.size(); i++)
            projectiles.get(i).render(g);

        boolean debug = true;
        if (debug) {
            g.setColor(Color.RED);
            Log.debug(g, player.getX() + ", " + player.getY(), 5, HEIGHT*SCALE - 10);
        }

        g.dispose();

        bs.show();
    }

}
