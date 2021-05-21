package test.edu.unibo.martyadventure.view.toolbox;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.toolbox.Toolbox;
import test.edu.unibo.martyadventure.GdxTestRunner;

class TestToolbox {

    private static final String BADLOGIC_TEXTURE_PATH = "assets/tests/badlogic.png";
    private static final String TMX_MAP_PATH = "assets/Level/Map/map1.tmx";


    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTextureBlocking() throws InterruptedException, ExecutionException {
        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH).get();
        assertNotNull(t);

        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapBlocking() throws InterruptedException, ExecutionException {
        TiledMap m = Toolbox.getMap(TMX_MAP_PATH).get();
        assertNotNull(m);

        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadBlockingWrongTexture() {
        assertThrows(IllegalArgumentException.class, () -> Toolbox.getTexture(TMX_MAP_PATH));
        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadBlockingWrongMap() {
        assertThrows(IllegalArgumentException.class, () -> Toolbox.getMap(BADLOGIC_TEXTURE_PATH));
        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }
}
