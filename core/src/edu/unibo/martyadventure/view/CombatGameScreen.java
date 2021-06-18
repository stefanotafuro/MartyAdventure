package edu.unibo.martyadventure.view;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.view.entity.EntityDirection;
import edu.unibo.martyadventure.view.entity.EntityState;

public class CombatGameScreen implements Screen {

    private static final float WEAPON_TEXTURE_SCALE = 2;
    private static final int ZOOM = 100;
    private static final int TABLE_POSITION_Y = 190;
    private static final int Y_TABLE_SPACE = 100;
    private static final int X_TABLE_SPACE = 330;
    private static final Vector2 PLAYER_POSITION = new Vector2(220, 470);
    private static final Vector2 ENEMY_POSITION = new Vector2(1360, 1100);
    private static final int SPRITE_DIMENSION = 300;
    private static final String BG_PATH = "Level/Fight/fight_map1.png";
    private static final float BUTTON_SPACE = 30;
    private static final int PLAYER_WEAPON_X = 250;
    private static final float PLAYER_WEAPON_Y = 1050;
    private static final int ENEMY_WEAPON_X = 1150;
    private static final float ENEMY_WEAPON_Y = 750;
    private static final int ENEMY_LABEL_X = 1400;
    private static final int PLAYER_LABEL_X = 150;
    private static final String weaponSelectionPath = "Level/Fight/WeaponSelection.png";

    private Sprite playerSprite;
    private Sprite enemySprite;
    private Texture playerWeaponTexture;
    private Texture enemyWeaponTexture;
    private Fight fight;
    private Stage stage;
    private Window weaponSelection;
    private Viewport viewport;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private Label playerHpLabel;
    private Label enemyHpLabel;
    private TextButton moveButton1;
    private TextButton moveButton2;
    private TextButton moveButton3;
    private TextButton moveButton4;
    private PlayerCharacterView playerView;
    private EnemyCharacterView enemyView;

    public CombatGameScreen(PlayerCharacterView player, EnemyCharacterView enemy) {
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        skin = new Skin(Gdx.files.internal("skin/comic-ui.json"), buttonAtlas);
        setupPlayer(player);
        setupEnemy(enemy);
        viewport = new FitViewport(ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);
        viewport.apply();
        fight = new Fight(player.getPlayer(), enemy.getEnemy());
        stage = new Stage(viewport);
        playerWeaponTexture = player.getWeaponView().getWeaponTexture();
        enemyWeaponTexture = enemy.getWeaponView().getWeaponTexture();
        playerView = player;
        enemyView = enemy;
    }

