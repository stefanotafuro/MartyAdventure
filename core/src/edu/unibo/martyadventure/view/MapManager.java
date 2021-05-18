//This class load the maps from the assets
package edu.unibo.martyadventure.view;

import java.util.Hashtable;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private Vector2 playerStartPosition;

    //map names
    private static final String MAP_1 = "map1";
    private static final String MAP_2 = "map2";
    private static final String MAP_3 = "map3";
    //map path
    private static final String MAP_1_PATH = "Level/Map/map1.tmx";
    private static final String MAP_2_PATH = "Level/Map/map2.tmx";
    private static final String MAP_3_PATH = "Level/Map/map3.tmx";
    
    //layers name
    private static final String MARTY_SPAWN_LAYER_NAME = "MartySpawn";
    private static final String COLLISION_LAYER_NAME = "Collision";
    private static final String PACMAN_LAYER_NAME = "PacMan";
    private static final String ENEMY_SPAWN_LAYER_NAME = "EnemySpawn";
    private static final String BIFF_SPAWN_LAYER_NAME = "BiffSpawn";
    private static final String MARTY_SPAWN_OBJECT_NAME = "MartySpawnObject";
    
    //unit scale
    public final static float UNIT_SCALE = 1/16f;
    
    private Hashtable<String,String> mapTable;
    
    private TiledMap currentMap;
    private String currentMapName;
    
    //map layers
    private MapLayer martySpawnLayer;
    private MapLayer collisionLayer;
    private MapLayer pacManLayer;
    private MapLayer enemySpawnLayer;
    private MapLayer biffSpawnLayer;
    
    
    //just initialize the hashtable
    public MapManager() {
        mapTable = new Hashtable<>();
        mapTable.put(MAP_1, MAP_1_PATH);
        mapTable.put(MAP_2, MAP_2_PATH);
        mapTable.put(MAP_3, MAP_3_PATH);
    }

    /** @return the current loaded map**/ 
    public TiledMap getCurrentMap() {
        return currentMap;
    }
    /** @return the current loaded map name**/
    public String getCurrentMapName() {
        return currentMapName;
    }
    /** @param the map name you want to load**/
    public void loadMap(String mapName) {
        
        //get the map path from the table and check it
        String mapPath = mapTable.get(mapName);
        if (mapPath.isEmpty()) {
            System.err.println("Map not loaded, invalid path");
            return;
        }
        
        //if we are using another map we dispose that and free memory
        if (currentMap != null) {
            currentMap.dispose();
        }
        
        //load the map with the toolbox and check
        Toolbox.loadMap(mapPath);

        if (Toolbox.isAssetLoaded(mapPath)) {
            currentMap = Toolbox.getMap(mapPath);
            currentMapName = mapName;
        } else {
            System.err.println("Map not loaded, loading error");
            return;
        }
        
        //getting layers
        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER_NAME);
        if (collisionLayer == null) {
            System.err.println("No collision layer loaded");
            return;
        }
        
        martySpawnLayer = currentMap.getLayers().get(MARTY_SPAWN_LAYER_NAME);
        if (martySpawnLayer == null) {
            System.err.println("No marty layer loaded");
            return;
        }
        
        biffSpawnLayer = currentMap.getLayers().get(BIFF_SPAWN_LAYER_NAME);
        if (biffSpawnLayer == null) {
            System.err.println("No biff layer loaded");
            return;
        }
        
        enemySpawnLayer = currentMap.getLayers().get(ENEMY_SPAWN_LAYER_NAME);
        if (enemySpawnLayer == null) {
            System.err.println("No enemy layer loaded");
            return;
        }
        
        pacManLayer = currentMap.getLayers().get(PACMAN_LAYER_NAME);
        if (pacManLayer == null) {
            System.err.println("No pacman layer loaded");
            return;
        }
        
        //Setting marty spawn point
        EllipseMapObject obj = (EllipseMapObject) martySpawnLayer.getObjects().get(MARTY_SPAWN_OBJECT_NAME);
        if (playerStartPosition == null) {
            playerStartPosition = new Vector2();
        }
        playerStartPosition.x = obj.getEllipse().x;
        playerStartPosition.y = obj.getEllipse().y;
        
    }
    /** @return the current loaded collision layer**/
    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }
    /** @return the player start position of the current map**/
    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }
    /** @return the current loaded marty layer**/
    public MapLayer getMartySpawnLayer() {
        return martySpawnLayer;
    }
    /** @return the current loaded pacman layer**/
    public MapLayer getPacManLayer() {
        return pacManLayer;
    }
    /** @return the current loaded enemy spawn layer**/
    public MapLayer getEnemySpawnLayer() {
        return enemySpawnLayer;
    }
    /** @return the current loaded biff layer**/
    public MapLayer getBiffSpawnLayer() {
        return biffSpawnLayer;
    }

}
