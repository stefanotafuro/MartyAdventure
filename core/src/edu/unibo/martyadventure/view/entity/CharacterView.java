package edu.unibo.martyadventure.view.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.controller.entity.ControllableEntity;
import edu.unibo.martyadventure.view.AnimationsPack;
import edu.unibo.martyadventure.view.FramesPack;

public abstract class CharacterView implements ControllableEntity {

    // Sprite pixel sizes.
    protected static final int SPRITE_WITDTH = 68;
    protected static final int SPRITE_HEIGHT = 104;

    private final Sprite sprite;

    private AnimationsPack animations;
    private FramesPack frames;

    // For physics collisions and such.
    private final Rectangle boundingBox;

    private EntityState movementState;
    private EntityDirection direction;

    private Vector2 currentPosition;
    private Vector2 nextPosition;
    private Vector2 velocity;


    public CharacterView(final Sprite sprite, final Vector2 initialPosition, final AnimationsPack animations,
            final FramesPack frames) {
        this.sprite = sprite;

        this.animations = animations;
        this.frames = frames;

        this.boundingBox = new Rectangle(initialPosition.x, initialPosition.y, CharacterView.SPRITE_WITDTH,
                CharacterView.SPRITE_HEIGHT / 2);

        this.movementState = EntityState.IDLE;
        this.direction = EntityDirection.UP;

        this.currentPosition = initialPosition;
        this.nextPosition = initialPosition;
        this.velocity = Vector2.Zero;
    }

    @Override
    public void calculateNextPosition(EntityDirection direction, float delta) {

    }

    @Override
    public void setState(final EntityState state) {
        this.movementState = state;
    }

    @Override
    public void setDirection(final EntityDirection direction) {
        this.direction = direction;
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
        return currentPosition;
    }

    /**
     * @return the current frame.
     */
    public TextureRegion getCurrentFrame() {
        return null;
    }
}
