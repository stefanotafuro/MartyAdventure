package edu.unibo.martyadventure.controller.entity;

import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

/**
 * An entity whose movement and direction can be controlled.
 */
public interface ControllableEntity {
    /**
     * Calculate the entity's position for the next update.
     * 
     * @param direction the direction the entity is moved to.
     * @param delta     the delta time
     */
    void calculateNextPosition(EntityDirection direction, float delta);

    /**
     * Set the entity's state.
     * 
     * @param state the state to set.
     */
    void setState(EntityState state);

    /**
     * Set the entity's direction.
     * 
     * @param direction the direction to set.
     */
    void setDirection(EntityDirection direction);
}
