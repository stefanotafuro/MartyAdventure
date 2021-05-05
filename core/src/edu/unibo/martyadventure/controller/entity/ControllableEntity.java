package edu.unibo.martyadventure.controller.entity;

import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public interface ControllableEntity {
    void calculateNextPosition(EntityDirection direction, float delta);
    void setState(EntityState state);
    void setDirection(EntityDirection direction);
}
