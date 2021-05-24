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

    private String map1 = "map1";
    private String map2 = "map2";
    private String map3 = "map3";

    MapManager manager = new MapManager();

    @Test
    public void TestAllMaps() {
        TestLoadingMap(map1);
        TestLoadingMap(map2);
        TestLoadingMap(map3);
    }

    @Test
    public void TestAllLayers() {
        TestLoadingLayers(map1);
        TestLoadingLayers(map2);
        TestLoadingLayers(map3);
    }

    void TestLoadingMap(String mapName) {
        try {
            manager.loadMap(mapName);
        } catch (ExecutionException | InterruptedException e) {
            fail();
        }
        TiledMap map = manager.getCurrentMap();
        assertNotNull(map);
        assertEquals(mapName, manager.getCurrentMapName());
    }

    void TestLoadingLayers(String mapName) {
        try {
            manager.loadMap(mapName);
        } catch (ExecutionException | InterruptedException e) {
            fail();
        }
        assertNotNull(manager.getCollisionLayer());
        assertNotNull(manager.getPacManLayer());
        assertNotNull(manager.getMartySpawnLayer());
        assertNotNull(manager.getEnemySpawnLayer());
        assertNotNull(manager.getBiffSpawnLayer());
        assertNotNull(manager.getPlayerStartPosition());
    }

}