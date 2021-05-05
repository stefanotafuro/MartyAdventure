package edu.unibo.martyadventure.controller.ui;

import java.util.Optional;

import org.javatuples.Pair;

import com.badlogic.gdx.Input;

/**
 * Allows to choose a selection from a grid of elements via the arrow keys.
 */
public class SelectionGridInputProcessor extends SelectionInputProcessor<Pair<Integer, Integer>> {

    private final int xAxisMultiplier;
    private final int yAxisMultiplier;


    @Override
    protected boolean isWithinBounds(Pair<Integer, Integer> candidate, Pair<Integer, Integer> bounds) {
        final int x = candidate.getValue0();
        final int y = candidate.getValue1();

        return x >= 0 && x < bounds.getValue0() && y >= 0 && y < bounds.getValue1();
    }

    @Override
    protected Optional<Pair<Integer, Integer>> updateSelection(int keycode, Pair<Integer, Integer> current) {
        switch (keycode) {
        case Input.Keys.UP:
            return Optional.of(current.setAt0(current.getValue0() + 1 * this.xAxisMultiplier));
        case Input.Keys.DOWN:
            return Optional.of(current.setAt0(current.getValue0() - 1 * this.xAxisMultiplier));
        case Input.Keys.LEFT:
            return Optional.of(current.setAt1(current.getValue1() - 1 * this.yAxisMultiplier));
        case Input.Keys.RIGHT:
            return Optional.of(current.setAt1(current.getValue1() + 1 * this.yAxisMultiplier));
        default:
            return Optional.empty();
        }
    }

    public SelectionGridInputProcessor(ControllableSelection<Pair<Integer, Integer>> selectionGrid,
            final boolean invertXAxis, final boolean invertYAxis) {
        super(selectionGrid, new Pair<Integer, Integer>(0, 0), Input.Keys.INSERT);
        this.xAxisMultiplier = invertXAxis ? -1 : 1;
        this.yAxisMultiplier = invertYAxis ? -1 : 1;
    }
}
