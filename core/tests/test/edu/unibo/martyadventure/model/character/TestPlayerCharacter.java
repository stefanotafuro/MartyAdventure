package test.edu.unibo.martyadventure.model.character;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.*;
import edu.unibo.martyadventure.model.weapon.*;

public class TestPlayerCharacter {

    String name = "Test";
    List<Move> moveList = new ArrayList<>(List.of(Move.UPPERCUT, Move.HOOK, Move.JAB, Move.SUPERMANPUNCH));
    Weapon weapon = new WeaponFactory().newWeapon("Gun", "Ranged", 10, moveList);
    Shoes shoes = Shoes.FAST;
    int hp = 500;
    float speed = 2;
    Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(1, 1);

    PlayerCharacter characterTest = new PlayerCharacter(shoes, name, hp, weapon, position, speed, velocity);

    @Test
    void testLoadingPlayerCharacter() {
        assertEquals(shoes, characterTest.getShoes());
        assertEquals(name, characterTest.getName());
        assertEquals(hp, characterTest.getHp());
        assertEquals(weapon, characterTest.getWeapon());
        assertEquals(position, characterTest.getPosition());
        assertEquals(speed, characterTest.getSpeed());
        assertEquals(velocity, characterTest.getVelocity());
        // System.err.println("testLoadingPlayerCharacter OK");
    }

    @Test
    void testSetShoes() {
        Shoes shoes2 = Shoes.SLOW;
        characterTest.setShoes(shoes2);
        assertEquals(shoes2, characterTest.getShoes());
        // System.err.println("testSetShoes ok");
    }

    public PlayerCharacter getPlayerCharacter() {
        return characterTest;
    }
}
