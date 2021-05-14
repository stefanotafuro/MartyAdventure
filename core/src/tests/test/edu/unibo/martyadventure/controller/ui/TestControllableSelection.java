package tests.test.edu.unibo.martyadventure.controller.ui;

import java.util.Optional;

import edu.unibo.martyadventure.controller.ui.ControllableSelection;

public class TestControllableSelection<T> implements ControllableSelection<T> {

    public Optional<T> currentSelection;
    public T bounds;


    public TestControllableSelection(Optional<T> initialSelection, T bounds) {
        this.currentSelection = initialSelection;
        this.bounds = bounds;
    }

    @Override
    public T getSelectionsBounds() {
        return this.bounds;
    }

    @Override
    public Optional<T> getCurrentSelection() {
        return this.currentSelection;
    }

    @Override
    public void select(T selection) {
        this.currentSelection = Optional.of(selection);
    }
}
