package edu.unibo.martyadventure.model.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.weapon.WeaponFactory;
import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;

public class EnemyFactory {

    private static final String BIFF_PATH_1 = "Characters/Biff/BiffMove (1).png";
    private static final String BIFF_PATH_2 = "Characters/Biff/BiffMove (2).png";
    private static final String BIFF_PATH_3 = "Characters/Biff/BiffMove (3).png";

    private static final String ENEMY_PATH_1 = "Characters/Bulli/Bullo (1).png";
    private static final String ENEMY_PATH_2 = "Characters/Bulli/Bullo (2).png";

    private static final int BIFF_HP_1 = 100;
    private static final int BIFF_HP_2 = 200;
    private static final int BIFF_HP_3 = 300;

    private static final int BULLO_HP_1 = 500;
    private static final int BULLO_HP_2 = 100;
    private static final int BULLO_HP_3 = 150;

    private Map<MapManager.Maps, String> mapPath;
    private Map<MapManager.Maps, Integer> mapBiffHp;
    private Map<MapManager.Maps, Integer> mapBulloHp;

    public EnemyFactory() {
        mapPath = new HashMap<>();
        mapPath.put(MapManager.Maps.MAP1, BIFF_PATH_1);
        mapPath.put(MapManager.Maps.MAP2, BIFF_PATH_2);
        mapPath.put(MapManager.Maps.MAP3, BIFF_PATH_3);

        mapBiffHp = new HashMap<>();
        mapBiffHp.put(MapManager.Maps.MAP1, BIFF_HP_1);
        mapBiffHp.put(MapManager.Maps.MAP2, BIFF_HP_2);
        mapBiffHp.put(MapManager.Maps.MAP3, BIFF_HP_3);
        
        mapBulloHp = new HashMap<>();
        mapBulloHp.put(MapManager.Maps.MAP1, BULLO_HP_1);
        mapBulloHp.put(MapManager.Maps.MAP2, BULLO_HP_2);
        mapBulloHp.put(MapManager.Maps.MAP3, BULLO_HP_3);
    }

    public EnemyCharacterView createBiff(Vector2 initialPosition, MapManager.Maps map)
            throws InterruptedException, ExecutionException {
        EnemyCharacter b = new EnemyCharacter(WeaponFactory.createRandomWeaponLevel("Tirapugni", map), "Biff",
                mapBiffHp.get(map), WeaponFactory.createRandomWeaponLevel("Pugno", map));
        EnemyCharacterView biff = new EnemyCharacterView(initialPosition, mapPath.get(map), b);

        return biff;
    }

    public EnemyCharacterView createEnemy(Vector2 initialPosition, MapManager.Maps map)
            throws InterruptedException, ExecutionException {
        EnemyCharacter b = new EnemyCharacter(WeaponFactory.createRandomWeaponLevel("Banana", map), "Bullo", mapBulloHp.get(map),
                WeaponFactory.createRandomWeaponLevel("Ginocchio", map));
        EnemyCharacterView bullo = new EnemyCharacterView(initialPosition,
                new Random().nextBoolean() ? ENEMY_PATH_1 : ENEMY_PATH_2, b);

        return bullo;
    }

}
