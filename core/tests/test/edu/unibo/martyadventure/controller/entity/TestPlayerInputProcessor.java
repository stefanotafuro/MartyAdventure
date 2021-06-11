package test.edu.unibo.martyadventure.controller.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Input.Keys;

import edu.unibo.martyadventure.controller.entity.ControllableEntity;
import edu.unibo.martyadventure.controller.entity.PlayerInputProcessor;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public class TestPlayerInputProcessor {

    private class DummyControllableEntity implements ControllableEntity {

        public EntityState state;
        public EntityDirection direction;


        @Override
        public void calculateNextPosition(EntityDirection direction, float delta) {
            this.direction = direction;
        }

        @Override
        public void setState(EntityState state) {
            this.state = state;
        }

        @Override
        public void setDirection(EntityDirection direction) {
            this.direction = direction;
        }
    }


    private static final float DUMMY_DELTA = 0.1f;


    private void assertDirection(final EntityDirection direction, final PlayerInputProcessor inputProcessor) {
        assertEquals(direction, ((DummyControllableEntity) inputProcessor.getPlayer()).direction);
    }

    private void assertDirectionNot(final EntityDirection direction, final PlayerInputProcessor inputProcessor) {
        assertNotEquals(direction, ((DummyControllableEntity) inputProcessor.getPlayer()).direction);
    }

    private void assertState(final EntityState state, final PlayerInputProcessor inputProcessor) {
        assertEquals(state, ((DummyControllableEntity) inputProcessor.getPlayer()).state);
    }

    /**
     * Get a clean input processor.
     */
    private PlayerInputProcessor getInputProcessor() {
        final PlayerInputProcessor inputProcessor = PlayerInputProcessor.getPlayerInputProcessor();
        inputProcessor.resetState();
        inputProcessor.setPlayer(new DummyControllableEntity(), true);
        inputProcessor.update(DUMMY_DELTA);
        return inputProcessor;
    }

    /**
     * Tests that the direction is correctly updated after each key press and
     * release.
     */
    private void keyInput(final int keycode, final EntityDirection direction, final int differentKeycode) {
        final PlayerInputProcessor inputProcessor = getInputProcessor();

        // The direction mustn't be set before the update
        // (Unless it's the default direction).
        assertTrue(inputProcessor.keyDown(keycode));
        if (direction != PlayerInputProcessor.DEFAULT_DIRECTION) {
            assertDirectionNot(direction, inputProcessor);
        }

        // The direction must be set after the update.
        inputProcessor.update(DUMMY_DELTA);
        assertDirection(direction, inputProcessor);

        // The direction must still be set after a key up.
        assertTrue(inputProcessor.keyUp(keycode));
        assertDirection(direction, inputProcessor);

        // Set a different direction
        assertTrue(inputProcessor.keyDown(differentKeycode));
        // The direction must then be unset after the update.
        inputProcessor.update(DUMMY_DELTA);
        assertDirectionNot(direction, inputProcessor);
    }

    /**
     * Test that a precedent vertical direction key press is kept after horizontal
     * keys are also pressed.
     */
    private void verticalInputPriority(final int keycode, final EntityDirection direction) {
        final PlayerInputProcessor inputProcessor = getInputProcessor();
        assertTrue(inputProcessor.keyDown(keycode));
        assertTrue(inputProcessor.keyDown(Keys.D));
        assertTrue(inputProcessor.keyDown(Keys.A));
        inputProcessor.update(DUMMY_DELTA);

        assertDirection(direction, inputProcessor);
    }

    /**
     * Test that the input is correctly detected, set and unset.
     */
    @Test
    void testKeyInput() {
        keyInput(Keys.W, EntityDirection.UP, Keys.S);
        keyInput(Keys.S, EntityDirection.DOWN, Keys.W);
        keyInput(Keys.A, EntityDirection.LEFT, Keys.D);
        keyInput(Keys.D, EntityDirection.RIGHT, Keys.A);
    }

    /**
     * Test that vertical input is given priority to horizontal inputs.
     */
    @Test
    void testVerticalPriorityInput() {
        verticalInputPriority(Keys.W, EntityDirection.UP);
        verticalInputPriority(Keys.S, EntityDirection.DOWN);
    }

    /**
     * Test that the state is correctly set.
     */
    @Test
    void testStatus() {
        final PlayerInputProcessor inputProcessor = getInputProcessor();
        // By default, the player should be idle
        assertState(EntityState.IDLE, inputProcessor);

        // The walking state mustn't be set before the update.
        assertTrue(inputProcessor.keyDown(Keys.W));
        assertState(EntityState.IDLE, inputProcessor);

        // The walking state must be set after the update.
        inputProcessor.update(DUMMY_DELTA);
        assertState(EntityState.WALKING, inputProcessor);

        // The walking state must still be set after a key up.
        assertTrue(inputProcessor.keyUp(Keys.W));
        assertState(EntityState.WALKING, inputProcessor);

        // The state must then be set to idle again after the update.
        inputProcessor.update(DUMMY_DELTA);
        assertState(EntityState.IDLE, inputProcessor);
    }
}
