package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.MapManager;

public class TestMapManager {

    String map1 = "map1";
    String map2 = "map1";
    String map3 = "map1";
    
    MapManager manager = new MapManager();
    
    void TestLoadingMap(String MapName) {
        manager.loadMap(map1);
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(map1, manager.getCurrentMapName());
    }
    
    void TestLoadingLayers(String MapName) {
        manager.loadMap(map1);
        assertNotNull(manager.getCollisionLayer());
        assertNotNull(manager.getPacManLayer());
        assertNotNull(manager.getMartySpawnLayer());
        assertNotNull(manager.getEnemySpawnLayer());
        assertNotNull(manager.getBiffSpawnLayer());
    }
    
    @Test
    void TestAllMaps() {
        TestLoadingMap(map1);
        TestLoadingMap(map2);
        TestLoadingMap(map3);
    }
    
    @Test
    void TestAllLayers() {
        TestLoadingLayers(map1);
        TestLoadingLayers(map2);
        TestLoadingLayers(map3);
    }
}
