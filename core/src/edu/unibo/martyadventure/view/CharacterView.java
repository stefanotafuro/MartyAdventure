package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.controller.entity.ControllableEntity;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public abstract class CharacterView implements ControllableEntity {

    // Sprite pixel sizes.
    protected static final int SPRITE_WITDTH = 68;
    protected static final int SPRITE_HEIGHT = 104;

    protected final float maxAccelleration;
    protected final float accellerationFactor;
    protected final float maxSpeed;

    private float velocity;
    private Vector2 currentPosition;
    private Vector2 nextPosition;

    private EntityState movementState;
    private EntityDirection movementDirection;

    private final Sprite sprite;

    private final AnimationsPack animations;
    private final FramesPack frames;

    // For physics collisions and such.
    private final Rectangle boundingBox;


    public CharacterView(final Vector2 initialPosition, final float maxAccelleration, final float accellerationFactor,
            final float maxSpeed, final Sprite sprite, final AnimationsPack animations, final FramesPack frames) {
        this.maxAccelleration = maxAccelleration;
        this.accellerationFactor = accellerationFactor;
        this.maxSpeed = maxSpeed;

        this.velocity = 0.0f;
        this.currentPosition = initialPosition;
        this.nextPosition = initialPosition;

        this.movementState = EntityState.IDLE;
        this.movementDirection = EntityDirection.UP;

        this.sprite = sprite;

        this.animations = animations;
        this.frames = frames;

        this.boundingBox = new Rectangle(initialPosition.x, initialPosition.y, CharacterView.SPRITE_WITDTH,
                CharacterView.SPRITE_HEIGHT / 2);
    }

    @Override
    public void calculateNextPosition(final EntityDirection direction, final float delta) {
        // Increase the acceleration (clamped to it's max).
        this.velocity = Math.min(this.maxAccelleration, this.velocity + this.accellerationFactor * delta);

        // Calculate the movement.
        Vector2 movement = Vector2.Zero;
        switch (direction) {
        case LEFT:
            movement.x = -1;
        case RIGHT:
            movement.x = +1;
        case UP:
            movement.x = -1;
        case DOWN:
            movement.x = +1;
            break;
        default:
            throw new IllegalArgumentException("Illegal direction '" + direction + "'");
        }

        movement = movement.scl(this.velocity);

        // Calculate the next position from the currently next (old) one.
        this.nextPosition = this.nextPosition.add(movement);
        this.nextPosition = this.nextPosition.clamp(0, this.maxSpeed);
        
        
    }

    @Override
    public void setState(final EntityState state) {
        this.movementState = state;
    }

    @Override
    public void setDirection(final EntityDirection direction) {
        this.movementDirection = direction;
    }

    /**
     * @return the character sprite
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**
     * @return the current position.
     */
    public Vector2 getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * @return the current frame.
     */
    public TextureRegion getCurrentFrame() {
        return null;
    }
}
