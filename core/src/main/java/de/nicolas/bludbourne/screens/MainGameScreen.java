package de.nicolas.bludbourne.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainGameScreen implements Screen {

    private static  final String TAG = MainGameScreen.class.getSimpleName();

    private static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRatio;
    }

    private PlayerController controller;
    private TextureRegion currentPlayerFrame;
    private Sprite currentPlayerSprite;

    private OrthogonalTiledMapRenderer mapRenderer = null;
    private OrthographicCamera camera = null;
    private static MapManager mapManager;

    private static Entity player;

    public MainGameScreen(){
        mapManager = new MapManager();
    }

    @Override
    public void show() {
        // Kamera setup
        setupViewport(10, 10);

        // die aktuelle Größe der Kamera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);

        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNI_SCALE);
        mapRenderer.setView(camera);

        Gdx.app.debug(TAG, "Der UNIT_SCALE Wert ist: " + mapManager.getUnitScale());

        player = new Entity();
        player.init(mapManager.getStartUnitScale().x, mapManager.getStartUnitScale().y);

        currentPlayerSprite = player.getFrameSprite();

        controller = new PlayerController(player);
        Gdx.input.setInputProcessor(controller);
    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1f);

        // die Kamera auf den Spieler ausrichten
        camera.position.set(currentPlayerSprite.getX(), currentPlayerSprite.getY(), 0);
        camera.update();

        player.update(delta);
        currentPlayerFrame = player.getFrame();
    }
}
