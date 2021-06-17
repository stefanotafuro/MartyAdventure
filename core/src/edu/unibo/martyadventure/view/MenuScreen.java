package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
    
    Music music;
    private static final int ZOOM = 70;
    private Stage stage;
    private Viewport viewport;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private static final String BG_PATH = "Level/Menu/Menu.png";
    TextButton martyButton;
    TextButton docButton;
    TextButton biffButton;

    public MenuScreen() {
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        buttonSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"), buttonAtlas);
        viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);
        viewport.apply();
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        music = Gdx.audio.newMusic(new FileHandle("music/theme.ogg"));
        music.setLooping(true);
        music.play();
        TextButton newGameButton = new TextButton("Nuova partita", buttonSkin);
        TextButton exitButton = new TextButton("Esci", buttonSkin);
        stage.addActor(newGameButton);
        stage.addActor(exitButton);
        newGameButton.setPosition(70, 550);
        exitButton.setPosition(120, 450);


        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.loadChoicecreen();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                music.dispose();
            }
        });
        
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().end();
        stage.draw();

    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        // TODO Auto-generated method stub

    }

}