    @Override
    public void show() {
        // Setup move labels
        Label move1Label = new Label("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(0).getReloadTime()
                + " Dmg: " + fight.getPlayer().getWeapon().getMoveList().get(0).getDamage(), skin, "title");

        Label move2Label = new Label("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(1).getReloadTime()
                + " Dmg: " + fight.getPlayer().getWeapon().getMoveList().get(1).getDamage(), skin, "title");

        Label move3Label = new Label("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(2).getReloadTime()
                + " Dmg: " + fight.getPlayer().getWeapon().getMoveList().get(2).getDamage(), skin, "title");

        Label move4Label = new Label("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(3).getReloadTime()
                + " Dmg: " + fight.getPlayer().getWeapon().getMoveList().get(3).getDamage(), skin, "title");

        // Setup info labels
        Label playerWeaponLabel;
        Label enemyWeaponLabel;
        DecimalFormat df = new DecimalFormat("###.#");

        playerWeaponLabel = new Label("Arma: " + fight.getPlayer().getWeapon().getName() + " \nDanno: "
                + df.format(fight.getPlayer().getWeapon().getDamageMultiplier()), skin, "title");
        playerWeaponLabel.setSize(100, 100);
        playerWeaponLabel.setPosition(PLAYER_LABEL_X, 900);

        enemyWeaponLabel = new Label("Arma: " + fight.getEnemy().getWeapon().getName() + " \nDanno: "
                + df.format(fight.getEnemy().getWeapon().getDamageMultiplier()), skin, "title");
        enemyWeaponLabel.setSize(100, 100);
        enemyWeaponLabel.setPosition(ENEMY_LABEL_X, 700);

        playerHpLabel = new Label("", skin, "title");
        playerHpLabel.setSize(100, 100);
        playerHpLabel.setPosition(PLAYER_LABEL_X, 800);

        enemyHpLabel = new Label("", skin, "title");
        enemyHpLabel.setSize(100, 100);
        enemyHpLabel.setPosition(ENEMY_LABEL_X, 800);

        // add lables to the stage
        stage.addActor(playerHpLabel);
        stage.addActor(enemyHpLabel);
        stage.addActor(enemyWeaponLabel);
        stage.addActor(playerWeaponLabel);

        // Create Table
        Table mainTable = new Table();
        mainTable.setTransform(true);
        mainTable.setPosition(stage.getWidth() / 2, TABLE_POSITION_Y);

        // Create buttons
        moveButton1 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(0).getName(), skin);
        moveButton2 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(1).getName(), skin);
        moveButton3 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(2).getName(), skin);
        moveButton4 = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(3).getName(), skin);

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
        mainTable.add(moveButton1).align(Align.center).spaceRight(BUTTON_SPACE);
        mainTable.add(move1Label).align(Align.right).spaceRight(X_TABLE_SPACE);
        mainTable.add(moveButton2).align(Align.center).spaceRight(BUTTON_SPACE);
        mainTable.add(move2Label).align(Align.right);

        mainTable.row().spaceTop(Y_TABLE_SPACE);
        mainTable.add(moveButton3).align(Align.center).spaceRight(BUTTON_SPACE);
        mainTable.add(move3Label).align(Align.right).spaceRight(X_TABLE_SPACE);
        mainTable.add(moveButton4).align(Align.center).spaceRight(BUTTON_SPACE);
        mainTable.add(move4Label).align(Align.right);

        // Add table to stage

        stage.addActor(mainTable);

        weaponSelection = new Window("", skin);
        weaponSelection.setBackground(new TextureRegionDrawable(Toolbox.getTexture(weaponSelectionPath)));
        weaponSelection.setSize(stage.getWidth() / 2, stage.getHeight() / 4);
        weaponSelection.setPosition(stage.getWidth() / 4, stage.getHeight() / 3);
        weaponSelection.setVisible(false);

        TextButton weapon1Button = new TextButton(fight.getPlayer().getWeapon().getName(), skin);
        TextButton weapon2Button = new TextButton(fight.getEnemy().getDropitem().getName(), skin);
        weapon1Button.setPosition(weaponSelection.getWidth()/4 - weapon1Button.getWidth()/2, 35);
        weapon2Button.setPosition((weaponSelection.getWidth()/4)*3 - weapon2Button.getWidth()/2, 35);
        
        Image weapon1Image = new Image(playerWeaponTexture);
        Image weapon2Image = new Image(enemyView.getDropWeapon().getWeaponTexture());
        
        Label playerDropLabel = new Label("La tua arma " + ":\n" + "Danno: " + df.format(fight.getPlayer().getWeapon().getDamageMultiplier()) , skin, "title");
        Label enemyDropLabel = new Label("Drop di " + fight.getEnemy().getName() + ":\n" + "Danno: " + df.format(fight.getEnemy().getDropitem().getDamageMultiplier()) , skin, "title");

        weaponSelection.addActor(weapon1Button);
        weaponSelection.addActor(weapon2Button);
        weaponSelection.add(weapon1Image).spaceRight(350).center();
        weaponSelection.add(weapon2Image);
        weaponSelection.row();
        weaponSelection.add(playerDropLabel).left();
        weaponSelection.add(enemyDropLabel);
        
        weapon1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.loadMovementScreen();
            }
        });
        
        weapon2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerView.setWeaponView(enemyView.getDropWeapon());
                ScreenManager.loadMovementScreen();
            }
        });

        stage.addActor(weaponSelection);

        // Setup the input processor
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateLabel();
        checkButton(moveButton1, 0);
        checkButton(moveButton2, 1);
        checkButton(moveButton3, 2);
        checkButton(moveButton4, 3);

        if (fight.fightWinner() != null) {
            if (fight.fightWinner() == fight.getPlayer()) {
                weaponSelection();
            } else {
                // TODO Lose screen
                ScreenManager.loadMenuScreen();
            }
        }
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        stage.getBatch().draw(playerSprite, PLAYER_POSITION.x, PLAYER_POSITION.y, SPRITE_DIMENSION, SPRITE_DIMENSION);
        stage.getBatch().draw(enemySprite, ENEMY_POSITION.x, ENEMY_POSITION.y, SPRITE_DIMENSION, SPRITE_DIMENSION);
        stage.getBatch().draw(playerWeaponTexture, PLAYER_WEAPON_X, PLAYER_WEAPON_Y, playerWeaponTexture.getWidth() * WEAPON_TEXTURE_SCALE,playerWeaponTexture.getHeight() * WEAPON_TEXTURE_SCALE);
        stage.getBatch().draw(enemyWeaponTexture, ENEMY_WEAPON_X, ENEMY_WEAPON_Y, enemyWeaponTexture.getWidth() * WEAPON_TEXTURE_SCALE,enemyWeaponTexture.getHeight() * WEAPON_TEXTURE_SCALE );
        stage.getBatch().end();
        stage.draw();

    }

    private void weaponSelection() {

        weaponSelection.setVisible(true);
        moveButton1.setTouchable(Touchable.disabled);
        moveButton1.setDisabled(true);
        moveButton2.setTouchable(Touchable.disabled);
        moveButton2.setDisabled(true);
        moveButton3.setTouchable(Touchable.disabled);
        moveButton3.setDisabled(true);
        moveButton4.setTouchable(Touchable.disabled);
        moveButton4.setDisabled(true);

        //playerView.setWeaponView(enemyView.getDropWeapon());

        // ScreenManager.loadMovementScreen();

    }

    private void updateLabel() {
        playerHpLabel.setText(fight.getPlayer().getName() + " PV: " + fight.getPlayer().getHp());
        enemyHpLabel.setText(fight.getEnemy().getName() + " PV: " + fight.getEnemy().getHp());

    }

    private void checkButton(TextButton button, int moveNumber) {
            if (fight.isMoveUsable(fight.getPlayer(), fight.getPlayer().getWeapon().getMoveList().get(moveNumber))) {
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
