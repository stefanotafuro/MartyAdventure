package edu.unibo.martyadventure.view;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.controller.entity.PlayerInputProcessor;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public class MovementGameScreen implements Screen {

    private static final int SPRITE_SCALE_FACTOR = 3;
    private PlayerCharacterView player;
    private EnemyCharacterView biff;
    private PlayerInputProcessor inputProcessor;
    private TextureRegion playerCurrentFrame;
    private TextureRegion biffCurrentFrame;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private static MapManager mapManager;
    private static Vector2 playerInitialPosition;

    public MovementGameScreen(MapManager.Maps map) {
        mapManager = new MapManager();
        try {
            mapManager.loadMap(MapManager.Maps.MAP1);
        } catch (InterruptedException | ExecutionException | IOException e1) {
            e1.printStackTrace();
        }
        playerInitialPosition = new Vector2(mapManager.getPlayerStartPosition());
        
     // camera
        ScreenManager.setupViewport(ScreenManager.VIEWPORT.ZOOM, ScreenManager.VIEWPORT.ZOOM);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, ScreenManager.VIEWPORT.viewportWidth, ScreenManager.VIEWPORT.viewportHeight);

        // rederer
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNIT_SCALE);
        mapRenderer.setView(camera);

        // player
        try {
            player = new PlayerCharacterView(playerInitialPosition);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // biff
        try {
            biff = new EnemyCharacterView(mapManager.getBiffStartPosition());
            biff.setDirection(EntityDirection.DOWN);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup the screen elements
     */
    @Override
    public void show() {
        
        resize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        inputProcessor = PlayerInputProcessor.getPlayerInputProcessor();
        inputProcessor.setPlayer(player, true);
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
        camera.position.set(player.getCurrentPosition().x, player.getCurrentPosition().y, 0f);
        camera.update();
        // update the current player frame
        playerCurrentFrame = player.getCurrentFrame();
        biffCurrentFrame = biff.getCurrentFrame();

        // check collisions
        try {
            if (!collisionWithMapLayer(player.getBoundingBox())) {
                player.goNextPosition();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // check collisions
        if (isAlive(biff)) {
            if (player.getBoundingBox().overlaps(biff.getBoundingBox())) {
                ScreenManager.loadCombatScreen(new CombatGameScreen(player, biff));
            }
        }

        // update the input processor
        inputProcessor.update(delta);

        // render the screen
        mapRenderer.setView(camera);
        mapRenderer.render();
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(playerCurrentFrame, player.getCurrentPosition().x, player.getCurrentPosition().y,
                SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
        if (isAlive(biff)) {
            mapRenderer.getBatch().draw(biffCurrentFrame, biff.getCurrentPosition().x, biff.getCurrentPosition().y,
                    SPRITE_SCALE_FACTOR, SPRITE_SCALE_FACTOR);
        }
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
        return enemy.getEnemy().getHp() != 0;
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

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        playerInitialPosition = new Vector2(player.getCurrentPosition());
    }

    @Override
    public void dispose() {
        // TODO player disposed
        mapRenderer.dispose();
        Gdx.input.setInputProcessor(null);

    }

}
