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
    
    private static final int BULLO_HP_1 = 50;
    private static final int BULLO_HP_2 = 100;
    private static final int BULLO_HP_3 = 150;
    
    private Map<MapManager.Maps, String> mapPath;
    
    public EnemyFactory(){
        mapPath = new HashMap<>();
        mapPath.put(MapManager.Maps.MAP1, BIFF_PATH_1);
        mapPath.put(MapManager.Maps.MAP2, BIFF_PATH_2);
        mapPath.put(MapManager.Maps.MAP3, BIFF_PATH_3);
    }
    
    public EnemyCharacterView createBiff(Vector2 initialPosition, MapManager.Maps map) throws InterruptedException, ExecutionException {
        EnemyCharacter b = new EnemyCharacter(WeaponFactory.createRandomMeleeWeapon("Tirapugni"), "Biff", BIFF_HP_1, WeaponFactory.createRandomMeleeWeapon("punch"));
        EnemyCharacterView biff = new EnemyCharacterView(initialPosition, mapPath.get(map) , b );
        
        return biff;
    }
    
    public EnemyCharacterView createEnemy(Vector2 initialPosition, MapManager.Maps map) throws InterruptedException, ExecutionException {
        EnemyCharacter b = new EnemyCharacter(WeaponFactory.createRandomMeleeWeapon("Tirapugni"), "Bullo", BULLO_HP_1, WeaponFactory.createRandomMeleeWeapon("punch"));
        EnemyCharacterView bullo = new EnemyCharacterView(initialPosition, new Random().nextBoolean() ? ENEMY_PATH_1 : ENEMY_PATH_2 , b );
        
        return bullo;
    }

}
