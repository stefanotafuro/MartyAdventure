package test.edu.unibo.martyadventure.model.weapon;

import edu.unibo.martyadventure.model.weapon.Move;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestMove {

    Move HOOK1 = Move.HOOK;

    @Test
    void testLoadingMove() {
        assertEquals(HOOK1.getName(), Move.HOOK.getName());
        assertEquals(HOOK1.getDamage(), Move.HOOK.getDamage());
        assertEquals(HOOK1.getFailRatio(), Move.HOOK.getFailRatio());
        assertEquals(HOOK1.getReloadTime(), Move.HOOK.getReloadTime());
        assertEquals(HOOK1.getMeleeOrRanged(), Move.HOOK.getMeleeOrRanged());
        assertEquals(HOOK1.getLastUse(), Move.HOOK.getLastUse());
        // System.err.println("testLoadingMove ok");
    }

    @Test
    void testSetLastUse() {
        HOOK1.setLastUse(1);
        assertEquals(HOOK1.getLastUse(), 1);
        // System.err.println("testSetLastUse ok");
    }

    @Test
    void testIsUsable() {
        // HOOK1 reloadTime -> 1
        // HOOK1 lastUse -> 1
        assertFalse(HOOK1.isUsable(2));
        assertTrue(HOOK1.isUsable(3));
        // System.err.println("testIsUsable ok");
    }

    @Test
    void testGetRandomMeleeMove() {
        assertEquals(Move.getRandomMeleeMove().getMeleeOrRanged(), 'M');
    }

    @Test
    void testGetRandomRangedMove() {
        assertEquals(Move.getRandomRangedMove().getMeleeOrRanged(), 'R');
    }

}
