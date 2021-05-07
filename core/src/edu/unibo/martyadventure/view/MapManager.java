package edu.unibo.martyadventure.view;

<<<<<<< HEAD
import java.util.Hashtable;

import com.badlogic.gdx.maps.MapLayer;
=======
>>>>>>> 71885e7... started TestMapManager and Map
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private Vector2 playerStartPosition;
    
<<<<<<< HEAD
    //map names
    private static final String MAP1 = "map1";
    private static final String MAP2 = "map2";
    private static final String MAP3 = "map3";
    //map path
    private static final String MAP1PATH = "Level/Map/map1.tmx";
    private static final String MAP2PATH = "Level/Map/map2.tmx";
    private static final String MAP3PATH = "Level/Map/map3.tmx";
    
    private Hashtable<String,String> mapTable;
    
    private TiledMap currentMap;
    private String currentMapName;
    
    //map layers
    private MapLayer martySpawnLayer;
    private MapLayer collisionLayer;
    private MapLayer pacManLayer;
    
    public MapManager() {
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
=======
    private TiledMap currentMap;
    
>>>>>>> 71885e7... started TestMapManager and Map
}
