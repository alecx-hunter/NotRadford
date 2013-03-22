package game.entity;

import game.Game;
import game.entity.projectile.Projectile;
import game.handlers.DamageEvent;
import game.pathfinding.Path;

import java.awt.*;
import java.util.ArrayList;

public abstract class Entity {

    public int WIDTH;
    public int HEIGHT;
    protected int MAX_X;
    protected int MAX_Y;

    protected Point position;
    protected int health;
    protected int maxHealth;
    protected int speed;
    protected Rectangle bounds;
    protected Game game;
    protected ArrayList<DamageEvent> damageEvents;

    public Entity(int x, int y, Game game) {
        position = new Point(x, y);
        this.game = game;
        damageEvents = new ArrayList<DamageEvent>();
    }

    /**
     * Must be called at the end of the child constructor to initialize the
     * MAX_X and MAX_Y variables
     */
    public void init() {
        MAX_X = (Game.WIDTH * Game.SCALE) - WIDTH + 10;
        MAX_Y = (Game.HEIGHT * Game.SCALE) - HEIGHT + 10;

        health = maxHealth;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Point getPosition() {
        return position;
    }

    public Point getCenterPosition() {
        return new Point(position.x + (WIDTH / 2), position.y + (HEIGHT / 2));
    }

    /**
     * @return The center X coordinate of this entity
     */
    public int getCenterX() {
        return getCenterPosition().x;
    }

    /**
     * @return The center Y coordinate of this entity
     */
    public int getCenterY() {
        return getCenterPosition().y;
    }

    /**
     * Moves the entity in the specified Direction
     *
     * @param d                The Direction in which to move
     * @param multipleMovement Whether or not this entity will be moving in more than one direction (Diagonal)
     */
    public void move(Direction d, boolean multipleMovement) {
        int mod = multipleMovement ? 2 : 1;

        switch (d) {
            case UP:
                if (Game.bounds.contains(getX(), getY() - (speed / mod), WIDTH, HEIGHT))
                    position.y -= (speed / mod);
                break;
            case DOWN:
                if (Game.bounds.contains(getX(), getY() + (speed / mod), WIDTH, HEIGHT))
                    position.y += (speed / mod);
                break;
            case RIGHT:
                if (Game.bounds.contains(getX() + (speed / mod), getY(), WIDTH, HEIGHT))
                    position.x += (speed / mod);
                break;
            case LEFT:
                if (Game.bounds.contains(getX() - (speed / mod), getY(), WIDTH, HEIGHT))
                    position.x -= speed / mod;
                break;
        }
    }

    /**
     * Moves the entity along the path specified
     *
     * @param path The path the entity is to follow
     */
    public void traversePath(Path path) {
        Point p;
        if ((p = path.getNext()) != null) {
            int directions = 0;

            if (getX() < p.x) directions++;
            if (getX() > p.x) directions++;
            if (getY() < p.y) directions++;
            if (getY() > p.y) directions++;

            if (getX() < p.x && getX() + speed < MAX_X)
                move(Direction.RIGHT, directions > 1);
            if (getX() > p.x && getX() - speed >= 0)
                move(Direction.LEFT, directions > 1);
            if (getY() < p.y && getY() + speed < MAX_Y)
                move(Direction.DOWN, directions > 1);
            if (getY() > p.y && getY() - speed >= 0)
                move(Direction.UP, directions > 1);
        }
    }

    /**
     * Returns the euclidean distance between two entities
     * sqrt((x1 - x2)^2 + (y1 - y2)^2)
     *
     * @param e The entity to get the distance between
     * @return Returns the euclidean distance between this entity and the Entity passed in
     */
    public double distanceTo(Entity e) {
        return Math.sqrt(Math.pow(e.getCenterX() - getCenterX(), 2.0) + Math.pow(e.getCenterY() - getCenterY(), 2.0));
    }

    /**
     * Adds or removes health from the entity
     * Positive values add, negative subtract
     *
     * @param amount The amout of health to add/remove
     */
    public void addHealth(int amount) {
        if (amount < 0)
            damageEvents.add(new DamageEvent(this, Math.abs(amount)));
        health += amount;
        if (health < 0)
            health = 0;
    }

    /**
     * @return The amount of health this entity currently has
     */
    public int getHealth() {
        return health;
    }

    /**
     * This method must be overwritten and then called in child classes.
     * It keeps track of the bounds of the entity as well as updating any
     * DamageEvents that may have occurred.
     *
     * @param diff The time difference in milliseconds since the last update.
     */
    public void update(double diff) {
        bounds = new Rectangle(position, new Dimension(WIDTH, HEIGHT));

        for (DamageEvent de : damageEvents)
            de.tick();
        for (int i = 0; i < damageEvents.size(); i++)
            if (damageEvents.get(i).getTicks() < 0)
                damageEvents.remove(i);
    }

    /**
     * This method must be overwritten and then called in child classes.
     * It handles rendering any DamageEvents available
     *
     * @param g The Graphics object to draw on
     */
    public void render(Graphics2D g) {
        for (DamageEvent de : damageEvents)
            de.render(g);
    }

    /**
     * @return Returns true if this entity is a player, false otherwise
     */
    public boolean isPlayer() {
        return getClass().equals(Player.class);
    }

    /**
     * @return Returns true if this entity is a projectile, false otherwise
     */
    public boolean isProjectile() {
        return getClass().equals(Projectile.class);
    }

    /**
     * @return Returns true if this entity is an enemy, false otherwise
     */
    public boolean isEnemy() {
        return getClass().equals(Enemy.class);
    }
}
