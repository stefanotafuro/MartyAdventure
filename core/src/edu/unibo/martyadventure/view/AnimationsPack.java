package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A simple POD class holding the animation objects for a character.
 */
public class AnimationsPack {

    public Animation<TextureRegion> upAnimation;
    public Animation<TextureRegion> downAnimation;
    public Animation<TextureRegion> leftAnimation;
    public Animation<TextureRegion> rightAnimation;


    public AnimationsPack(final Animation<TextureRegion> up, final Animation<TextureRegion> down,
            final Animation<TextureRegion> left, final Animation<TextureRegion> right) {
        this.upAnimation = up;
        this.downAnimation = down;
        this.leftAnimation = left;
        this.rightAnimation = right;
    }
}
