package edu.unibo.martyadventure.view;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Manages the asset resources.
 */
public class Toolbox {

    private static final String TEXTURE_EXTENSION = "png";
    private static final String MAP_EXTENSION = "tmx";
    private static final String ALTLAS_EXTENSION = "atlas";
    private static final String SKIN_EXTENSION = "json";

    /**
     * A static asset manager will cause issues on android: luckily we only support
     * desktop.
     */
    private static AssetManager assetManager;

    static {
        Toolbox.assetManager = new AssetManager();
        Toolbox.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }


    private static AssetDescriptor<Texture> getTextureAssetDescriptor(final String path) {
        return new AssetDescriptor<Texture>(getHandle(path, TEXTURE_EXTENSION), Texture.class);
    }

    private static AssetDescriptor<TiledMap> getMapAssetDescriptor(final String path) {
        return new AssetDescriptor<TiledMap>(getHandle(path, MAP_EXTENSION), TiledMap.class);
    }

    private static AssetDescriptor<TextureAtlas> getAtlasAssetDescriptor(final String path) {
        return new AssetDescriptor<TextureAtlas>(getHandle(path, ALTLAS_EXTENSION), TextureAtlas.class);
    }

    private static AssetDescriptor<Skin> getSkinAssetDescriptor(final String path) {
        return new AssetDescriptor<Skin>(getHandle(path, SKIN_EXTENSION), Skin.class);
    }

    /**
     * Verifies that the path isn't empty and tries to verify the file type from the
     * extension.
     *
     * @return a file handle to the asset to load.
     */
    private static FileHandle getHandle(final String path, final String expectedExtension) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Invalid empty asset path");
        }

        final FileHandle fh = new FileHandle(path);
        final String extension = fh.extension();
        if (extension.isBlank() || extension.equals(expectedExtension)) {
            return fh;
        } else {
            throw new IllegalArgumentException("Wrong file type by extension");
        }
    }

    /**
     * Get the described asset. Blocks if isn't not loaded yet.
     */
    private static <T> T getAsset(final AssetDescriptor<T> assetDescriptor) {
        if (!Toolbox.assetManager.isLoaded(assetDescriptor)) {
            // Check if the asset is known by the asset manager.
            if (!Toolbox.assetManager.contains(assetDescriptor.file.path(), assetDescriptor.type)) {
                // Queue the asset for loading.
                Toolbox.assetManager.load(assetDescriptor);
            }

            // Block and load the asset if it's not loaded yet.
            return Toolbox.assetManager.finishLoadingAsset(assetDescriptor);
        }
        return Toolbox.assetManager.get(assetDescriptor);
    }

    /**
     * Unloads the asset if the reference count has reached 0.
     */
    public static void unloadAsset(String filePath) {
        if (Toolbox.assetManager.isLoaded(filePath)) {
            Toolbox.assetManager.unload(filePath);
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
     * @return the number of assets in queue for loading.
     */
    public static int queuedAssetCount() {
        return Toolbox.assetManager.getQueuedAssets();
    }

    /**
     * Progress asset loading.
     * 
     * @return true if all assets have been loaded, false otherwise.
     */
    public static boolean updateAssetLoading() {
        return Toolbox.assetManager.update();
    }

    /**
     * @return true if the asset at the path has been fully loaded.
     */
    public static boolean isAssetLoaded(final String filePath) {
        return Toolbox.assetManager.isLoaded(filePath);
    }

    /**
     * Queues a map for loading.
     */
    public static void queueMap(final String mapPath) {
        Toolbox.assetManager.load(getMapAssetDescriptor(mapPath));
    }

    /**
     * Queues an atlas for loading.
     */
    public static void queueAtlas(final String atlasPath) {
        Toolbox.assetManager.load(getAtlasAssetDescriptor(atlasPath));
    }

    /**
     * Queues a skin for loading.
     */
    public static void queueSkin(final String skinPath) {
        Toolbox.assetManager.load(getSkinAssetDescriptor(skinPath));
    }

    /**
     * Queues a texture for loading.
     */
    public static void queueTexture(final String texturePath) {
        Toolbox.assetManager.load(getTextureAssetDescriptor(texturePath));
    }

    /**
     * Get the map at the path. Block if the asset hasn't been fully loaded yet.
     *
     * @return the map asset at the given path.
     */
    public static TiledMap getMap(final String mapPath) {
        return getAsset(getMapAssetDescriptor(mapPath));
    }

    /**
     * Get the texture at the path. Block if the asset hasn't been fully loaded yet.
     *
     * @return the texture asset at the given path.
     */
    public static Texture getTexture(final String texturePath) {
        return getAsset(getTextureAssetDescriptor(texturePath));
    }

    /**
     * Get the atlas at the path. Block if the asset hasn't been fully loaded yet.
     *
     * @return the atlas asset at the given path.
     */
    public static TextureAtlas getAtlas(final String atlasPath) {
        return getAsset(getAtlasAssetDescriptor(atlasPath));
    }

    /**
     * Get the skin at the path. Block if the asset hasn't been fully loaded yet.
     *
     * @return the skin asset at the given path.
     */
    public static Skin getSkin(final String skinPath) {
        return getAsset(getSkinAssetDescriptor(skinPath));
    }

    /**
     * Prevent instantiation.
     */
    private Toolbox() {}
}
