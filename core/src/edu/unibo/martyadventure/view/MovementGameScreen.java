package edu.unibo.martyadventure.view;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import edu.unibo.martyadventure.controller.entity.PlayerInputProcessor;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;

public class MovementGameScreen implements Screen {
    
    private PlayerCharacterView player;
    private PlayerInputProcessor inputProcessor;
    private TextureRegion currentFrame;
    private Sprite currentSprite;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private static MapManager mapManager;
    
    public MovementGameScreen(){
        mapManager = new MapManager();
    }

    @Override
    public void show() {
        //camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 100 , 100);
        
        //rederer
        try {
            mapManager.loadMap(MapManager.Maps.MAP1);
        } catch (InterruptedException | ExecutionException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        mapRenderer = new OrthogonalTiledMapRenderer(mapManager.getCurrentMap(), MapManager.UNIT_SCALE);
        mapRenderer.setView(camera);
        
        System.err.println("Unit scale:" + MapManager.UNIT_SCALE);
        
        //player
        try {
            player = new PlayerCharacterView(mapManager.getPlayerStartPosition());
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        currentSprite = player.getSprite();
        inputProcessor = PlayerInputProcessor.getPlayerInputProcessor();
        inputProcessor.setPlayer(player);
        Gdx.input.setInputProcessor(inputProcessor);
        

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.position.set(currentSprite.getX(),currentSprite.getY(),0f);
        camera.update();
        currentFrame = player.getCurrentFrame();
        player.goNextPosition();
        inputProcessor.update(delta);
        
        mapRenderer.setView(camera);
        mapRenderer.render();
        mapRenderer.getBatch().begin();
        mapRenderer.getBatch().draw(currentFrame, currentSprite.getX(), currentSprite.getY(), 3, 4);
        mapRenderer.getBatch().end();
        
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
        // TODO player disposed
        mapRenderer.dispose();
        Gdx.input.setInputProcessor(null);
        

    }

}
