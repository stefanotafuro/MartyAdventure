package edu.unibo.martyadventure.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Toolbox {

    /**
     * A static asset manager will cause issues on android: luckily we only support desktop.
     */
    private static AssetManager assetManager;

    static {
        Toolbox.assetManager = new AssetManager();
    }


    public void unloadAsset(String filePath) {
        Toolbox.assetManager.unload(filePath);
    }

    public float loadCompletion() {
        return Toolbox.assetManager.getProgress();
    }

    public int queuedAssetCount() {
        return Toolbox.assetManager.getQueuedAssets();
    }

    public boolean updateAssetLoading() {
        return Toolbox.assetManager.update();
    }

    public boolean isAssetLoaded(String filePath) {
        return Toolbox.assetManager.isLoaded(filePath);
    }

    public void loadMap(String mapPath) {
        Toolbox.assetManager.load(mapPath, TiledMap.class);
    }

    public TiledMap getMap(String mapPath) {
        return Toolbox.assetManager.get(mapPath);
    }

    public void loadTexture(String texturePath) {
        Toolbox.assetManager.load(texturePath, Texture.class);
    }

    public Texture getTexture(String texturePath) {
        return Toolbox.assetManager.get(texturePath);
    }
}
