package test.edu.unibo.martyadventure.model.character;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import edu.unibo.martyadventure.model.character.*;
import test.edu.unibo.martyadventure.model.TestCharacterFactory;

public class TestPlayerCharacter extends TestCharacter {

    @Test
    void testLoadingPlayerCharacter() {
        final PlayerCharacter character = TestCharacterFactory.getPlayerCharacter();
        assertEquals(Shoes.FAST, character.getShoes());
        testLoadingCharacter(character, TestCharacterFactory.PLAYER_NAME);
    }

    @Test
    void testSetShoes() {
        final PlayerCharacter character = TestCharacterFactory.getPlayerCharacter();
        Shoes shoes2 = Shoes.SLOW;
        character.setShoes(shoes2);
        assertEquals(shoes2, character.getShoes());
    }
}
