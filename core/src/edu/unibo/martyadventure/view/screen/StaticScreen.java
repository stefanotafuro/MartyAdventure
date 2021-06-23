package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.view.Toolbox;

/**
 * Implements functionalities shared by static screens, managing the background,
 * UI styling resources and the viewport.
 */
abstract class StaticScreen implements Screen {

    private static final String ATLAS_PATH = "skin/comic-ui.atlas";
    private static final String SKIN_PATH = "skin/comic-ui.json";
    private static final String CHOICE_SKIN_PATH = "skin/menuButton.json";

    private final String backgroundPath;

    protected final TextureRegion background;
    protected final TextureAtlas uiAtlas;
    protected final Skin uiSkin;
    protected final Skin choiceSkin;

    protected final Stage stage;
    protected final ScreenManager screenManager;

    static {
        Toolbox.queueAtlas(ATLAS_PATH);
        Toolbox.queueSkin(SKIN_PATH);
        Toolbox.queueSkin(CHOICE_SKIN_PATH);
    }


    private Stage getStage(final int width, final int height) {
        final Viewport viewport = new FitViewport(width, height);
        viewport.apply(true);
        return new Stage(viewport);
    }

    private TextButton getTextButton(final String title, final float x, final float y, final Runnable clickListener,
            Skin skin) {
        final TextButton button = new TextButton(title, skin);
        button.setPosition(x, y);
        button.addListener(new ClickListener() {

            @SuppressWarnings("unused")
            @Override
            public void clicked(InputEvent event, float xDummy, float yDummy) {
                clickListener.run();
            }
        });
        return button;
    }

    /**
     * Instantiates a new static screen.
     *
     * @param manager        the screen manager to operate on.
     * @param backgroundPath the path to the background texture.
     * @param viewportZoom   the zoom level to apply to the viewport.
     */
    protected StaticScreen(final ScreenManager manager, final String backgroundPath, final int viewportZoom) {
        this.backgroundPath = backgroundPath;
        this.background = new TextureRegion(Toolbox.getTexture(backgroundPath));

        this.uiAtlas = Toolbox.getAtlas(ATLAS_PATH);
        this.uiSkin = Toolbox.getSkin(SKIN_PATH);
        this.choiceSkin = Toolbox.getSkin(CHOICE_SKIN_PATH);

        this.stage = getStage(ScreenManager.VIEWPORT.X_VIEWPORT * viewportZoom,
                ScreenManager.VIEWPORT.Y_VIEWPORT * viewportZoom);
        this.screenManager = manager;
    }

    /**
     * @param title         the button's text content.
     * @param position      the button's position.
     * @param clickListener the callback to execute when the button is clicked.
     * @return a new text button with the default skin and given parameters.
     */
    protected TextButton getStandardTextButton(final String title, final Vector2 position,
            final Runnable clickListener) {
        return getStandardTextButton(title, position.x, position.y, clickListener);
    }

    /**
     * @param title         the button's text content.
     * @param x             the button's x-axis position.
     * @param y             the button's y-axis position.
     * @param clickListener the callback to execute when the button is clicked.
     * @return a new text button with the default skin and given parameters.
     */
    protected TextButton getStandardTextButton(final String title, final float x, final float y,
            final Runnable clickListener) {
        return getTextButton(title, x, y, clickListener, this.uiSkin);
    }

    /**
     * @param title         the button's text content.
     * @param x             the button's x-axis position.
     * @param y             the button's y-axis position.
     * @param clickListener the callback to execute when the button is clicked.
     * @return a new text button with the default choice skin and given parameters.
     */
    protected TextButton getChoiceTextButton(final String title, final float x, final float y,
            final Runnable clickListener) {
        return getTextButton(title, x, y, clickListener, this.choiceSkin);
    }

    @Override
    public void pause() {
        // unused
    }

    @Override
    public void resume() {
        // unused
    }

    @Override
    public void hide() {
        // unused
    }

    /**
     * Resize this screen to the given width and height.
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        Toolbox.unloadAsset(this.backgroundPath);
        stage.dispose();
    }
}
