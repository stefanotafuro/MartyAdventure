package edu.unibo.martyadventure.ui.controller;

import java.util.Optional;

public interface ControllableSelection<T> {
    /**
     * @return the number of possible selections indexed from 0.
     */
    T getSelectionsBounds();

    /**
     * @return the current selection.
     */
    Optional<T> getCurrentSelection();

    /**
     * @param the selection at the index has been chosen.
     */
    void select(T selection);
}
