package game.entity.projectile;

import game.Game;
import game.entity.Direction;
import game.entity.Entity;

import java.util.ArrayList;

public abstract class Projectile extends Entity {

    protected int speed, power;
    private Entity owner;
    protected Direction direction;

    public Projectile(int x, int y, Game game, Entity owner, Direction direction) {
        super(x, y, game);
        this.owner = owner;
        this.direction = direction;
    }

    /**
     * Overrides the Entities move method to ignore boundaries
     *
     * @param d The direction to move in (UP, DOWN, LEFT, RIGHT)
     */
    public void move(Direction d) {
        switch (d) {
            case UP:
                position.y -= speed;
                break;
            case DOWN:
                position.y += speed;
                break;
            case RIGHT:
                position.x += speed;
                break;
            case LEFT:
                position.x -= speed;
                break;
        }
    }

    /**
     * Checks if this projectile has collided with another entity or has gone outside the boundaries
     */
    public void checkCollision() {
        if (position.x < 0 || position.x > MAX_X || position.y < 0 || position.y > MAX_Y)
            game.removeEntity(this);
        if (!game.getLevel().isTraversable(bounds))
            game.removeEntity(this);

        ArrayList<Entity> entities = game.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            if ((e.isPlayer() && owner.isPlayer()) || e.isProjectile())
                continue;
            if (e.isEnemy() && owner.isPlayer())
                if (e.getBounds().intersects(bounds)) {
                    game.removeEntity(this);
                    dealDamage(e);
                }
            if (e.isPlayer() && owner.isEnemy())
                if (e.getBounds().intersects(bounds)) {
                    game.removeEntity(this);
                    dealDamage(e);
                }


        }
    }

    /**
     * Applies damage to an Entity
     *
     * @param e The entity to apply the damage to
     */
    public void dealDamage(Entity e) {
        e.addHealth(-power);
    }

    /*
     * Getters and Setters
     */

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

}
