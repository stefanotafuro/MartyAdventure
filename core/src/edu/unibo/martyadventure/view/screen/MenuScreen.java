package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.controller.sound.MusicController;

public class MenuScreen implements Screen {

    private static final int CLOSE_BUTTON_X = 600;
    private static final int CLOSE_BUTTON_Y = 170;
    private static final int ZOOM = 70;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private static final String BG_PATH = "Level/Menu/Menu.png";
    private static final String WINDOW_BG_PATH = "Level/Menu/button.png";
    private static final String BUTTON_BG_PATH = "Level/Menu/XclosingButton.png";
    private Window optionWindow;

    public MenuScreen() {

        MusicController.startMusic();
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"), buttonAtlas);
        viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);
        viewport.apply();
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        TextButton newGameButton = new TextButton("Nuova partita", skin);
        TextButton exitButton = new TextButton("Esci", skin);
        TextButton optionButton = new TextButton("Opzioni", skin);
        stage.addActor(newGameButton);
        stage.addActor(exitButton);
        stage.addActor(optionButton);
        newGameButton.setPosition(70, 550);
        exitButton.setPosition(120, 350);
        optionButton.setPosition(110, 450);

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
            }
        });

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionWindow.setVisible(true);
            }
        });

        optionWindow = new Window("", skin);
        optionWindow.setSize(stage.getWidth() / 2, stage.getHeight() / 4);
        optionWindow.setPosition(stage.getWidth() / 4, stage.getHeight() / 3);
        optionWindow.setBackground(new TextureRegionDrawable(Toolbox.getTexture(WINDOW_BG_PATH)));
        optionWindow.setVisible(false);
        stage.addActor(optionWindow);

        Label titleLabel = new Label("Volume musica", skin, "title");
        Slider volumeSlider = new Slider(0, 1, 0.1f, false, skin);
        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(Toolbox.getTexture(BUTTON_BG_PATH)));
        closeButton.setPosition(CLOSE_BUTTON_X, CLOSE_BUTTON_Y);
        closeButton.setTransform(true);
        closeButton.scaleBy(1.2f);

        optionWindow.add(titleLabel);
        optionWindow.row();
        optionWindow.add(volumeSlider);
        optionWindow.addActor(closeButton);
        volumeSlider.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MusicController.setMusicVolume(volumeSlider.getValue());

            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                optionWindow.setVisible(false);
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
