package tests.test.edu.unibo.martyadventure.controller.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Input;

import edu.unibo.martyadventure.controller.ui.SelectionListInputProcessor;

class TestSelectionListInputProcessor {

    private static final int TEST_LIST_LENGTH = 10;
    private static final int TEST_LIST_MAX_INDEX = TEST_LIST_LENGTH - 1;


    private int moveSelection(boolean topDown, int index, int keycode, int keypresses) {
        TestControllableSelection<Integer> sel = new TestControllableSelection<Integer>(Optional.of(index),
                TEST_LIST_LENGTH);
        SelectionListInputProcessor list = new SelectionListInputProcessor(sel, topDown);

        for (int i = 0; i < keypresses; i++) {
            list.keyDown(keycode);
        }

        return list.getCurrentSelection();
    }

    @Test
    void testBellowBoundsArrows() {
        // Try go lower than 0
        assertEquals(0, moveSelection(false, 0, Input.Keys.DOWN, TEST_LIST_LENGTH / 2));
        // Topdown
        assertEquals(TEST_LIST_MAX_INDEX,
                moveSelection(true, TEST_LIST_MAX_INDEX, Input.Keys.DOWN, TEST_LIST_LENGTH / 2));
    }

    @Test
    void testAboveBoundsArrows() {
        // Try go lower than 0
        assertEquals(TEST_LIST_MAX_INDEX,
                moveSelection(false, TEST_LIST_MAX_INDEX, Input.Keys.UP, TEST_LIST_LENGTH / 2));
        // Topdown
        assertEquals(0, moveSelection(true, 0, Input.Keys.UP, TEST_LIST_LENGTH / 2));
    }

    @Test
    void testChangeSelection() {
        assertEquals(7, moveSelection(false, TEST_LIST_LENGTH / 2, Input.Keys.UP, 2));
        assertEquals(3, moveSelection(false, TEST_LIST_LENGTH / 2, Input.Keys.DOWN, 2));

        assertEquals(7, moveSelection(true, TEST_LIST_LENGTH / 2, Input.Keys.DOWN, 2));
        assertEquals(3, moveSelection(true, TEST_LIST_LENGTH / 2, Input.Keys.UP, 2));
    }

    @Test
    void testConstructorArgs() {
        // Shouldn't throw
        try {
            TestControllableSelection<Integer> sel = new TestControllableSelection<Integer>(Optional.of(1),
                    TEST_LIST_LENGTH);
            SelectionListInputProcessor list = new SelectionListInputProcessor(sel, false);
        } catch (Exception e) {
            fail();
        }

        // Under 0, should throw
        try {
            TestControllableSelection<Integer> sel = new TestControllableSelection<Integer>(Optional.of(-100),
                    TEST_LIST_LENGTH);
            SelectionListInputProcessor list = new SelectionListInputProcessor(sel, false);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        } catch (Exception e) {
            fail();
        }

        // Beyond the length, should throw
        try {
            TestControllableSelection<Integer> sel = new TestControllableSelection<Integer>(
                    Optional.of(TEST_LIST_LENGTH + 100), TEST_LIST_LENGTH);
            SelectionListInputProcessor list = new SelectionListInputProcessor(sel, false);
        } catch (IndexOutOfBoundsException e) {
            assertNotNull(e);
        } catch (Exception e) {
            fail();
        }
    }

}
