package test.edu.unibo.martyadventure.model.character;

import org.junit.jupiter.api.Test;

import edu.unibo.martyadventure.model.character.*;
import test.edu.unibo.martyadventure.model.TestCharacterFactory;

public class TestPlayerCharacter extends TestCharacter {

    @Test
    void testLoadingPlayerCharacter() {
        final PlayerCharacter character = TestCharacterFactory.getPlayerCharacter();
        testLoadingCharacter(character, TestCharacterFactory.PLAYER_NAME);
    }
}
