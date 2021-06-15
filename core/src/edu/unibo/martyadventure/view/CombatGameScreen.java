package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public class CombatGameScreen implements Screen {

    private static final int BASE_HEIGHT = 1080;
    private static final int BASE_WIDTH = 1920;
    private static final int TABLE_SCALE = 1;
    private static final int TABLE_POSITION_Y = 135;
    private static final int ROW_SPACE = 15;
    private static final int BUTTON_SPACE = 220;
    private static final Vector2 PLAYER_POSITION = new Vector2(220, 320);
    private static final Vector2 ENEMY_POSITION = new Vector2(1360, 780);
    private static final int SPRITE_DIMENSION = 300;
    private static final String BG_PATH = "Level/Fight/fight_map1.png";

    private Sprite playerSprite;
    private Sprite enemySprite;
    private Fight fight;
    private Stage stage;
    private Viewport viewport;
    private Skin buttonSkin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private Label playerHpLabel;
    private Label enemyHpLabel;
    private TextButton moveButton1;
    private TextButton moveButton2;
    private TextButton moveButton3;
    private TextButton moveButton4;

    public CombatGameScreen(PlayerCharacterView player, EnemyCharacterView enemy) {
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        buttonSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"), buttonAtlas);
        setupPlayer(player);
        setupEnemy(enemy);
        viewport = new FitViewport(BASE_WIDTH, BASE_HEIGHT);
        viewport.apply();
        fight = new Fight(player.getPlayer(), enemy.getEnemy());
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create Table
        Table mainTable = new Table();
        mainTable.setTransform(true);
        mainTable.setPosition(stage.getWidth() / 2, TABLE_POSITION_Y);
        mainTable.scaleBy(TABLE_SCALE);
        mainTable.center();

        // Create buttons
        moveButton1 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(0).getName(), buttonSkin);
        moveButton2 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(1).getName(), buttonSkin);
        moveButton3 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(2).getName(), buttonSkin);
        moveButton4 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(3).getName(), buttonSkin);

        // Add listeners to buttons
        moveButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fight.playerAttack(fight.getPlayer().getWeapon().getMoveList().get(0));
            }
        });

        moveButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fight.playerAttack(fight.getPlayer().getWeapon().getMoveList().get(1));
            }
        });

        moveButton3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fight.playerAttack(fight.getPlayer().getWeapon().getMoveList().get(2));
            }
        });

        moveButton4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fight.playerAttack(fight.getPlayer().getWeapon().getMoveList().get(3));
            }
        });

        // Add buttons to table
        mainTable.row();
        mainTable.add(moveButton1).spaceRight(BUTTON_SPACE);
        mainTable.add(moveButton2);

        mainTable.row().spaceTop(ROW_SPACE);
        mainTable.add(moveButton3).spaceRight(BUTTON_SPACE);
        mainTable.add(moveButton4);

        // Add table to stage

        stage.addActor(mainTable);

        // Create label

        playerHpLabel = new Label(String.valueOf(fight.getPlayer().getHp()), buttonSkin);
        playerHpLabel.setFontScale(5);
        playerHpLabel.setSize(100, 100);
        playerHpLabel.setPosition(300, 300);

        enemyHpLabel = new Label(String.valueOf(fight.getEnemy().getHp()), buttonSkin);
        enemyHpLabel.setFontScale(5);
        enemyHpLabel.setSize(100, 100);
        enemyHpLabel.setPosition(1300, 700);

        stage.addActor(playerHpLabel);
        stage.addActor(enemyHpLabel);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        enemyHpLabel.setText(fight.getEnemy().getHp());
        playerHpLabel.setText(fight.getPlayer().getHp());
        checkButton(moveButton1, 0);
        checkButton(moveButton2, 1);
        checkButton(moveButton3, 2);
        checkButton(moveButton4, 3);

        if (fight.fightWinner() != null) {
            if (fight.fightWinner() == fight.getPlayer()) {
                fight.getPlayer().setWeapon(fight.getEnemy().getDropitem());
                ScreenManager.loadMovementScreen();
            } else {
                System.err.println("GAME OVER");
                Gdx.app.exit();
            }
        }
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().draw(playerSprite, PLAYER_POSITION.x, PLAYER_POSITION.y, SPRITE_DIMENSION, SPRITE_DIMENSION);
        stage.getBatch().draw(enemySprite, ENEMY_POSITION.x, ENEMY_POSITION.y, SPRITE_DIMENSION, SPRITE_DIMENSION);
        stage.getBatch().end();
        stage.draw();

    }

    private void checkButton(TextButton button, int moveNumber) {
        if (fight.getPlayer().getWeapon().getMoveList().get(moveNumber).isUsable(fight.getTurnCount())) {
            button.setTouchable(Touchable.enabled);
            button.setDisabled(false);
        } else {
            button.setTouchable(Touchable.disabled);
            button.setDisabled(true);
        }

    }

    private void setupPlayer(PlayerCharacterView p) {
        p.setState(EntityState.IDLE);
        p.setDirection(EntityDirection.UP);
        this.playerSprite = new Sprite(p.getCurrentFrame());
    }

    private void setupEnemy(EnemyCharacterView e) {
        e.setDirection(EntityDirection.DOWN);
        this.enemySprite = new Sprite(e.getCurrentFrame());
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
        stage.dispose();

    }

}
