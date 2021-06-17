package edu.unibo.martyadventure.view.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.controller.entity.ControllableEntity;
import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;
import edu.unibo.martyadventure.model.character.Character;

/**
 * A character's base providing basic movement, interaction with given the map
 * and visual representation.
 */
public abstract class CharacterView<C extends Character> implements ControllableEntity {

    private static final float BOX_OFFSET = 1.7f;

    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;


    private float velocity;
    private Vector2 currentPosition;
    private Vector2 nextPosition;

    private EntityState movementState;
    private EntityDirection movementDirection;

    private final Rectangle boundingBox;

    private final AnimationPack animations;
    private float animationStartTime;

    private C character;

    protected final float maxAccelleration;
    protected final float accellerationFactor;
    protected final float maxSpeed;


    public CharacterView(final C character, final Vector2 initialPosition, final float maxAccelleration, final float accellerationFactor,
            final float maxSpeed, final TextureRegion texture) {
        this.maxAccelleration = maxAccelleration;
        this.accellerationFactor = accellerationFactor;
        this.maxSpeed = maxSpeed;

        this.velocity = 0.0f;
        this.currentPosition = new Vector2(initialPosition);
        this.nextPosition = new Vector2(initialPosition);

        this.movementState = EntityState.IDLE;
        this.movementDirection = EntityDirection.UP;

        this.boundingBox = new Rectangle();
        calculateBoundingBoxPosition();

        this.animations = new AnimationPack(texture, FRAME_WIDTH, FRAME_HEIGHT);
        this.animationStartTime = AnimationPack.ANIMATION_START;

        this.character = character;
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
     * Restore the character bounding box to it's original size.
     */
    public void calculateBoundingBoxPosition() {
        this.boundingBox.set(nextPosition.x + BOX_OFFSET ,
                nextPosition.y, 
                FRAME_WIDTH * MapManager.UNIT_SCALE / 8, 
                FRAME_HEIGHT * MapManager.UNIT_SCALE / 8);
    }

    /**
     * Move the character to the next position.
     */
    public void goNextPosition() {
        setCurrentPosition(this.nextPosition);
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
        this.currentPosition = new Vector2 (position);
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
     * @return calculate the current frame.
     */
    public TextureRegion getCurrentFrame() {
        if (this.movementState == EntityState.WALKING) {
            this.animationStartTime += Gdx.graphics.getDeltaTime();
            return this.animations.getEntityDirectionAnimation(this.movementDirection)
                    .getKeyFrame(this.animationStartTime);
        } else {
            this.animationStartTime = AnimationPack.ANIMATION_START;
            return this.animations.getEntityDirectionIdle(this.movementDirection);
        }
    }
    
    /**
     * @return the view's character.
     */
    public C getCharacter() {
        return this.character;
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
        this.velocity = Math.min(this.maxAccelleration, this.accellerationFactor * delta);

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
            movement.y = +1;
            break;
        case DOWN:
            movement.y = -1;
            break;
        default:
            throw new IllegalArgumentException("Illegal direction '" + direction + "'");
        }

        movement = movement.scl(this.velocity);

        // Calculate the next position from the currently next (old) one.
        this.nextPosition.set(this.currentPosition.x + movement.x, this.currentPosition.y + movement.y);
        calculateBoundingBoxPosition();
    }
}
