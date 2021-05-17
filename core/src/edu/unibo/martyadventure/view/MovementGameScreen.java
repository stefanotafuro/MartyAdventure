package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.unibo.martyadventure.controller.entity.PlayerInputProcessor;

public class MovementGameScreen implements Screen {
    
    private PlayerCharacterView player;
    private PlayerInputProcessor inputProcessor;
    private TextureRegion currentFrame;
    private Sprite currentSprite;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private static MapManager mapManager;
    
    public MovementGameScreen() {
        mapManager = new MapManager();
    }

    @Override
    public void show() {
        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10 , 10);
        
        //rederer
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNIT_SCALE);
        mapRenderer.setView(camera);
        
        System.err.println("Unit scale:" + MapManager.UNIT_SCALE);
        
        //player
        
        

    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

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
        // TODO player dispose
        mapRenderer.dispose();
        Gdx.input.setInputProcessor(null);
        

    }

}
