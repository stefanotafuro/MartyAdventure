package edu.unibo.martyadventure.view.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
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

    private static final String ENEMY_PATH_1 = "Characters/Bully/Bully (1).png";
    private static final String ENEMY_PATH_2 = "Characters/Bully/Bully (2).png";

    private static final int BOSS_HP_1 = 100;
    private static final int BOSS_HP_2 = 200;
    private static final int BOSS_HP_3 = 300;

    private static final int BULLO_HP_1 = 50;
    private static final int BULLO_HP_2 = 10;
    private static final int BULLO_HP_3 = 15;

    // Static since they're read-only
    private static Map<Player, Player> bossNameMap;
    private static Map<Maps, MapData> mapData;

    static {
        bossNameMap = new EnumMap<>(Player.class);
        bossNameMap.put(Player.BIFF, Player.MARTY);
        bossNameMap.put(Player.MARTY, Player.BIFF);
        bossNameMap.put(Player.DOC, Player.BIFF);

        mapData = new EnumMap<Maps, MapData>(Maps.class);
        mapData.put(Maps.MAP1, new MapData(MARTY_PATH_1, BIFF_PATH_1, DOC_PATH_1, BOSS_HP_1, BULLO_HP_1));
        mapData.put(Maps.MAP2, new MapData(MARTY_PATH_2, BIFF_PATH_2, DOC_PATH_2, BOSS_HP_2, BULLO_HP_2));
        mapData.put(Maps.MAP3, new MapData(MARTY_PATH_3, BIFF_PATH_3, DOC_PATH_3, BOSS_HP_3, BULLO_HP_3));
    }
    
    public PlayerCharacterView createPlayer(Player player, Vector2 initialPosition, Maps map) throws InterruptedException, ExecutionException {
        return new PlayerCharacterView(player.getName(),initialPosition, loadTexture(player,map));
    }

    private TextureRegion loadTexture(Player player, Maps map) {
        final Texture texture = new Texture(mapData.get(map).getTexturePathOf(player));
        return new TextureRegion(texture);
    }

    public EnemyCharacterView createEnemy(Vector2 initialPosition, Maps map)
            throws InterruptedException, ExecutionException {
        final WeaponView weaponView = WeaponViewFactory.createRandomWeaponView(map);
        final WeaponView dropWeaponView = WeaponViewFactory.createRandomWeaponView(map);
        final String bullyTexturePath = ThreadLocalRandom.current().nextBoolean() ? ENEMY_PATH_1 : ENEMY_PATH_2;

        final EnemyCharacter bully = new EnemyCharacter(dropWeaponView.getWeapon(), "Bullo", mapData.get(map).bullyHP,
                weaponView.getWeapon());
        return new EnemyCharacterView(bully, initialPosition, bullyTexturePath, weaponView, dropWeaponView);
    }

    public EnemyCharacterView createBoss(final Player player, final Vector2 initialPosition, final Maps map)
            throws InterruptedException, ExecutionException {
        final WeaponView weaponView = WeaponViewFactory.createRandomWeaponView(map);
        final WeaponView dropWeaponView = WeaponViewFactory.createRandomWeaponView(map);

        final MapData currentMapData = mapData.get(map);
        final Player bossPlayer = bossNameMap.get(player);

        final EnemyCharacter boss = new EnemyCharacter(dropWeaponView.getWeapon(), bossPlayer.getName(),
                currentMapData.bossHp, weaponView.getWeapon());
        return new EnemyCharacterView(boss, initialPosition, currentMapData.getTexturePathOf(bossPlayer), weaponView,
                dropWeaponView);
    }
}
