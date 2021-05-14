package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

import edu.unibo.martyadventure.view.Toolbox;
import test.edu.unibo.martyadventure.GdxTestRunner;

class TestToolbox {

    private static final String BADLOGIC_TEXTURE_PATH = "assets/tests/badlogic.png";
    private static final String TMX_MAP_PATH = "assets/Level/Map/map1.tmx";


    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTextureBlocking() {
        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);

        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTexturePreloadPartial() {
        Toolbox.loadTexture(BADLOGIC_TEXTURE_PATH);
        // Update only once... it shouldn't load the whole thing in one go, right?
        Toolbox.updateAssetLoading();

        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);

        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTexturePreloaded() {
        Toolbox.loadTexture(BADLOGIC_TEXTURE_PATH);

        // Load the whole thing
        while (!Toolbox.isAssetLoaded(BADLOGIC_TEXTURE_PATH)) {
            Toolbox.updateAssetLoading();
        }

        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);

        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapBlocking() {
        TiledMap m = Toolbox.getMap(TMX_MAP_PATH);
        assertNotNull(m);

        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapPreloadPartial() {
        Toolbox.loadMap(TMX_MAP_PATH);
        // Update only once... it shouldn't load the whole thing in one go, right?
        Toolbox.updateAssetLoading();

        TiledMap m = Toolbox.getMap(TMX_MAP_PATH);
        assertNotNull(m);

        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapPreloaded() {
        Toolbox.loadMap(TMX_MAP_PATH);

        // Load the whole thing
        while (!Toolbox.isAssetLoaded(TMX_MAP_PATH)) {
            Toolbox.updateAssetLoading();
        }

        TiledMap m = Toolbox.getMap(TMX_MAP_PATH);
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
    void loadPreloadedWrongTexture() {
        assertThrows(IllegalArgumentException.class, () -> {
            Toolbox.loadTexture(TMX_MAP_PATH);

            // Load the whole thing
            while (!Toolbox.isAssetLoaded(TMX_MAP_PATH)) {
                Toolbox.updateAssetLoading();
            }

            @SuppressWarnings("unused")
            Texture t = Toolbox.getTexture(TMX_MAP_PATH);
        });
        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadBlockingWrongMap() {
        assertThrows(IllegalArgumentException.class, () -> Toolbox.getMap(BADLOGIC_TEXTURE_PATH));
        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadPreloadedWrongMap() {
        assertThrows(IllegalArgumentException.class, () -> {
            Toolbox.loadMap(BADLOGIC_TEXTURE_PATH);

            // Load the whole thing
            while (!Toolbox.isAssetLoaded(BADLOGIC_TEXTURE_PATH)) {
                Toolbox.updateAssetLoading();
            }

            @SuppressWarnings("unused")
            TiledMap m = Toolbox.getMap(BADLOGIC_TEXTURE_PATH);
        });
        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }
}
