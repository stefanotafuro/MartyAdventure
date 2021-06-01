package edu.unibo.martyadventure.view.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import edu.unibo.martyadventure.view.entity.EntityDirection;

/**
 * From a texture containing the tiles for the walking animation, build each
 * animation.
 */
class AnimationPack {

    private static final float FRAME_DURATION = 0.3f;
    private static final int DOWN_FRAMES_INDEX = 0;
    private static final int LEFT_FRAMES_INDEX = 1;
    private static final int RIGHT_FRAMES_INDEX = 2;
    private static final int UP_FRAMES_INDEX = 3;

    public static final float ANIMATION_START = 0.0f;

    private final Animation<TextureRegion>[] animations;


    private Animation<TextureRegion> buildAnimation(final TextureRegion[][] textures, final int tilesIndex) {
        final Animation<TextureRegion> animation = new Animation<TextureRegion>(AnimationPack.FRAME_DURATION,
                textures[tilesIndex]);
        animation.setPlayMode(PlayMode.LOOP);
        return animation;
    }

    public AnimationPack(final TextureRegion texture, final int tileWidth, final int tileHeight) {
        TextureRegion[][] textures = texture.split(tileWidth, tileHeight);
        // Workaround for no generic arrays in java
        this.animations = (Animation<TextureRegion>[]) new Object[4];
        this.animations[AnimationPack.DOWN_FRAMES_INDEX] = buildAnimation(textures, AnimationPack.DOWN_FRAMES_INDEX);
        this.animations[AnimationPack.LEFT_FRAMES_INDEX] = buildAnimation(textures, AnimationPack.LEFT_FRAMES_INDEX);
        this.animations[AnimationPack.RIGHT_FRAMES_INDEX] = buildAnimation(textures, AnimationPack.RIGHT_FRAMES_INDEX);
        this.animations[AnimationPack.UP_FRAMES_INDEX] = buildAnimation(textures, AnimationPack.UP_FRAMES_INDEX);
    }

    /**
     * @return the up-side walking animation
     */
    public Animation<TextureRegion> getUpAnimation() {
        return this.animations[AnimationPack.UP_FRAMES_INDEX];
    }

    /**
     * @return the down-side walking animation
     */
    public Animation<TextureRegion> getDownAnimation() {
        return this.animations[AnimationPack.DOWN_FRAMES_INDEX];
    }

    /**
     * @return the left-side walking animation
     */
    public Animation<TextureRegion> getLeftAnimation() {
        return this.animations[AnimationPack.LEFT_FRAMES_INDEX];
    }

    /**
     * @return the right-side walking animation.
     */
    public Animation<TextureRegion> getRightAnimation() {
        return this.animations[AnimationPack.RIGHT_FRAMES_INDEX];
    }

    /**
     * @return the matching animation for the given entity direction.
     */
    public Animation<TextureRegion> getEntityDirectionAnimation(final EntityDirection direction) {
        final int index;
        switch (direction) {
        case LEFT:
            index = AnimationPack.LEFT_FRAMES_INDEX;
            break;
        case RIGHT:
            index = AnimationPack.RIGHT_FRAMES_INDEX;
            break;
        case UP:
            index = AnimationPack.UP_FRAMES_INDEX;
            break;
        case DOWN:
            index = AnimationPack.DOWN_FRAMES_INDEX;
            break;
        default:
            throw new IllegalArgumentException("Unknow direction");
        }
        return this.animations[index];
    }

    /**
     * @return the up-side idle frame.
     */
    public TextureRegion getUpIdle() {
        return getUpAnimation().getKeyFrame(AnimationPack.ANIMATION_START);
    }

    /**
     * @return the down-side idle frame.
     */
    public TextureRegion getDownIdle() {
        return getDownAnimation().getKeyFrame(AnimationPack.ANIMATION_START);
    }

    /**
     * @return the left-side idle frame.
     */
    public TextureRegion getLeftIdle() {
        return getLeftAnimation().getKeyFrame(AnimationPack.ANIMATION_START);
    }

    /**
     * @return the right-side idle frame.
     */
    public TextureRegion getRightIdel() {
        return getRightAnimation().getKeyFrame(AnimationPack.ANIMATION_START);
    }

    /**
     * @return the matching idle frame for the given entity direction.
     */
    public TextureRegion getEntityDirectionIdle(final EntityDirection direction) {
        return getEntityDirectionAnimation(direction).getKeyFrame(AnimationPack.ANIMATION_START);
    }
}
