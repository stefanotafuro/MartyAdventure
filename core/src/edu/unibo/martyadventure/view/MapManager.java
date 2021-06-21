//This class load the maps from the assets
package edu.unibo.martyadventure.view;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private Vector2 playerStartPosition;
    private Vector2 biffStartPosition;

    // map names
    public static enum Maps {
        MAP1, MAP2, MAP3
    }

    // map path
    private static final String MAP_1_PATH = "Level/Map/map1.tmx";
    private static final String MAP_2_PATH = "Level/Map/map2.tmx";
    private static final String MAP_3_PATH = "Level/Map/map3.tmx";

    // layers name
    private static final String MARTY_SPAWN_LAYER_NAME = "MartySpawn";
    private static final String COLLISION_LAYER_NAME = "Collision";
    private static final String PACMAN_LAYER_NAME = "PacMan";
    private static final String ENEMY_SPAWN_LAYER_NAME = "EnemySpawn";
    private static final String BIFF_SPAWN_LAYER_NAME = "BiffSpawn";
    private static final String MARTY_SPAWN_OBJECT_NAME = "MartySpawnObject";
    private static final String BIFF_SPAWN_OBJECT_NAME = "BiffSpawnObject";

    // unit scale
    public final static float UNIT_SCALE = 1 / 16f;

    private Hashtable<Maps, String> mapTable;

    private TiledMap currentMap;
    private Maps currentMapName;

    // map layers
    private MapLayer martySpawnLayer;
    private MapLayer collisionLayer;
    private MapLayer pacManLayer;
    private MapLayer enemySpawnLayer;
    private MapLayer biffSpawnLayer;

    // initialize the hashtable
    public MapManager() {
        mapTable = new Hashtable<>();
        mapTable.put(Maps.MAP1, MAP_1_PATH);
        mapTable.put(Maps.MAP2, MAP_2_PATH);
        mapTable.put(Maps.MAP3, MAP_3_PATH);
    }

    /** @return the current loaded map **/
    public TiledMap getCurrentMap() {
        return currentMap;
    }

    /** @return the current loaded map name **/
    public Maps getCurrentMapName() {
        return currentMapName;
    }

    /**
     * Start loading the given map from file into memory
     *
     * @param mapName the map name you want to start to load
     * @throws IOException
     **/
    public void preLoadMap(Maps mapName) throws IOException {
        // get the map path from the table and check it
        String mapPath = mapTable.get(mapName);
        if (mapPath.isEmpty()) {
            throw new IOException("Invalid map path");
        }
        Toolbox.queueMap(mapPath);
    }

    /**
     * Load the given map name from file to the local memory
     *
     * @return the current loaded map name
     * @param mapName the map name you want to load
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    public void loadMap(Maps mapName) throws InterruptedException, ExecutionException, IOException {

        // get the map path from the table and check it
        String mapPath = mapTable.get(mapName);
        if (mapPath.isEmpty()) {
            throw new IOException("Invalid map path");
        }

        // if we are using another map we dispose that and free memory
        if (currentMap != null) {
            currentMap.dispose();
        }

        // load the map with the toolbox
        currentMap = Toolbox.getMap(mapPath);
        currentMapName = mapName;

        if (currentMap == null) {
            throw new IOException("Map not loaded, loading error");
        }

        // getting layers
        collisionLayer = currentMap.getLayers().get(COLLISION_LAYER_NAME);
        if (collisionLayer == null) {
            throw new IOException("No collision layer loaded");
        }

        martySpawnLayer = currentMap.getLayers().get(MARTY_SPAWN_LAYER_NAME);
        if (martySpawnLayer == null) {
            throw new IOException("No marty layer loaded");
        }

        biffSpawnLayer = currentMap.getLayers().get(BIFF_SPAWN_LAYER_NAME);
        if (biffSpawnLayer == null) {
            throw new IOException("No biff layer loaded");
        }

        enemySpawnLayer = currentMap.getLayers().get(ENEMY_SPAWN_LAYER_NAME);
        if (enemySpawnLayer == null) {
            throw new IOException("No enemy layer loaded");
        }

        pacManLayer = currentMap.getLayers().get(PACMAN_LAYER_NAME);
        if (pacManLayer == null) {
            throw new IOException("No pacman layer loaded");
        }

        // Setting Marty's spawn point
        EllipseMapObject obj = (EllipseMapObject) martySpawnLayer.getObjects().get(MARTY_SPAWN_OBJECT_NAME);
        playerStartPosition = new Vector2();
        playerStartPosition.x = obj.getEllipse().x;
        playerStartPosition.y = obj.getEllipse().y;
        playerStartPosition.x = playerStartPosition.x * UNIT_SCALE;
        playerStartPosition.y = playerStartPosition.y * UNIT_SCALE;
        
     // Setting biff spawn point
        obj = (EllipseMapObject) biffSpawnLayer.getObjects().get(BIFF_SPAWN_OBJECT_NAME);
        biffStartPosition = new Vector2();
        biffStartPosition.x = obj.getEllipse().x -20f;
        biffStartPosition.y = obj.getEllipse().y;
        biffStartPosition.x = biffStartPosition.x * UNIT_SCALE;
        biffStartPosition.y = biffStartPosition.y * UNIT_SCALE;
    }

    /** @return the current loaded collision layer **/
    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }

    /** @return the player start position of the current map **/
    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }
    
    /** @return biff start position of the current map **/
    public Vector2 getBiffStartPosition() {
        return biffStartPosition;
    }

    /** @return the current loaded marty layer **/
    public MapLayer getMartySpawnLayer() {
        return martySpawnLayer;
    }

    /** @return the current loaded pacman layer **/
    public MapLayer getPacManLayer() {
        return pacManLayer;
    }

    /** @return the current loaded enemy spawn layer **/
    public MapLayer getEnemySpawnLayer() {
        return enemySpawnLayer;
    }

    /** @return the current loaded biff layer **/
    public MapLayer getBiffSpawnLayer() {
        return biffSpawnLayer;
    }
}
