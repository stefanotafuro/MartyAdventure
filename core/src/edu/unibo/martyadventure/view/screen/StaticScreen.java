package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.view.Toolbox;

abstract class StaticScreen implements Screen {

    private static final String ATLAS_PATH = "skin/comic-ui.atlas";
    private static final String SKIN_PATH = "skin/comic-ui.json";

    private final String backgroundPath;

    protected final TextureRegion background;
    protected final TextureAtlas uiAtlas;
    protected final Skin uiSkin;
    protected final Stage stage;

    static {
        Toolbox.queueAtlas(ATLAS_PATH);
        Toolbox.queueSkin(SKIN_PATH);
    }


    private Stage getStage(final int width, final int height) {
        final Viewport viewport = new FitViewport(width, height);
        viewport.apply();
        return new Stage(viewport);
    }

    protected StaticScreen(final String backgroundPath, final int width, final int height) {
        this.backgroundPath = backgroundPath;
        this.background = new TextureRegion(Toolbox.getTexture(backgroundPath));

        this.uiAtlas = Toolbox.getAtlas(ATLAS_PATH);
        this.uiSkin = Toolbox.getSkin(SKIN_PATH);

        this.stage = getStage(width, height);
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

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        Toolbox.unloadAsset(this.backgroundPath);
        Toolbox.unloadAsset(ATLAS_PATH);
        Toolbox.unloadAsset(SKIN_PATH);
        stage.dispose();
    }
}
