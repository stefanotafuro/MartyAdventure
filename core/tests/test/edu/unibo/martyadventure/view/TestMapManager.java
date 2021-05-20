package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.MapManager;
import test.edu.unibo.martyadventure.GdxTestRunner;

@ExtendWith(GdxTestRunner.class)
public class TestMapManager {

    MapManager manager = new MapManager();

    @Test
    public void TestAllMaps() {
        TestLoadingMap(MapManager.Maps.MAP1);
        TestLoadingMap(MapManager.Maps.MAP2);
        TestLoadingMap(MapManager.Maps.MAP3);
    }

    @Test
    public void TestAllLayers() {
        TestLoadingLayers(MapManager.Maps.MAP1);
        TestLoadingLayers(MapManager.Maps.MAP2);
        TestLoadingLayers(MapManager.Maps.MAP3);
    }

    
    void TestLoadingMap(MapManager.Maps mapName) {
        manager.loadMap(mapName);
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(mapName, manager.getCurrentMapName());
    }


    
    void TestLoadingLayers(MapManager.Maps mapName) {
        manager.loadMap(mapName);
        assertNotNull(manager.getCollisionLayer());
        assertNotNull(manager.getPacManLayer());
        assertNotNull(manager.getMartySpawnLayer());
        assertNotNull(manager.getEnemySpawnLayer());
        assertNotNull(manager.getBiffSpawnLayer());
        assertNotNull(manager.getPlayerStartPosition());
    }

}