package edu.unibo.martyadventure.view.toolbox;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.HashMap;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

/**
 * Manages the asset resources.
 */
public class Toolbox {

    /**
     * A static asset manager will cause issues on android: luckily we only support
     * desktop.
     */
    private static AssetManager assetManager;
    private static HashMap<String, Future> assetHandles;

    static {
        Toolbox.assetManager = new AssetManager();
        Toolbox.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        Toolbox.assetHandles = new HashMap<String, Future>();
    }


    private static <T> boolean registerHandle(final String path, final AssetLoaderParameters<T> param) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Invalid empty asset path");
        }

        synchronized (Toolbox.assetHandles) {
            if (!Toolbox.assetHandles.containsKey(path)) {
                CompletableFuture<T> handle = new CompletableFuture<T>();
                param.loadedCallback = (am, p, type) -> handle.complete((T) am.get(p, type));

                Toolbox.assetHandles.put(path, handle);
                return true;
            }
            return false;
        }
    }

    /**
     * Verifies that the path isn't empty
     */
    private static <T> void queueAsset(final String path, final AssetLoaderParameters<T> param, final Class<T> type) {
        if (registerHandle(path, param)) {
            Toolbox.assetManager.load(path, type, param);
        }
    }

    /**
     * Get the described asset. Blocks if isn't not loaded yet.
     */
    private static <T> Future<T> getAsset(final String path, final AssetLoaderParameters<T> param) {
        registerHandle(path, param);
        return (Future<T>) Toolbox.assetHandles.get(path);
    }

    /**
     * Unloads the asset if the reference count has reached 0.
     */
    public static void unloadAsset(String filePath) {
        synchronized (Toolbox.assetHandles) {
            Toolbox.assetHandles.remove(filePath);
        }
        Toolbox.assetManager.unload(filePath);
    }

    /**
     * @return a value between 0.0 and 1.0, representing the percentage of known
     *         assets loaded so far.
     */
    public static float loadCompletion() {
        return Toolbox.assetManager.getProgress();
    }

    /**
     * @return the number of assets in queue for loading.
     */
    public static int queuedAssetCount() {
        return Toolbox.assetManager.getQueuedAssets();
    }

    /**
     * @return true if the asset at the path has been fully loaded.
     */
    public static boolean isAssetLoaded(final String filePath) {
        synchronized (Toolbox.assetHandles) {
            Future handle = Toolbox.assetHandles.get(filePath);
            return handle != null && handle.isDone();
        }
    }

    /**
     * Queues a map for loading.
     */
    public static void loadMap(final String mapPath) {
        queueAsset(mapPath, new Parameters(), TiledMap.class);
    }

    /**
     * Queues a texture for loading.
     */
    public static void loadTexture(final String texturePath) {
        queueAsset(texturePath, new TextureParameter(), Texture.class);
    }

    /**
     * Get the map at the path. Block if the asset hasn't been fully loaded yet.
     * 
     * @return the map asset at the given path.
     */
    public static Future<TiledMap> getMap(final String mapPath) {
        return getAsset(mapPath, new Parameters());
    }

    /**
     * Get the texture at the path. Block if the asset hasn't been fully loaded yet.
     * 
     * @return the texture asset at the given path.
     */
    public static Future<Texture> getTexture(final String texturePath) {
        return getAsset(texturePath, new TextureParameter());
    }

    /**
     * Prevent instantiation.
     */
    private Toolbox() {}
}
