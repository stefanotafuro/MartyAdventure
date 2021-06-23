package edu.unibo.martyadventure.view.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

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
import edu.unibo.martyadventure.model.character.Character;
import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.Player;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.character.BossCharacterView;
import edu.unibo.martyadventure.view.character.CharacterView;
import edu.unibo.martyadventure.view.character.CharacterViewFactory;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.ui.WorldBannerFactory;

/**
 * Manage the player movement, map s, collision and enemy spawn
 */
class MovementGameScreen implements Screen {

    private static final int FADE_TIME = 4;
    private static final int SPRITE_SCALE_FACTOR = 3;

    private static MapManager mapManager;
    private static Vector2 playerInitialPosition;

    private final CharacterViewFactory cFactory;
    private final List<EnemyCharacterView> enemyViewList;
    private PlayerCharacterView playerView;
    private BossCharacterView bossView;
    private PlayerInputProcessor inputProcessor;

    private final ScreenManager screenManager;
    private Viewport viewport;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private boolean loadingNewWorld;
    private Sprite worldBanner;
    private Batch uiBatch;
    private float time = 1;

    private boolean disposed;

    public MovementGameScreen(final ScreenManager manager, final CharacterViewFactory characterFactory,
            final Player player, final Maps map) {
        // instantiate factories and managers
        this.screenManager = manager;
        this.uiBatch = new SpriteBatch();
        this.loadingNewWorld = true;
        this.cFactory = characterFactory;
        mapManager = new MapManager();
        WorldBannerFactory bFactory = new WorldBannerFactory();

        // try to load map, if errors close the game
        try {
            mapManager.loadMap(map);
        } catch (InterruptedException | ExecutionException | IOException e1) {
            e1.printStackTrace();
            Gdx.app.exit();
        }

        // camera
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT, ScreenManager.VIEWPORT.Y_VIEWPORT, camera);

