package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.Player;

public class PlayerChoiceScreen implements Screen {
    
    private static final String DOC_PATH = "Characters/Doc/DocSelection.png";
    private static final String MARTY_PATH = "Characters/Marty/MartySelection.png";
    private static final String BIFF_PATH = "Characters/Biff/BiffSelection.png";
    private static final int BIFF_BUTTON_X = 975;
    private static final int DOC_BUTTON_X = 270;
    private static final int MARTY_BUTTON_X = 620;
    private static final int START_BUTTON_X = 570;
    private static final int START_BUTTON_Y = 150;
    private static final int BUTTON_Y = 350;
    private static final int SPRITE_HEIGHT = 101;
    private static final int SPRITE_WIDTH = 74;
    private static final int SPRITE_SCALE = 2;
    private static final int MARTY_X = 625;
    private static final int DOC_X = 275;
    private static final int SPRITE_Y = 450;
    private static final int BIFF_X = 975;
    private static final int ZOOM = 70;
    private Stage stage;
    private Viewport viewport;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private static final String BG_PATH = "Level/Menu/SelectCharacters.png";
    private static final float FRAME_DURATION = 0.25f;
    private float time=0;
    Animation<TextureRegion> biffAnimation;
    Animation<TextureRegion> martyAnimation;
    Animation<TextureRegion> docAnimation;
    
    public PlayerChoiceScreen() {
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        buttonSkin = new Skin(Gdx.files.internal("skin/menuButton.json"), buttonAtlas);
        viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);
        viewport.apply();
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        TextButton newGameButton = new TextButton("Inizia partita", buttonSkin);
        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<>();
        TextButton martyButton = new TextButton("Marty", buttonSkin);
        TextButton docButton = new TextButton("Doc", buttonSkin);
        TextButton biffButton = new TextButton("Biff", buttonSkin);
       
        buttonGroup.add(martyButton);
        buttonGroup.add(docButton);
        buttonGroup.add(biffButton);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setUncheckLast(true);
        
        martyButton.setChecked(true);
        ScreenManager.changePlayer(Player.MARTY);
        
        martyButton.setPosition(MARTY_BUTTON_X, BUTTON_Y);
        docButton.setPosition(DOC_BUTTON_X, BUTTON_Y);
        biffButton.setPosition(BIFF_BUTTON_X, BUTTON_Y);
        newGameButton.setPosition(START_BUTTON_X, START_BUTTON_Y);
        
        stage.addActor(newGameButton);
        stage.addActor(martyButton);
        stage.addActor(docButton);
        stage.addActor(biffButton);
        
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.changeMap(Maps.MAP1);
                ScreenManager.loadMovementScreen();
            }
        });
        
        martyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.changePlayer(Player.MARTY);
            }
        });
        
        docButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.changePlayer(Player.DOC);
            }
        });
        
        biffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.changePlayer(Player.BIFF);
            }
        });
        biffAnimation = loadAnimation(BIFF_PATH);
        martyAnimation  = loadAnimation(MARTY_PATH);
        docAnimation = loadAnimation(DOC_PATH);

        Gdx.input.setInputProcessor(stage);

    }

    private Animation<TextureRegion> loadAnimation(String path) {
        TextureRegion[][] textures = new TextureRegion(Toolbox.getTexture(path)).split(SPRITE_WIDTH, SPRITE_HEIGHT);
        Animation<TextureRegion> a = new Animation<TextureRegion>(FRAME_DURATION, textures[0]);
        a.setPlayMode(PlayMode.LOOP);
        return a;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        time += delta;
        time = time % 10;
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().draw(biffAnimation.getKeyFrame(time), BIFF_X, SPRITE_Y, SPRITE_WIDTH *  SPRITE_SCALE ,SPRITE_HEIGHT * SPRITE_SCALE);
        stage.getBatch().draw(docAnimation.getKeyFrame(time), DOC_X, SPRITE_Y, SPRITE_WIDTH *  SPRITE_SCALE ,SPRITE_HEIGHT * SPRITE_SCALE);
        stage.getBatch().draw(martyAnimation.getKeyFrame(time), MARTY_X, SPRITE_Y, SPRITE_WIDTH *  SPRITE_SCALE ,SPRITE_HEIGHT * SPRITE_SCALE);
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
