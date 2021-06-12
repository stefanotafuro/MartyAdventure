package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.entity.EntityDirection;

public class CombatGameScreen implements Screen {

    private Sprite player;
    private Sprite enemy;
    private Fight fight;
    private SpriteBatch batch;
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private TextureAtlas atlas;

    public CombatGameScreen(PlayerCharacterView player, EnemyCharacterView enemy) {
        atlas = new TextureAtlas("skin/comic-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
        batch = new SpriteBatch();
        setupPlayer(player);
        setupEnemy(enemy);
        viewport = new FitViewport(ScreenManager.VIEWPORT.physicalWidth, ScreenManager.VIEWPORT.physicalHeight);
        viewport.apply();
        fight = new Fight(player.getPlayer(), enemy.getEnemy());
        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create Table
        Table mainTable = new Table();
        // Set table to fill stage
        mainTable.setFillParent(true);
        // Set alignment of contents in the table.
        mainTable.top();

        // Create buttons
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Add listeners to buttons
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(exitButton);

        // Add table to stage
        stage.addActor(mainTable);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    private void setupPlayer(PlayerCharacterView p) {
        p.setDirection(EntityDirection.UP);
        this.player = new Sprite(p.getCurrentFrame());
    }

    private void setupEnemy(EnemyCharacterView e) {
        e.setDirection(EntityDirection.DOWN);
        this.enemy = new Sprite(e.getCurrentFrame());
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
