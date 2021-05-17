package test.edu.unibo.martyadventure.model.character;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.weapon.*;

public class TestEnemyCharacter {

    String name = "Test";
    List<Move> moveList = new ArrayList<>(List.of(Move.SHOOT, Move.HOOK, Move.SHOOT, Move.HOOK));
    Weapon weapon = new WeaponFactory().newWeapon("Gun", "Ranged", 10, moveList);
    Weapon dropitem = weapon;
    int hp = 500;
    float speed = 2;
    Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(1, 1);

    EnemyCharacter characterTest = new EnemyCharacter(dropitem, name, hp, weapon, position, speed, velocity);

    @Test
    void testLoadingEnemyCharacter() {
        assertEquals(dropitem, characterTest.getDropitem());
        assertEquals(name, characterTest.getName());
        assertEquals(hp, characterTest.getHp());
        assertEquals(weapon, characterTest.getWeapon());
        assertEquals(position, characterTest.getPosition());
        assertEquals(speed, characterTest.getSpeed());
        assertEquals(velocity, characterTest.getVelocity());
        // System.err.println("testLoadingEnemyCharacter OK");
    }

    @Test
    void testsetDropitem() {
        Weapon weapon2 = new WeaponFactory().newWeapon("Punch", "Melee", 10, moveList);
        characterTest.setDropitem(weapon2);
        assertEquals(weapon2, characterTest.getDropitem());
        // System.err.println("testsetDropitem ok");
    }

    public EnemyCharacter getEnemyCharacter() {
        return characterTest;
    }
}
