package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.ExecutionException;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.MapManager;
import test.edu.unibo.martyadventure.GdxTestRunner;

@ExtendWith(GdxTestRunner.class)
public class TestMapManager {

    MapManager manager = new MapManager();
    
    @Test
    public void TestNotPreLoadingAllMaps() throws InterruptedException, ExecutionException, IOException {

        TestNotPreLoadingMap(MapManager.Maps.MAP1);
        TestNotPreLoadingMap(MapManager.Maps.MAP2);
        TestNotPreLoadingMap(MapManager.Maps.MAP3);
    }
    
    @Test
    public void TestAllMaps() throws InterruptedException, ExecutionException, IOException {

        TestLoadingMap(MapManager.Maps.MAP1);
        TestLoadingMap(MapManager.Maps.MAP2);
        TestLoadingMap(MapManager.Maps.MAP3);
    }

    @Test
    public void TestAllLayers() throws InterruptedException, ExecutionException, IOException {
        TestLoadingLayers(MapManager.Maps.MAP1);
        TestLoadingLayers(MapManager.Maps.MAP2);
        TestLoadingLayers(MapManager.Maps.MAP3);
    }

    void TestLoadingMap(MapManager.Maps mapName) throws InterruptedException, ExecutionException, IOException {
        manager.preLoadMap(mapName);
        manager.loadMap(mapName);
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(mapName, manager.getCurrentMapName());
    }
    
    void TestNotPreLoadingMap(MapManager.Maps mapName) throws InterruptedException, ExecutionException, IOException {
        manager.loadMap(mapName);
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(mapName, manager.getCurrentMapName());
    }

    void TestLoadingLayers(MapManager.Maps mapName) throws InterruptedException, ExecutionException, IOException {  
        manager.preLoadMap(mapName);
        manager.loadMap(mapName);
        assertNotNull(manager.getCollisionLayer());
        assertNotNull(manager.getPacManLayer());
        assertNotNull(manager.getMartySpawnLayer());
        assertNotNull(manager.getEnemySpawnLayer());
        assertNotNull(manager.getBiffSpawnLayer());
        assertNotNull(manager.getPlayerStartPosition());
    }

}