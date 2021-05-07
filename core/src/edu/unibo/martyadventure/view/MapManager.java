package edu.unibo.martyadventure.view;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

public class MapManager {

    private Vector2 playerStartPosition;
    
    private TiledMap currentMap;
    private String currentMapName;
    
    
    public MapManager() {
        
    }

    public TiledMap getCurrentMap() {
        // TODO Auto-generated method stub
        return new TiledMap();
    }

    public String getCurrentMapName() {
        return currentMapName;
    }

    public void loadMap(String mapName) {
        // TODO Auto-generated method stub
        
    }

    public MapLayer getCollisionLayer() {
        // TODO Auto-generated method stub
        return null;
    }
}
