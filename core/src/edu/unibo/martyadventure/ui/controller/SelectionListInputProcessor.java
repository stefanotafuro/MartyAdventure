package edu.unibo.martyadventure.ui.controller;

import java.util.Optional;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * Allows to choose a selection from an indexed list of elements via UP/DOWN
 * arrow keys and numeric pad.
 */
public class SelectionListInputProcessor extends ControllableSelectionInputProvider<Integer> {

    private final int directionMultiplier;


    /**
     * Tries to map the keycode to a valid single-digit value.
     */
    private Optional<Integer> updateSelectionWithNum(int keycode) {
        String keycodeName = Input.Keys.toString(keycode);
        // Is it a single character?
        if (keycodeName.length() == 1) {
            // Get the unicode/ASCII value and subtract the offset.
            char keycodeCharacter = keycodeName.charAt(0);
            int value = Character.getNumericValue(keycodeCharacter) - '0';

            // The maximum numeric value of a single digit stops at 9.
            if (value >= 0 && value < 10) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * @return an updated selection index changed with the arrows.
     */
    private Optional<Integer> updateSelectionWithArrows(int keycode, int current) {
        if (keycode == Input.Keys.UP) {
            return Optional.of(current + 1 * this.directionMultiplier);
        } else if (keycode == Input.Keys.DOWN) {
            return Optional.of(current - 1 * this.directionMultiplier);
        }
        return Optional.empty();
    }

    @Override
    protected boolean isWithinBounds(Integer candidate, Integer bounds) {
        return candidate >= 0 && candidate < bounds;
    }

    @Override
    protected Optional<Integer> updateSelection(int keycode, Integer current) {
        Optional<Integer> arrowsUpdate = updateSelectionWithArrows(keycode, current);
        if (arrowsUpdate.isPresent()) {
            return arrowsUpdate;
        }

        return updateSelectionWithNum(keycode);
    }

    /**
     * Instances a new input processor for the given selection list.
     * 
     * @param selectionList the list to choose a selection from
     * @param topdown       inverts the UP/DOWN keys relatively to the list indexes
     *                      if true
     * 
     * @throws IndexOutOfBoundsException if the current selection is present by
     *                                   bellow 0 or beyond the list length.
     */
    public SelectionListInputProcessor(final ControllableSelection<Integer> selectionList, final boolean topdown) {
        super(selectionList, 0, Input.Keys.INSERT);
        this.directionMultiplier = topdown ? -1 : 1;
    }

    /**
     * Attempts to parse the given character to a valid selection index and use it
     * to set the current selection.
     * 
     * @return if the character was a valid selection.
     */
    @Override
    public boolean keyTyped(char character) {

        // If it is a single digit within the selection list range
        return tryChangeSelection(value);
    }

}
