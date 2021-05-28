package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

    // For physics collisions and such.
    private final Rectangle boundingBox;
    private final MapLayer collisionLayer;

    /**
     * Build a bounding box rectangle centered at the given position.
     */
    private Rectangle buildBoundingBoxAt(final Vector2 position) {
        return new Rectangle(position.x + CharacterView.SPRITE_WITDTH / 2.0f,
                position.y + CharacterView.SPRITE_HEIGHT / 2.0f, CharacterView.SPRITE_WITDTH,
                CharacterView.SPRITE_HEIGHT);
    }

    /**
     * Test if the current box would collide with the map if centered at the given
     * position.
     */
    private boolean collision(final Vector2 position) {
        final Rectangle testBox = buildBoundingBoxAt(position);

        for (MapObject obj : this.collisionLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                if (testBox.overlaps(((RectangleMapObject) obj).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public CharacterView(final Vector2 initialPosition, final float maxAccelleration, final float accellerationFactor,
            final float maxSpeed, final Sprite sprite, final AnimationsPack animations, final FramesPack frames,
            final MapLayer collisionLayer) {
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

        this.boundingBox = buildBoundingBoxAt(initialPosition);
        this.collisionLayer = collisionLayer;
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
        Vector2 testPosition = this.nextPosition.add(movement);
        testPosition = testPosition.clamp(0, this.maxSpeed);

        if (!collision(testPosition)) {
            this.nextPosition = testPosition;
            this.boundingBox.setPosition(testPosition);
        }
    }

    /**
     * Update the character status before rendering.
     */
    public void update() {
        this.currentPosition = this.nextPosition;
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
