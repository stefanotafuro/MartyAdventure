package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.unibo.martyadventure.view.Toolbox;
import test.edu.unibo.martyadventure.GdxTestRunner;

class TestToolbox {

    private static final String BADLOGIC_TEXTURE_PATH = "assets/tests/badlogic.png";
    private static final String TMX_MAP_PATH = "assets/Level/Map/map1.tmx";

    private <R> void loadBlocking(final String path, final Function<String, R> get) {
        R res = get.apply(path);
        assertNotNull(res);

        Toolbox.unloadAsset(path);
    }

    private <R> void loadPreloaded(final String path, final Consumer<String> queue, final Function<String, R> get) {
        queue.accept(path);

        // Load the whole thing
        while (!Toolbox.isAssetLoaded(path)) {
            Toolbox.updateAssetLoading();
        }

        loadBlocking(path, get);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTextureBlocking() {
        loadBlocking(BADLOGIC_TEXTURE_PATH, Toolbox::getTexture);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTexturePreloaded() {
        loadPreloaded(BADLOGIC_TEXTURE_PATH, Toolbox::queueTexture, Toolbox::getTexture);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapBlocking() {
        loadBlocking(TMX_MAP_PATH, Toolbox::getMap);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadTmxMapPreloaded() {
        loadPreloaded(TMX_MAP_PATH, Toolbox::queueMap, Toolbox::getMap);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadBlockingWrongTexture() {
        assertThrows(IllegalArgumentException.class, () -> loadBlocking(TMX_MAP_PATH, Toolbox::getTexture));
        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadPreloadedWrongTexture() {
        assertThrows(IllegalArgumentException.class,
                () -> loadPreloaded(TMX_MAP_PATH, Toolbox::queueTexture, Toolbox::getTexture));
        Toolbox.unloadAsset(TMX_MAP_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadBlockingWrongMap() {
        assertThrows(IllegalArgumentException.class, () -> loadBlocking(BADLOGIC_TEXTURE_PATH, Toolbox::getMap));
        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    @ExtendWith(GdxTestRunner.class)
    void loadPreloadedWrongMap() {
        assertThrows(IllegalArgumentException.class,
                () -> loadPreloaded(BADLOGIC_TEXTURE_PATH, Toolbox::queueMap, Toolbox::getMap));
        Toolbox.unloadAsset(BADLOGIC_TEXTURE_PATH);
    }
}
