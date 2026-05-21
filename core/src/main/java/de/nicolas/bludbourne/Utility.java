package de.nicolas.bludbourne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

/** Utility Klasse für Asset Management */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();

    public static final AssetManager assetManager = new AssetManager();

    private static InternalFileHandleResolver filePathResolver = new InternalFileHandleResolver();

    public static void unloadAsset(String assetFilenamePath){
        if (assetManager.isLoaded(assetFilenamePath)){
            assetManager.unload(assetFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset nicht geladen. auf " + assetFilenamePath + " kein unload möglich.");
        }
    }

    /** Fortschrit des AssetManagers */
    public static float loadCompleted(){
        return assetManager.getProgress();
    }

    /** anstehende Assets zum Laden */
    public static int assetsQueued(){
        return assetManager.getQueuedAssets();
    }

    /** Aufruf des AssetManagers bei asynchronem laden */
    public static boolean updateAssetsLoading(){
        return assetManager.update();
    }
    /** Frage, ob ein bestimmtes Asset geladen ist */
    public static boolean isAssetLoaded(String fileName){
        return assetManager.isLoaded(fileName);
    }

    /** laden einer Map */
    public static void loadMapAsset(String mapFilenamePath){
        if(mapFilenamePath == null || mapFilenamePath.isEmpty()){
            return;
        }

        // lade Asset
        if (filePathResolver.resolve(mapFilenamePath).exists()) {
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(filePathResolver));

            assetManager.load(mapFilenamePath, TiledMap.class);

            // Bis wir einen loadingScreen eingefürgt haben, weiteren Programmablauf blocken
            assetManager.finishLoadingAsset(mapFilenamePath);
            Gdx.app.debug(TAG, "Map geladen: " + mapFilenamePath);
        }
        else {
            Gdx.app.debug(TAG, "Die Map existiert nicht: " + mapFilenamePath);
        }
    }

    /** Abruf einer Map */
    public static TiledMap getMapAsset(String mapFilenamePath){
        TiledMap map = null;

        // wenn der Ladevorgang beendet ist
        if (assetManager.isLoaded(mapFilenamePath)){
            map = assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, "Die Map ist nicht geladen: " + mapFilenamePath);
        }

        return map;
    }

    /** Laden eines TGextures */
    public static void loadTextureAsset(String textureFilenamePath){
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()){
            return;
        }

        // lade Asset
        if (filePathResolver.resolve(textureFilenamePath).exists()){
            assetManager.setLoader(Texture.class, new TextureLoader(filePathResolver));

            assetManager.load(textureFilenamePath, Texture.class);

            // Bis wir einen loadingScreen eingefürgt haben, weiteren Programmablauf blocken
            assetManager.finishLoadingAsset(textureFilenamePath);
        }
        else {
            Gdx.app.debug(TAG, "Die Texture existiert nicht: " + textureFilenamePath);
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath){
        Texture texture = null;

        // wenn der Ladevorgang beendet ist
        if (assetManager.isLoaded(textureFilenamePath)){
            texture = assetManager.get(textureFilenamePath, Texture.class);
        }
        else {
            Gdx.app.debug(TAG, "Die Texture ist nicht geladen: " + textureFilenamePath);
        }

        return texture;
    }
}
