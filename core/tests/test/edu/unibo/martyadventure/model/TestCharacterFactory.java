package test.edu.unibo.martyadventure.model;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;

import edu.unibo.martyadventure.model.weapon.Weapon;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;

public class TestCharacterFactory {

    public static final Weapon WEAPON = WeaponFactory.createRandomWeapon("Test random weapon");
    public static final int HP = 500;
    public static final Weapon DROP_ITEM = WEAPON;
    public static final Shoes SHOES = Shoes.FAST;
    public static final String PLAYER_NAME = "Test player character";
    public static final String ENEMY_NAME = "Test enemy character";


    public static PlayerCharacter getPlayerCharacter() {
        return new PlayerCharacter(SHOES, PLAYER_NAME, HP, WEAPON);
    }

    public static EnemyCharacter getEnemyCharacter() {
        return new EnemyCharacter(DROP_ITEM, ENEMY_NAME, HP, WEAPON);
    }

    private TestCharacterFactory() {}
}
