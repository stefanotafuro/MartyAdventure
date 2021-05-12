package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.MapManager;

public class TestMapManager {

    String map1 = "map1";
    String map2 = "map2";
    String map3 = "map3";
    
    MapManager manager = new MapManager();
    
    void TestLoadingMap(String mapName) {
        manager.loadMap(mapName);
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(mapName, manager.getCurrentMapName());
    }
    
    void TestLoadingLayers(String mapName) {
        manager.loadMap(mapName);
        assertNotNull(manager.getCollisionLayer());
        assertNotNull(manager.getPacManLayer());
        assertNotNull(manager.getMartySpawnLayer());
        assertNotNull(manager.getEnemySpawnLayer());
        assertNotNull(manager.getBiffSpawnLayer());
    }
    
    @Test
    public void TestAllMaps() {
        TestLoadingMap(map1);
        TestLoadingMap(map2);
        TestLoadingMap(map3);
    }
    
    public void TestAllLayers() {
        TestLoadingLayers(map1);
        TestLoadingLayers(map2);
        TestLoadingLayers(map3);
    }
}
