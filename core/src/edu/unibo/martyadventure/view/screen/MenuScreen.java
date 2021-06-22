package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.unibo.martyadventure.controller.sound.MusicController;
import edu.unibo.martyadventure.view.Toolbox;

class MenuScreen extends StaticScreen {

    private static final float DEFAULT_MUSIC_VOLUME = 0.5f;

    private static final int CLOSE_BUTTON_X = 600;
    private static final int CLOSE_BUTTON_Y = 170;
    private static final int ZOOM = 70;

    private static final Vector2 NEW_GAME_BUTTON_POSITION = new Vector2(70, 550);
    private static final Vector2 EXIT_BUTTON_POSITION = new Vector2(120, 350);
    private static final Vector2 OPTIONS_BUTTON_POSITION = new Vector2(110, 450);

    private static final String BG_PATH = "menu/menu.png";
    private static final String WINDOW_BG_PATH = "menu/button.png";
    private static final String BUTTON_BG_PATH = "menu/XclosingButton.png";

    private Window optionWindow;


    public MenuScreen(final ScreenManager manager) {
        super(manager, BG_PATH, ZOOM);
        MusicController.startMusic();
        // Start silently.
        MusicController.setMusicVolume(0.0f);
    }

    @Override
    public void show() {
        stage.addActor(getStandardTextButton("Nuova partita", NEW_GAME_BUTTON_POSITION, () -> screenManager.loadChoiceScreen()));
        stage.addActor(getStandardTextButton("Esci", EXIT_BUTTON_POSITION, () -> Gdx.app.exit()));
        stage.addActor(getStandardTextButton("Opzioni", OPTIONS_BUTTON_POSITION, () -> optionWindow.setVisible(true)));

        optionWindow = new Window("", super.uiSkin);
        optionWindow.setSize(stage.getWidth() / 2, stage.getHeight() / 4);
        optionWindow.setPosition(stage.getWidth() / 4, stage.getHeight() / 3);
        optionWindow.setBackground(new TextureRegionDrawable(Toolbox.getTexture(WINDOW_BG_PATH)));
        optionWindow.setVisible(false);
        stage.addActor(optionWindow);

        Label titleLabel = new Label("Volume musica", super.uiSkin, "title");
        Slider volumeSlider = new Slider(0, 1, 0.1f, false, super.uiSkin);
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

        // Set the music volume to the current slider value.
        volumeSlider.setValue(DEFAULT_MUSIC_VOLUME);
        MusicController.setMusicVolume(volumeSlider.getValue());
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
}
