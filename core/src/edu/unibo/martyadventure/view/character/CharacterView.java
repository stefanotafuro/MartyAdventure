package edu.unibo.martyadventure.view.character;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.controller.entity.ControllableEntity;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

/**
 * A character's base providing basic movement, interaction with given the map
 * and visual representation.
 */
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

        this.boundingBox = new Rectangle();
        this.boundingBox.setCenter(initialPosition);
        resetBoundingBoxSize();
    }

    /**
     * Resize the character bounding box.
     * 
     * @param resizeWidth  percentage from 1.0 to 0.0 to resize the width to.
     * @param resizeHeight percentage from 1.0 to 0.0 to resize the height to.
     */
    public void resizeBoundingBox(final float resizeWidth, final float resizeHeight) {
        this.boundingBox.width *= resizeWidth;
        this.boundingBox.height *= resizeHeight;
    }

    /**
     * Restore the character boudning box to it's original size.
     */
    public void resetBoundingBoxSize() {
        this.boundingBox.setSize(CharacterView.SPRITE_WITDTH, CharacterView.SPRITE_HEIGHT);
    }

    /**
     * Move the character to the next position.
     */
    public void goNextPosition() {
        this.currentPosition = this.nextPosition;
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
     * @param position the position to set the character in.
     */
    public void setCurrentPosition(final Vector2 position) {
        this.currentPosition = position;
        this.boundingBox.setCenter(position);
    }

    /**
     * @return the next precalculated position.
     */
    public Vector2 getNextPosition() {
        return this.nextPosition;
    }

    /**
     * @return the character collision bounding box.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.boundingBox);
    }

    /**
     * @return the current frame.
     */
    public TextureRegion getCurrentFrame() {
        return null;
    }

    @Override
    public void setState(final EntityState state) {
        this.movementState = state;
    }

    @Override
    public void setDirection(final EntityDirection direction) {
        this.movementDirection = direction;
    }

    @Override
    public void calculateNextPosition(final EntityDirection direction, final float delta) {
        // Increase the acceleration (clamped to it's max).
        this.velocity = Math.min(this.maxAccelleration, this.velocity + this.accellerationFactor * delta);

        // Update the direction
        this.movementDirection = direction;

        // Calculate the movement.
        Vector2 movement = Vector2.Zero;
        switch (direction) {
        case LEFT:
            movement.x = -1;
            break;
        case RIGHT:
            movement.x = +1;
            break;
        case UP:
            movement.x = -1;
            break;
        case DOWN:
            movement.x = +1;
            break;
        default:
            throw new IllegalArgumentException("Illegal direction '" + direction + "'");
        }

        movement = movement.scl(this.velocity);

        // Calculate the next position from the currently next (old) one.
        this.nextPosition = this.nextPosition.add(movement).clamp(0, this.maxSpeed);
    }
}
