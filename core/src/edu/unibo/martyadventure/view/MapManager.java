package edu.unibo.martyadventure.view;

import java.util.Hashtable;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private Vector2 playerStartPosition;

    //map names
    private static final String MAP1 = "map1";
    private static final String MAP2 = "map2";
    private static final String MAP3 = "map3";
    //map path
    private static final String MAP1PATH = "Level/Map/map1.tmx";
    private static final String MAP2PATH = "Level/Map/map2.tmx";
    private static final String MAP3PATH = "Level/Map/map3.tmx";
    
    //layers name
    private static final String MARTYSPAWNLAYERNAME = "MartySpawn";
    private static final String COLLISIONLAYERNAME = "Collision";
    private static final String PACMANLAYERNAMENAME = "PacMan";
    private static final String ENEMYSPAWNLAYERNAME = "EnemySpawn";
    private static final String BIFFSPAWNLAYERNAME = "BiffSpawn";
    private static final String MARTYSPAWNOBJECTNAME = "MartySpawnObject";
    
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
    
    public MapManager() {
        mapTable = new Hashtable<>();
        mapTable.put(MAP1, MAP1PATH);
        mapTable.put(MAP2, MAP2PATH);
        mapTable.put(MAP3, MAP3PATH);
    }

    public TiledMap getCurrentMap() {
        return currentMap;
    }

    public String getCurrentMapName() {
        return currentMapName;
    }

    public void loadMap(String mapName) {
        
        //get the map path and check it
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
        collisionLayer = currentMap.getLayers().get(COLLISIONLAYERNAME);
        if (collisionLayer == null) {
            System.err.println("No collision layer loaded");
            return;
        }
        
        martySpawnLayer = currentMap.getLayers().get(MARTYSPAWNLAYERNAME);
        if (martySpawnLayer == null) {
            System.err.println("No marty layer loaded");
            return;
        }
        
        biffSpawnLayer = currentMap.getLayers().get(BIFFSPAWNLAYERNAME);
        if (biffSpawnLayer == null) {
            System.err.println("No biff layer loaded");
            return;
        }
        
        enemySpawnLayer = currentMap.getLayers().get(ENEMYSPAWNLAYERNAME);
        if (enemySpawnLayer == null) {
            System.err.println("No enemy layer loaded");
            return;
        }
        
        pacManLayer = currentMap.getLayers().get(PACMANLAYERNAMENAME);
        if (pacManLayer == null) {
            System.err.println("No pacman layer loaded");
            return;
        }
        
        //Setting marty spawn point
        EllipseMapObject obj = (EllipseMapObject) martySpawnLayer.getObjects().get(MARTYSPAWNOBJECTNAME);
        if (playerStartPosition == null) {
            playerStartPosition = new Vector2();
        }
        playerStartPosition.x = obj.getEllipse().x;
        playerStartPosition.y = obj.getEllipse().y;
        
    }

    public MapLayer getCollisionLayer() {
        return collisionLayer;
    }

    public Vector2 getPlayerStartPosition() {
        return playerStartPosition;
    }

    public MapLayer getMartySpawnLayer() {
        return martySpawnLayer;
    }

    public MapLayer getPacManLayer() {
        return pacManLayer;
    }

    public MapLayer getEnemySpawnLayer() {
        return enemySpawnLayer;
    }

    public MapLayer getBiffSpawnLayer() {
        return biffSpawnLayer;
    }

}
