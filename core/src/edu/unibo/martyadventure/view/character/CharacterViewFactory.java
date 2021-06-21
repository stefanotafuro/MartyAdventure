package edu.unibo.martyadventure.view.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.weapon.WeaponView;
import edu.unibo.martyadventure.view.weapon.WeaponViewFactory;

public class CharacterViewFactory {
    
    private static final String MARTY_PATH_1 = "Characters/Marty/MartyMove (1).png";
    private static final String MARTY_PATH_2 = "Characters/Marty/MartyMove (2).png";
    private static final String MARTY_PATH_3 = "Characters/Marty/MartyMove (3).png";
    
    private static final String BIFF_PATH_1 = "Characters/Biff/BiffMove (1).png";
    private static final String BIFF_PATH_2 = "Characters/Biff/BiffMove (2).png";
    private static final String BIFF_PATH_3 = "Characters/Biff/BiffMove (3).png";
    
    private static final String DOC_PATH_1 = "Characters/Doc/DocMove (1).png";
    private static final String DOC_PATH_2 = "Characters/Doc/DocMove (2).png";
    private static final String DOC_PATH_3 = "Characters/Doc/DocMove (3).png";
    
    private static final String ENEMY_PATH_1 = "Characters/Bulli/Bullo (1).png";
    private static final String ENEMY_PATH_2 = "Characters/Bulli/Bullo (2).png";
    
    private static final int BOSS_HP_1 = 150;
    private static final int BOSS_HP_2 = 250;
    private static final int BOSS_HP_3 = 500;

    private static final int BULLO_HP_1 = 50;
    private static final int BULLO_HP_2 = 100;
    private static final int BULLO_HP_3 = 150;

    private Map<MapManager.Maps, String> martyTextureMapPath;
    private Map<MapManager.Maps, String> docTextureMapPath;
    private Map<MapManager.Maps, String> biffTextureMapPath;
    private Map<Player, Map<MapManager.Maps, String>> playerMap;
    private Map<Player, Map<MapManager.Maps, String>> bossMap;
    private Map<Player, Player> bossNameMap;
    private Map<Maps, Integer> mapBulloHp;
    private Map<MapManager.Maps, Integer> mapBossHp;
    
    public CharacterViewFactory() {
        martyTextureMapPath = new HashMap<>();
        martyTextureMapPath.put(Maps.MAP1, MARTY_PATH_1);
        martyTextureMapPath.put(Maps.MAP2, MARTY_PATH_2);
        martyTextureMapPath.put(Maps.MAP3, MARTY_PATH_3);
        
        biffTextureMapPath = new HashMap<>();
        biffTextureMapPath.put(Maps.MAP1, BIFF_PATH_1);
        biffTextureMapPath.put(Maps.MAP2, BIFF_PATH_2);
        biffTextureMapPath.put(Maps.MAP3, BIFF_PATH_3);
        
        docTextureMapPath = new HashMap<>();
        docTextureMapPath.put(Maps.MAP1, DOC_PATH_1);
        docTextureMapPath.put(Maps.MAP2, DOC_PATH_2);
        docTextureMapPath.put(Maps.MAP3, DOC_PATH_3);
        
        playerMap = new HashMap<>();
        playerMap.put(Player.MARTY, martyTextureMapPath);
        playerMap.put(Player.DOC, docTextureMapPath);
        playerMap.put(Player.BIFF, biffTextureMapPath);
        
        bossMap = new HashMap<>();
        bossMap.put(Player.BIFF, martyTextureMapPath);
        bossMap.put(Player.MARTY, biffTextureMapPath);
        bossMap.put(Player.DOC, biffTextureMapPath);
        
        mapBulloHp = new HashMap<>();
        mapBulloHp.put(MapManager.Maps.MAP1, BULLO_HP_1);
        mapBulloHp.put(MapManager.Maps.MAP2, BULLO_HP_2);
        mapBulloHp.put(MapManager.Maps.MAP3, BULLO_HP_3);
        
        mapBossHp = new HashMap<>();
        mapBossHp.put(MapManager.Maps.MAP1, BOSS_HP_1);
        mapBossHp.put(MapManager.Maps.MAP2, BOSS_HP_2);
        mapBossHp.put(MapManager.Maps.MAP3, BOSS_HP_3);
        
        bossNameMap = new HashMap<>();
        bossNameMap.put(Player.BIFF, Player.MARTY);
        bossNameMap.put(Player.MARTY, Player.BIFF);
        bossNameMap.put(Player.DOC, Player.BIFF);
    }
    
    public PlayerCharacterView createPlayer(Player player, Vector2 initialPosition, Maps map) throws InterruptedException, ExecutionException {
        return new PlayerCharacterView(player.getName(),initialPosition, loadTexture(player,map));
    }
    
    private TextureRegion loadTexture(Player player, Maps map) throws InterruptedException, ExecutionException {
        Texture texture = new Texture(playerMap.get(player).get(map));
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }
    
    public EnemyCharacterView createEnemy(Vector2 initialPosition, MapManager.Maps map)
            throws InterruptedException, ExecutionException {
        Random r = new Random();
        WeaponView weaponView = WeaponViewFactory.createRandomWeaponView(map);
        WeaponView dropWeaponView = WeaponViewFactory.createRandomWeaponView(map);
        EnemyCharacter b = new EnemyCharacter(dropWeaponView.getWeapon(), "Bullo", mapBulloHp.get(map),
                weaponView.getWeapon());
        EnemyCharacterView bullo = new EnemyCharacterView(initialPosition,
                r.nextBoolean() ? ENEMY_PATH_1 : ENEMY_PATH_2, b, weaponView, dropWeaponView);

        return bullo;
    }
    
    public EnemyCharacterView createBoss(Player player, Vector2 initialPosition, MapManager.Maps map)
            throws InterruptedException, ExecutionException {
        WeaponView weaponView = WeaponViewFactory.createRandomWeaponView(map);
        WeaponView dropWeaponView = WeaponViewFactory.createRandomWeaponView(map);
        EnemyCharacter b = new EnemyCharacter(dropWeaponView.getWeapon(), bossNameMap.get(player).getName(),
                mapBossHp.get(map), weaponView.getWeapon());
        EnemyCharacterView biff = new EnemyCharacterView(initialPosition, bossMap.get(player).get(map), b, weaponView, dropWeaponView);

        return biff;
    }

}
