package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class GameOverScreen extends StaticScreen {

    private static final String BACKGROUND_PATH = "menu/gameover.png";
    private static final String WON_TEXT = "Hai vinto!";
    private static final String LOST_TEXT = "Hai perso, vuoi riprovare?";

    private final Label textLabel;
    private final TextButton menuButton;


    public GameOverScreen(final ScreenManager manager, final boolean playerWon, final int height, final int width) {
        super(manager, BACKGROUND_PATH, height, width);
        this.textLabel = new Label(playerWon ? WON_TEXT : LOST_TEXT, uiSkin);
        this.menuButton = new TextButton("Ritornare al menu?", uiSkin);

        super.stage.addActor(this.textLabel);
        super.stage.addActor(this.menuButton);
    }

    /**
     * Set the callback for when a request to go back to the main menu is made.
     *
     * @param listener the callback object.
     */
    public void addMenuListener(final EventListener listener) {
        this.menuButton.addListener(listener);
    }

    @Override
    public void show() {
        resize(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(this.background, 0.0f, 0.0f);
        stage.getBatch().end();
        stage.draw();
    }
}
