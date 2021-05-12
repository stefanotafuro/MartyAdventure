package edu.unibo.martyadventure.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Toolbox {

    /**
     * A static asset manager will cause issues on android: luckily we only support desktop.
     */
    private static AssetManager assetManager;

    static {
        Toolbox.assetManager = new AssetManager();
    }


    public static void unloadAsset(String filePath) {
        Toolbox.assetManager.unload(filePath);
    }

    public static float loadCompletion() {
        return Toolbox.assetManager.getProgress();
    }

    public static int queuedAssetCount() {
        return Toolbox.assetManager.getQueuedAssets();
    }

    public static boolean updateAssetLoading() {
        return Toolbox.assetManager.update();
    }

    public static boolean isAssetLoaded(String filePath) {
        return Toolbox.assetManager.isLoaded(filePath);
    }

    public static void loadMap(String mapPath) {
        
        if (mapPath == null || mapPath.isEmpty()) {
            return;
        }
        
        if( Toolbox.assetManager.isLoaded(mapPath) ){
            return;
        }
        
        Toolbox.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        Toolbox.assetManager.load(mapPath, TiledMap.class);
        Toolbox.assetManager.finishLoadingAsset(mapPath);
    }

    public static TiledMap getMap(String mapPath) {
        return Toolbox.assetManager.get(mapPath, TiledMap.class);
    }

    public static void loadTexture(String texturePath) {
        Toolbox.assetManager.load(texturePath, Texture.class);
    }

    public static Texture getTexture(String texturePath) {
        return Toolbox.assetManager.get(texturePath);
    }
}
