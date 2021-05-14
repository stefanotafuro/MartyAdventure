package edu.unibo.martyadventure.view;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A simple POD class holding the animation objects for a character.
 */
public class FramesPack {

    private TextureRegion upFrames;
    private TextureRegion downFrames;
    private TextureRegion leftFrames;
    private TextureRegion rightFrames;


    public FramesPack(final TextureRegion up, final TextureRegion down, final TextureRegion left,
            final TextureRegion right) {
        this.upFrames = up;
        this.downFrames = down;
        this.leftFrames = left;
        this.rightFrames = right;
    }

    /**
     * @return the up frames.
     */
    public TextureRegion getUpFrames() {
        return upFrames;
    }

    /**
     * @return the down frames.
     */
    public TextureRegion getDownFrames() {
        return downFrames;
    }

    /**
     * @return the left frames.
     */
    public TextureRegion getLeftFrames() {
        return leftFrames;
    }

    /**
     * @return the right frames.
     */
    public TextureRegion getRightFrames() {
        return rightFrames;
    }
}
