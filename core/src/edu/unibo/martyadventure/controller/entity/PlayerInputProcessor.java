package edu.unibo.martyadventure.controller.entity;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

/**
 * Handles the player input
 */
public class PlayerInputProcessor implements InputProcessor {

    /*
     * Creating this ordered array to impose a stable preference in the entity's
     * direction, otherwise at each run the hashcode of the EntityDirection's
     * singletons may change, resulting in an inconsistent orientation across runs
     * when the player presses more than a single key.
     */
    private static final EntityDirection[] orderedDirections = { EntityDirection.UP, EntityDirection.DOWN,
            EntityDirection.LEFT, EntityDirection.RIGHT };
    private static PlayerInputProcessor instance;

    private Map<EntityDirection, Boolean> directionsMap;
    private ControllableEntity playerEntity;

    public static final EntityDirection DEFAULT_DIRECTION = PlayerInputProcessor.orderedDirections[0];

    /**
     * Get the player input processor singleton.
     *
     * There may be 1 and only 1 player input processor to prevent multiple
     * instances catching each's other's keycodes or sending multiple updates to the
     * same player
     */
    public static PlayerInputProcessor getPlayerInputProcessor() {
        if (PlayerInputProcessor.instance == null) {
            PlayerInputProcessor.instance = new PlayerInputProcessor();
        }
        return PlayerInputProcessor.instance;
    }

    private PlayerInputProcessor() {
        resetState();
    }

    private Map<EntityDirection, Boolean> getDirectionsMap() {
        final Map<EntityDirection, Boolean> map = new EnumMap<EntityDirection, Boolean>(EntityDirection.class);
        for (EntityDirection direction : EntityDirection.values()) {
            map.put(direction, false);
        }
        return map;
    }

    /**
     * Sets the given keycode to the given value
     *
     * @return true if the keycode was valid and the value has been set, false
     *         otherwise.
     */
    private boolean setSelectedDirection(final int keycode, final boolean value) {
        Optional<EntityDirection> pressedKey = matchKeycode(keycode);
        if (pressedKey.isPresent()) {
            this.directionsMap.put(pressedKey.get(), value);
            return true;
        }
        return false;
    }

    /**
     * @return the matching key to the given keycode, if valid.
     */
    private Optional<EntityDirection> matchKeycode(final int keycode) {
        switch (keycode) {
        case Input.Keys.A:
            return Optional.of(EntityDirection.LEFT);
        case Input.Keys.D:
            return Optional.of(EntityDirection.RIGHT);
        case Input.Keys.W:
            return Optional.of(EntityDirection.UP);
        case Input.Keys.S:
            return Optional.of(EntityDirection.DOWN);
        default:
            return Optional.empty();
        }
    }

    /**
     * @param player set the player entity to update.
     * @param setDefaults if true, set the player to the default state and direction.
     */
    public void setPlayer(final ControllableEntity player, final boolean setDefaults) {
        this.playerEntity = player;
        if (setDefaults) {
            this.playerEntity.setDirection(PlayerInputProcessor.DEFAULT_DIRECTION);
            this.playerEntity.setState(EntityState.IDLE);
        }
    }

    /**
     * @return the currently updated player entity.
     */
    public ControllableEntity getPlayer() {
        return this.playerEntity;
    }

    /**
     * Resets the internal state. May be used after the processor has been
     * temporarily disabled or paused.
     */
    public void resetState() {
        this.directionsMap = getDirectionsMap();
    }

    /**
     * Update the player entity state and set the next position.
     *
     * @param delta time since last update.
     */
    public void update(float delta) {
        boolean anyDirection = false;
        for (EntityDirection direction : PlayerInputProcessor.orderedDirections) {
            if (this.directionsMap.get(direction)) {
                this.playerEntity.setState(EntityState.WALKING);
                this.playerEntity.setDirection(direction);
                this.playerEntity.calculateNextPosition(direction, delta);

                anyDirection = true;
                break;
            }
        }

        if (!anyDirection) {
            this.playerEntity.setState(EntityState.IDLE);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return setSelectedDirection(keycode, true);
    }

    @Override
    public boolean keyUp(int keycode) {
        return setSelectedDirection(keycode, false);
    }

    @SuppressWarnings("unused")
    @Override
    public boolean keyTyped(char character) {
        // Unused
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Unused
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // Unused
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Unused
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // Unused
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // Unused
        return false;
    }
}
