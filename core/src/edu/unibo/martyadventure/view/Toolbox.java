package edu.unibo.martyadventure.view;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    private static final String TEXTURE_EXTENSION = "png";
    private static final String MAP_EXTENSION = "tmx";

    /*
     * A static asset manager will cause issues on android: luckily we only support
     * desktop.
     */
    private static AssetManager assetManager;
    // Tracks the handles of each asset.
    private static HashMap<String, Future> assetHandles;
    // Asset loader.
    private static Executor loader;

    static {
        Toolbox.assetManager = new AssetManager();
        Toolbox.assetManager.setLoader(TiledMap.class, new TmxMapLoader());

        Toolbox.assetHandles = new HashMap<String, Future>();

        Toolbox.loader = Executors.newSingleThreadExecutor();
        Toolbox.loader.execute(Toolbox::load);
    }

    /**
     * Keep looping to load all the assets that get queued.
     */
    private static void load() {
        while (true) {
            Toolbox.assetManager.update();
        }
    }

    /**
     * @return the extension at the end of the path, if any.
     */
    private static String getExtension(final String path) {
        int dotIndex = path.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return path.substring(dotIndex + 1);
    }

    /**
     * Get the future handle of the given asset, queuing it for loading if it's not
     * already queued.
     * 
     * @param path      the path to the asset file to load.
     * @param extension the expected extension of the asset.
     * @param type      the type of the asset to load.
     * @param param     the appropriate AssetLoader parameters for the type.
     * @return the future handle to the given asset.
     */
    private static <T> Future<T> getAsset(final String path, final String extension, Class<T> type,
            final AssetLoaderParameters<T> param) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Invalid empty asset path");
        }

        final String ext = getExtension(path);
        if (ext.isEmpty() || !extension.equalsIgnoreCase(ext)) {
            throw new IllegalArgumentException("Wrong extension file type");
        }

        synchronized (Toolbox.assetHandles) {
            if (!Toolbox.assetHandles.containsKey(path)) {
                CompletableFuture<T> handle = new CompletableFuture<T>();
                param.loadedCallback = (am, p, t) -> handle.complete((T) am.get(p, t));

                Toolbox.assetHandles.put(path, handle);
                Toolbox.assetManager.load(path, type, param);
            }
            return (Future<T>) Toolbox.assetHandles.get(path);
        }
    }

    /**
     * Unloads the asset if the reference count has reached 0. Don't do anything if
     * the asset isn't loaded.
     * 
     * @param path the path to the asset file to unload.
     */
    public static void unloadAsset(final String path) {
        synchronized (Toolbox.assetHandles) {
            if (Toolbox.assetHandles.remove(path) != null) {
                Toolbox.assetManager.unload(path);
            }
        }
    }

    /**
     * @return a value between 0.0 and 1.0, representing the percentage of known
     *         assets loaded so far.
     */
    public static float loadCompletion() {
        return Toolbox.assetManager.getProgress();
    }

    /**
     * @return the number of assets queued for loading.
     */
    public static int queuedAssetCount() {
        return Toolbox.assetManager.getQueuedAssets();
    }

    /**
     * @param path the path to the asset file to query for.
     * @return true if the asset at the path has been fully loaded.
     */
    public static boolean isAssetLoaded(final String path) {
        synchronized (Toolbox.assetHandles) {
            Future handle = Toolbox.assetHandles.get(path);
            return handle != null && handle.isDone();
        }
    }

    /**
     * Get the future handle of the map at the path. Queue the asset for loading if
     * it's not queued yet.
     * 
     * @param path the path to the map asset file to load.
     * @return the future handle for the map.
     */
    public static Future<TiledMap> getMap(final String mapPath) {
        return getAsset(mapPath, MAP_EXTENSION, TiledMap.class, new Parameters());
    }

    /**
     * Get the future handle of the texture at the path. Queue the asset for loading
     * if it's not queued yet.
     * 
     * @param path the path to the texture asset file to load.
     * @return the future handle for the texture.
     */
    public static Future<Texture> getTexture(final String texturePath) {
        return getAsset(texturePath, TEXTURE_EXTENSION, Texture.class, new TextureParameter());
    }

    /**
     * Prevent instantiation.
     */
    private Toolbox() {
    }
}