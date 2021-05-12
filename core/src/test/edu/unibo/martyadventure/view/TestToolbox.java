package test.edu.unibo.martyadventure.view;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;

import edu.unibo.martyadventure.view.Toolbox;

class TestToolbox {

    private static final String BADLOGIC_TEXTURE_PATH = "assets/badlogic.jpg";

    @Test
    void loadTextureBlocking() {
        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);
    }

    @Test
    void loadTexturePreloadPartial() {
        Toolbox.loadTexture(BADLOGIC_TEXTURE_PATH);
        // Update only once.. shouldn't load the whole thing in one go, right?
        Toolbox.updateAssetLoading();
        
        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);
    }

    @Test
    void loadTexturePreloaded() {
        Toolbox.loadTexture(BADLOGIC_TEXTURE_PATH);

        // Load the whole thing
        while (!Toolbox.isAssetLoaded(BADLOGIC_TEXTURE_PATH)) {
            Toolbox.updateAssetLoading();
        }

        Texture t = Toolbox.getTexture(BADLOGIC_TEXTURE_PATH);
        assertNotNull(t);
    }

    @Test
    void loadMap() {

    }
}
