package edu.unibo.martyadventure.ui.controller;

import java.util.Optional;

import com.badlogic.gdx.InputProcessor;

/**
 * Controllable selection shared logic.
 */
public abstract class ControllableSelectionInputProvider<T> implements InputProcessor {

    private final int confirmKeycode;
    private final ControllableSelection<T> controllableSelection;
    private T currentSelection;


    private boolean tryChangeSelection(final T candidate) {
        if (isWithinBounds(candidate, this.controllableSelection.getSelectionsBounds())) {
            this.currentSelection = candidate;
            return true;
        }
        return false;
    }

    /**
     * @return true if the selection is within bounds, false otherwise.
     */
    protected abstract boolean isWithinBounds(T candidate, T bounds);

    /**
     * Change from the current selection from the given keycode.
     * 
     * @param keycode the pressed keycode. It will never be called with the
     *                confirmKeycode value.
     * @param current the current selection index.
     * @return the new selection index if the keycode is supported, an empty
     *         optional otherwise. Doesn't have to be within bounds.
     */
    protected abstract Optional<T> updateSelection(int keycode, T current);

    /**
     * @param controllableSelection the selection to send the inputs to.
     * @param defaultSelection      the initial current selection if the
     *                              controllableSelection doesn't have a default
     *                              one.
     * @param confirmKeycode        the keycode that triggers the selection confirm
     */
    protected ControllableSelectionInputProvider(final ControllableSelection<T> controllableSelection,
            final T defaultSelection, final int confirmKeycode) {
        this.confirmKeycode = confirmKeycode;
        this.controllableSelection = controllableSelection;

        Optional<T> initialSelection = controllableSelection.getCurrentSelection();
        if (initialSelection.isPresent()) {
            T selection = initialSelection.get();
            if (isWithinBounds(selection, controllableSelection.getSelectionsBounds())) {
                throw new IndexOutOfBoundsException("Invalid default selection");
            } else {
                this.currentSelection = selection;
            }
        } else {
            this.currentSelection = defaultSelection;
        }
    }

    /**
     * @return the current selection, which may not be confirmed.
     */
    public T getCurrentSelection() {
        return this.currentSelection;
    }

    @Override
    public final boolean keyDown(int keycode) {
        // Triggers the selection confirm.
        if (keycode == this.confirmKeycode) {
            this.controllableSelection.select(this.currentSelection);
            return true;
        }

        Optional<T> updatedSelection = updateSelection(keycode, this.currentSelection);

        // If the keycode is supported by the implementation, go for it.
        if (updatedSelection.isPresent()) {
            T selection = updatedSelection.get();
            return tryChangeSelection(selection);
        }
        return false;
    }

    @Override
    public final boolean keyUp(int keycode) {
        // unused
        return false;
    }

    @Override
    public final boolean keyTyped(char character) {
        // unused
        return false;
    }

    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // unused
        return false;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // unused
        return false;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        // unused
        return false;
    }

    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        // unused
        return false;
    }

    @Override
    public final boolean scrolled(float amountX, float amountY) {
        // unused
        return false;
    }
}
