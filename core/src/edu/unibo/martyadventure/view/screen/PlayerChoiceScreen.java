package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.unibo.martyadventure.view.Toolbox;
import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.Player;

class PlayerChoiceScreen extends StaticScreen {

    private static final String BUTTON_BG_PATH = "menu/XclosingButton.png";

    private static final String DOC_PATH = "Characters/Doc/DocSelection.png";
    private static final String MARTY_PATH = "Characters/Marty/MartySelection.png";
    private static final String BIFF_PATH = "Characters/Biff/BiffSelection.png";

    private static final int BIFF_BUTTON_X = 975;
    private static final int DOC_BUTTON_X = 270;
    private static final int MARTY_BUTTON_X = 620;
    private static final int PLAYER_BUTTON_Y = 350;

    private static final Vector2 START_BUTTON_POSITION = new Vector2(570, 150);

    private static final int SPRITE_HEIGHT = 101;
    private static final int SPRITE_WIDTH = 74;
    private static final int SPRITE_SCALE = 2;

    private static final int MARTY_X = 625;
    private static final int DOC_X = 275;
    private static final int SPRITE_Y = 450;
    private static final int BIFF_X = 975;
    private static final int ZOOM = 70;

    private static final String BG_PATH = "menu/SelectCharacters.png";
    private static final float FRAME_DURATION = 0.25f;
    private static final float CLOSE_BUTTON_X = 200;
    private static final float CLOSE_BUTTON_Y = 200;

    private float time = 0;

    private Animation<TextureRegion> biffAnimation;
    private Animation<TextureRegion> martyAnimation;
    private Animation<TextureRegion> docAnimation;


    private TextButton getSetPlayerButton(final String name, final int xPosition, final Player player) {
        return getChoiceTextButton(name, xPosition, PLAYER_BUTTON_Y, () -> screenManager.changePlayer(player));
    }

    private Animation<TextureRegion> loadAnimation(String path) {
        TextureRegion[][] textures = new TextureRegion(Toolbox.getTexture(path)).split(SPRITE_WIDTH, SPRITE_HEIGHT);
        Animation<TextureRegion> a = new Animation<TextureRegion>(FRAME_DURATION, textures[0]);
        a.setPlayMode(PlayMode.LOOP);
        return a;
    }

    private void drawCharacterAnimation(final Animation<TextureRegion> animation, final int xPosition,
            final Batch batch) {
        batch.draw(animation.getKeyFrame(this.time), xPosition, SPRITE_Y, SPRITE_WIDTH * SPRITE_SCALE,
                SPRITE_HEIGHT * SPRITE_SCALE);
    }

    public PlayerChoiceScreen(final ScreenManager manager) {
        super(manager, BG_PATH, ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);
        Toolbox.queueTexture(BUTTON_BG_PATH);
    }

    @Override
    public void show() {
        ImageButton closeButton = new ImageButton(new TextureRegionDrawable(Toolbox.getTexture(BUTTON_BG_PATH)));
        TextButton newGameButton = getStandardTextButton("Inizia partita", START_BUTTON_POSITION, () -> {
            screenManager.changeMap(Maps.MAP1);
            screenManager.loadMovementScreen();
        });

        ButtonGroup<TextButton> buttonGroup = new ButtonGroup<>();
        TextButton martyButton = getSetPlayerButton("Marty", MARTY_BUTTON_X, Player.MARTY);
        TextButton docButton = getSetPlayerButton("Doc", DOC_BUTTON_X, Player.DOC);
        TextButton biffButton = getSetPlayerButton("Biff", BIFF_BUTTON_X, Player.BIFF);

        buttonGroup.add(martyButton);
        buttonGroup.add(docButton);
        buttonGroup.add(biffButton);
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(1);
        buttonGroup.setUncheckLast(true);

        martyButton.setChecked(true);
        screenManager.changePlayer(Player.MARTY);

        closeButton.setPosition(stage.getWidth() - CLOSE_BUTTON_X, stage.getHeight() - CLOSE_BUTTON_Y);
        closeButton.setTransform(true);
        closeButton.scaleBy(2);

        stage.addActor(newGameButton);
        stage.addActor(martyButton);
        stage.addActor(docButton);
        stage.addActor(biffButton);
        stage.addActor(closeButton);

        closeButton.addListener(new ClickListener() {

            @SuppressWarnings("unused")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.loadMenuScreen();
            }
        });
        biffAnimation = loadAnimation(BIFF_PATH);
        martyAnimation = loadAnimation(MARTY_PATH);
        docAnimation = loadAnimation(DOC_PATH);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        time += delta;
        time = time % 10;

        stage.act();

        final Batch batch = stage.getBatch();
        batch.begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        drawCharacterAnimation(biffAnimation, BIFF_X, batch);
        drawCharacterAnimation(docAnimation, DOC_X, batch);
        drawCharacterAnimation(martyAnimation, MARTY_X, batch);
        batch.end();

        stage.draw();
    }

    @Override
    public void dispose() {
        Toolbox.unloadAsset(BUTTON_BG_PATH);
        super.dispose();
    }
}
