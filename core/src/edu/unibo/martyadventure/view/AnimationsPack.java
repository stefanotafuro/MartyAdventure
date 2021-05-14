package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A simple POD class holding the animation objects for a character.
 */
public class AnimationsPack {

    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> leftAnimation;
    private Animation<TextureRegion> rightAnimation;


    public AnimationsPack(final Animation<TextureRegion> up, final Animation<TextureRegion> down,
            final Animation<TextureRegion> left, final Animation<TextureRegion> right) {
        this.upAnimation = up;
        this.downAnimation = down;
        this.leftAnimation = left;
        this.rightAnimation = right;
    }

    /**
     * @return the up animation.
     */
    public Animation<TextureRegion> getUpAnimation() {
        return this.upAnimation;
    }

    /**
     * @return the down animation.
     */
    public Animation<TextureRegion> getDownAnimation() {
        return this.downAnimation;
    }

    /**
     * @return the left animation.
     */
    public Animation<TextureRegion> getLeftAnimation() {
        return this.leftAnimation;
    }

    /**
     * @return the right animation.
     */
    public Animation<TextureRegion> getRightAnimation() {
        return this.rightAnimation;
    }
}
