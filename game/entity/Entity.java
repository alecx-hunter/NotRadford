package game.entity;

import game.Game;
import game.entity.projectile.Projectile;
import game.graphics.SpriteSheet;
import game.handlers.DamageEvent;
import game.pathfinding.Path;

import java.awt.*;
import java.awt.image.BufferedImage;
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

    protected Direction facing;
    protected boolean moving;
    protected SpriteSheet sprites;
    protected int anim;
    protected BufferedImage sprite;

    public Entity(int x, int y, Game game) {
        position = new Point(x, y);
        this.game = game;
        damageEvents = new ArrayList<DamageEvent>();
        anim = 0;
        facing = Direction.DOWN;
        moving = false;
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

        facing = d;
        moving = true;

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
     * DamageEvents that may have occurred. It also handles animating sprites
     * of this Entity. The child class has to set moving = false in it's
     * implementation to ensure the animations work correctly
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

        anim = anim < 10000 ? ++anim : 0;
        if (sprite == null)
            return;
        if (moving) {
            switch (facing) {
                case UP:
                    if (anim % 20 < 10)
                        sprite = sprites.getSprite(13);
                    else
                        sprite = sprites.getSprite(15);
                    break;
                case DOWN:
                    if (anim % 20 < 10)
                        sprite = sprites.getSprite(1);
                    else
                        sprite = sprites.getSprite(3);
                    break;
                case LEFT:
                    if (anim % 10 == 0)
                        sprite = sprites.getSprite(5);
                    if (anim % 20 == 0)
                        sprite = sprites.getSprite(6);
                    if (anim % 30 == 0)
                        sprite = sprites.getSprite(7);
                    break;
                case RIGHT:
                    if (anim % 10 == 0)
                        sprite = sprites.getSprite(9);
                    if (anim % 20 == 0)
                        sprite = sprites.getSprite(10);
                    if (anim % 30 == 0)
                        sprite = sprites.getSprite(11);
                    break;
            }
        }
        else {
            switch (facing) {
                case UP:
                    sprite = sprites.getSprite(12);
                    break;
                case DOWN:
                    sprite = sprites.getSprite(0);
                    break;
                case LEFT:
                    sprite = sprites.getSprite(4);
                    break;
                case RIGHT:
                    sprite = sprites.getSprite(8);
                    break;
            }
        }
    }

    /**
     * This method must be overwritten and then called in child classes.
     * It handles rendering any DamageEvents available as well as rendering
     * the sprite that represents this entity
     *
     * @param g The Graphics object to draw on
     */
    public void render(Graphics2D g) {
        g.drawImage(sprite, getX(), getY(), WIDTH, HEIGHT, null);

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
