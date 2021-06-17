package edu.unibo.martyadventure.view.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.controller.entity.PlayerInputProcessor;
import edu.unibo.martyadventure.model.character.EnemyFactory;
import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.Player;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.character.CharacterViewFactory;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.ui.WorldBannerFactory;

public class MovementGameScreen implements Screen {

    private static final int FADE_TIME = 4;
    private static final int SPRITE_SCALE_FACTOR = 3;
    private PlayerCharacterView playerView;
    private EnemyCharacterView biffView;
    private PlayerInputProcessor inputProcessor;
    private List<EnemyCharacterView> enemyList;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private static MapManager mapManager;
    private static Vector2 playerInitialPosition;
    private CharacterViewFactory cFactory;
    private Viewport viewport;
    private boolean newWorld;
    private Sprite worldBanner;
    private float time = 1;
    private Batch uiBatch;


    public MovementGameScreen(Maps map) {
        eFactory = new EnemyFactory();
        mapManager = new MapManager();
    }

    /**
     * Setup the screen elements
     */
    @Override
    public void show() {
        // camera
        setupViewport(VIEWPORT.ZOOM, VIEWPORT.ZOOM);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT.viewportWidth, VIEWPORT.viewportHeight);

        // rederer
        try {
            mapManager.loadMap(map);
        } catch (InterruptedException | ExecutionException | IOException e1) {
            e1.printStackTrace();
        }
        playerInitialPosition = new Vector2(mapManager.getPlayerStartPosition());

        // camera
        camera = new OrthographicCamera();
        this.viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT, ScreenManager.VIEWPORT.Y_VIEWPORT, camera);

        // rederer
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNIT_SCALE);
        mapRenderer.setView(camera);

        // player
        try {
            playerInitialPosition = mapManager.getPlayerStartPosition();
            playerView = cFactory.createPlayer(player, playerInitialPosition, map);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // biff
        try {
            biffView = cFactory.createBoss(player, mapManager.getBiffStartPosition(), map);
            biffView.setDirection(EntityDirection.DOWN);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        worldBanner = new Sprite(bFactory.createBanner(map));
        worldBanner.setSize(Gdx.app.getGraphics().getWidth() / 2, Gdx.app.getGraphics().getHeight() / 4);
        worldBanner.setPosition(Gdx.app.getGraphics().getWidth() / 2 - worldBanner.getWidth() / 2,
                (Gdx.app.getGraphics().getHeight() / 3) * 2);

        setupList();
    }

    private void setupList() {
        enemyList = new ArrayList<>();
        MapLayer enemyLayer = mapManager.getEnemySpawnLayer();
        Rectangle spawnPoint;

        // iterate all the map box
        for (MapObject o : enemyLayer.getObjects()) {
            spawnPoint = new Rectangle(((RectangleMapObject) o).getRectangle());
            try {
                enemyList.add(cFactory.createEnemy(
                        new Vector2(spawnPoint.x * MapManager.UNIT_SCALE, spawnPoint.y * MapManager.UNIT_SCALE),
                        mapManager.getCurrentMapName()));

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        // set random direction for each enemy
        Random r = new Random();
        enemyList.forEach(e -> e.setDirection(Arrays.asList(EntityDirection.values()).get(r.nextInt(3))));
    }

    /**
     * Setup the screen elements
     */
    @Override
    public void show() {
        resize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        inputProcessor = PlayerInputProcessor.getPlayerInputProcessor();
        inputProcessor.setPlayer(playerView, true);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Called every frame, update the current screen view
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // set the camera position
        camera.position.set(playerView.getCurrentPosition().x, playerView.getCurrentPosition().y, 0f);
        camera.update();

        // check collisions
        try {
            if (!collisionWithMapLayer(playerView.getBoundingBox())) {
                playerView.goNextPosition();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check collisions
        if (isAlive(biffView)) {
            if (playerView.getBoundingBox().overlaps(biffView.getBoundingBox())) {
                ScreenManager.loadCombatScreen(new CombatGameScreen(playerView, biffView));
            }
        } else {
            if (mapManager.getCurrentMapName() == Maps.MAP1) {
                ScreenManager.changeMap(Maps.MAP2);
            } else {
                ScreenManager.changeMap(Maps.MAP3);
            }
            }
        }

        enemyList.forEach(enemy -> {
            if (isAlive(enemy)) {
                if (playerView.getBoundingBox().overlaps(enemy.getBoundingBox())) {
                    ScreenManager.loadCombatScreen(new CombatGameScreen(playerView, enemy));
                }
            }
        });

        // update the input processor
        inputProcessor.update(delta);

        // render the screen
        mapRenderer.setView(camera);
        mapRenderer.render();
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(playerView.getCurrentFrame(), playerView.getCurrentPosition().x,
                playerView.getCurrentPosition().y, SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
        if (isAlive(biffView)) {
            mapRenderer.getBatch().draw(biffView.getCurrentFrame(), biffView.getCurrentPosition().x,
                    biffView.getCurrentPosition().y, SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
        }
        enemyList.forEach(enemy -> {
            if (isAlive(enemy)) {
                mapRenderer.getBatch().draw(enemy.getCurrentFrame(), enemy.getCurrentPosition().x,
                        enemy.getCurrentPosition().y, SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
            }
        });
        mapRenderer.getBatch().end();
    }

    /**
     * for debug, used to render the bounding box of the player
     * 
     * @param r the rectangle to render
     */
    @SuppressWarnings("unused")
    private void renderR(Rectangle r) {
        Pixmap pixmap = new Pixmap((int) r.getWidth(), (int) r.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(44444));
        pixmap.fillRectangle(0, 0, (int) r.getWidth(), (int) r.getHeight());
        mapRenderer.getBatch().draw(new Texture(pixmap), r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Check if the given box is colliding with a map layer box
     * 
     * @param box
     * @return
     * @throws IOException
     */
    private boolean collisionWithMapLayer(Rectangle box) throws IOException {
        MapLayer mapLayer = mapManager.getCollisionLayer();
        Rectangle layerBox = new Rectangle();
        if (mapLayer == null) {
            throw new IOException();
        }

        // iterate all the map box
        for (MapObject o : mapLayer.getObjects()) {
            layerBox = ((RectangleMapObject) o).getRectangle();
            layerBox = new Rectangle(((RectangleMapObject) o).getRectangle());
            layerBox.x *= MapManager.UNIT_SCALE;
            layerBox.y *= MapManager.UNIT_SCALE;
            layerBox.width *= MapManager.UNIT_SCALE;
            layerBox.height *= MapManager.UNIT_SCALE;
            if (box.overlaps(layerBox)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlive(EnemyCharacterView enemy) {
        return enemy.getCharacter().getHp() != 0;
    }

    /**
     * Resize the view
     * 
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        ScreenManager.setupViewport(width / ScreenManager.VIEWPORT.ZOOM, height / ScreenManager.VIEWPORT.ZOOM);
        camera.setToOrtho(false, ScreenManager.VIEWPORT.viewportWidth, ScreenManager.VIEWPORT.viewportHeight);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO player disposed
        mapRenderer.dispose();
        Gdx.input.setInputProcessor(null);
    }
}
