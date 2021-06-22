package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.ui.Label;


class GameOverScreen extends StaticScreen {

    private static final int ZOOM = 70;

    private static final Vector2 TITLE_POSITION = new Vector2(100, 650);
    private static final Vector2 MENU_BUTTON_POSITION = new Vector2(30, 40);

    private static final String WON_TEXT = "Hai vinto!";
    private static final String LOSE_TEXT = "Hai perso, vuoi riprovare?";

    private static final String BG_PATH = "menu/gameover.png";

    private final boolean playerWon;


    public GameOverScreen(final ScreenManager manager, final boolean playerWon) {
        super(manager, BG_PATH, ZOOM);
        this.playerWon = playerWon;
    }

    @Override
    public void show() {
        final Label titleLabel = new Label(this.playerWon ? WON_TEXT : LOSE_TEXT, super.uiSkin, "title");
        titleLabel.setPosition(TITLE_POSITION.x, TITLE_POSITION.y);
        stage.addActor(titleLabel);

        stage.addActor(getStandardTextButton("Ritorna al menu", MENU_BUTTON_POSITION, () -> {
            screenManager.cleanMovementScreen();
            screenManager.loadMenuScreen();
        }));

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