        // renderer
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNIT_SCALE);
        mapRenderer.setView(camera);

        // try to load the player, if errors close the game
        playerInitialPosition = mapManager.getPlayerStartPosition();
        try {
            this.playerView = cFactory.createPlayer(player, playerInitialPosition, map);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        // try to load the boss, if errors close the game
        try {
            bossView = cFactory.createBoss(player, mapManager.getBiffStartPosition(), map);
            bossView.setDirection(EntityDirection.DOWN);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }

        // show the current world banner
        worldBanner = new Sprite(bFactory.createBanner(map));
        worldBanner.setSize(Gdx.app.getGraphics().getWidth() / 2, Gdx.app.getGraphics().getHeight() / 4);
        worldBanner.setPosition(Gdx.app.getGraphics().getWidth() / 2 - worldBanner.getWidth() / 2,
                (Gdx.app.getGraphics().getHeight() / 3) * 2);

        this.enemyViewList = getEnemyList(mapManager.getEnemySpawnLayer());

        this.disposed = false;
    }

    /**
     * Setup the enemies of the level
     * 
     * @param enemyLayer
     * @return a list of EnemyCharacterView
     */
    private List<EnemyCharacterView> getEnemyList(final MapLayer enemyLayer) {
        final List<EnemyCharacterView> enemies = new ArrayList<>();

        // Iterate all the map box
        for (MapObject o : enemyLayer.getObjects()) {
            final Rectangle spawnPoint = new Rectangle(((RectangleMapObject) o).getRectangle());
            try {
                enemies.add(cFactory.createEnemy(
                        new Vector2(spawnPoint.x * MapManager.UNIT_SCALE, spawnPoint.y * MapManager.UNIT_SCALE),
                        mapManager.getCurrentMapName()));

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Gdx.app.exit();
            }
        }

        // Set a random direction for each enemy
        final EntityDirection[] directions = EntityDirection.values();
        enemies.forEach(e -> e.setDirection(directions[ThreadLocalRandom.current().nextInt(directions.length)]));

        return enemies;
    }

    /**
     * @return true if the boss is dead
     */
    private boolean cleanDeadEnemies() {
        this.enemyViewList.removeIf(e -> !isAlive(e));
        return !isAlive(bossView);
    }

    /**
     * Battle the living enemy if the player overlaps it's box.
     *
     * @param enemy the enemy the player may battle.
     * @return true if the combat screen was loaded.
     */
    private boolean trySetBattleOverlap(final EnemyCharacterView enemy, final boolean displayGameOver) {
        if (isAlive(enemy) && playerView.getBoundingBox().overlaps(enemy.getBoundingBox())) {
            screenManager.loadCombatScreen(playerView, enemy, displayGameOver);
            return true;
        }
        return false;
    }

    /**
     * Initiate a CombatScreen with the boss or any enemy if the player overlaps
     * with their box.
     */
    private void battleOverlappingEnemies() {
        if (this.enemyViewList.stream().allMatch(e -> !isAlive(e))) {
            trySetBattleOverlap(bossView, mapManager.getCurrentMapName() == Maps.MAP3);
        } else {
            for (EnemyCharacterView enemy : this.enemyViewList) {
                if (trySetBattleOverlap(enemy, false)) {
                    break;
                }
            }
        }
    }

    /**
     * Update the player, camera and input processor positions.
     */
    private void updatePositions(final float delta) {
        // Check collisions
        try {
            if (!collisionWithMapLayer(playerView.getBoundingBox())) {
                playerView.goNextPosition();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update the camera position.
        camera.position.set(playerView.getCurrentPosition().x, playerView.getCurrentPosition().y, 0f);
        camera.update();

        // Update the input processor.
        inputProcessor.update(delta);
    }

    /**
     * Dispatch the screen once the current level has ended.
     */
    private void levelEndDispatch(final Maps currentMap) {
        switch (currentMap) {
        case MAP1:
            playerView.getCharacter()
                    .setHp((int) (PlayerCharacterView.PLAYER_HP * PlayerCharacterView.MAP1_PLAYER_HP_MULTIPLIER));
            screenManager.changeMovementScreen(MapManager.Maps.MAP2);
            screenManager.loadMovementScreen();
            break;
        case MAP2:
            playerView.getCharacter()
                    .setHp((int) (PlayerCharacterView.PLAYER_HP * PlayerCharacterView.MAP2_PLAYER_HP_MULTIPLIER));
            screenManager.changeMovementScreen(MapManager.Maps.MAP3);

            screenManager.loadMovementScreen();
            break;
        default:
            throw new IllegalArgumentException("Unknow map");
        }
    }

    /**
     * Update the game logic status
     */
    private void updateGameLogic(final float delta) {
        updatePositions(delta);

        final boolean isBossDead = cleanDeadEnemies();
        if (isBossDead) {
            levelEndDispatch(mapManager.getCurrentMapName());
        } else {
            battleOverlappingEnemies();
        }
    }

    /**
     * Draw a character view.
     */
    private <C extends Character> void drawCharacter(final CharacterView<C> character, final Batch batch) {
        final Vector2 pos = character.getCurrentPosition();
        batch.draw(character.getCurrentFrame(), pos.x, pos.y, SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
    }

    /**
     * Draw the level's map.
     */
    private void drawMap() {
        mapRenderer.setView(camera);
        mapRenderer.render();

        final Batch batch = mapRenderer.getBatch();
        batch.begin();
        drawCharacter(playerView, batch);

        if (isAlive(bossView)) {
            drawCharacter(bossView, batch);
        }

        enemyViewList.forEach(enemy -> {
            drawCharacter(enemy, batch);
        });
        batch.end();
    }

    /**
     * Draw the game UI.
     */
    private void drawUI(final float delta) {
        uiBatch.begin();
        if (loadingNewWorld) {
            fadeTitle(delta);
        }
        uiBatch.end();
    }

    /**
     * Update the fading, if any.
     */
    private void fadeTitle(float delta) {
        if (time >= 0) {
            worldBanner.draw(uiBatch, time -= delta / FADE_TIME);
        } else {
            loadingNewWorld = false;
        }
    }

    /**
     * Setup the screen elements
     */
    @Override
    public void show() {
        inputProcessor = PlayerInputProcessor.getPlayerInputProcessor();
        inputProcessor.resetState();
        inputProcessor.setPlayer(playerView, true);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    /**
     * Called every frame, update the current screen view
     */
    @Override
    public void render(float delta) {
        updateGameLogic(delta);

        if (!disposed) {
            // Render the screen.
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            drawMap();
            drawUI(delta);
        }
    }

    /**
     * For debug, used to render the bounding box of the player
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
        final MapLayer mapLayer = mapManager.getCollisionLayer();
        if (mapLayer == null) {
            throw new IOException();
        }

        // iterate all the map box
        for (MapObject o : mapLayer.getObjects()) {
            final Rectangle layerBox = ((RectangleMapObject) o).getRectangle();
            final Rectangle scaledLayerBox = new Rectangle(layerBox.x * MapManager.UNIT_SCALE,
                    layerBox.y * MapManager.UNIT_SCALE, layerBox.width * MapManager.UNIT_SCALE,
                    layerBox.height * MapManager.UNIT_SCALE);

            if (box.overlaps(scaledLayerBox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if the given character is alive.
     */
    private <C extends Character> boolean isAlive(final CharacterView<C> character) {
        return character.getCharacter().getHp() > 0;
    }

    /**
     * Resize the view.
     *
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // unused
    }

    @Override
    public void resume() {
        // unused
    }

    @Override
    public void hide() {
        playerInitialPosition = new Vector2(playerView.getCurrentPosition());
    }

    @Override
    public void dispose() {
        if (!this.disposed) {
            this.bossView.dispose();
            this.mapRenderer.dispose();
            Gdx.input.setInputProcessor(null);

            this.disposed = true;
        }
    }
}
