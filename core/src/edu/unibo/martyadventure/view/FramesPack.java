package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A simple POD class holding the animation objects for a character.
 */
public class FramesPack {

    public TextureRegion upFrames;
    public TextureRegion downFrames;
    public TextureRegion leftFrames;
    public TextureRegion rightFrames;


    public FramesPack(final TextureRegion up, final TextureRegion down, final TextureRegion left,
            final TextureRegion right) {
        this.upFrames = up;
        this.downFrames = down;
        this.leftFrames = left;
        this.rightFrames = right;
    }
}
