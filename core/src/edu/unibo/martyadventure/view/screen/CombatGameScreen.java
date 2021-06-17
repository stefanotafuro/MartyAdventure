package edu.unibo.martyadventure.view.screen;

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
import edu.unibo.martyadventure.view.Toolbox;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.model.character.Character;

public class CombatGameScreen implements Screen {

    private static final int BASE_HEIGHT = 1080;
    private static final int BASE_WIDTH = 1920;
    private static final int TABLE_POSITION_Y = 135;
    private static final int ROW_SPACE = 15;
    private static final int BUTTON_SPACE = 220;

    private static final Vector2 PLAYER_POSITION = new Vector2(220, 320);
    private static final Vector2 ENEMY_POSITION = new Vector2(1360, 780);

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

    private static final Vector2 PLAYER_HP_LABEL_POSITION = new Vector2(70, 700);
    private static final Vector2 ENEMY_HP_LABEL_POSITION = new Vector2(1200, 450);
    private static final Vector2 PLAYER_WEAPON_LABEL_POSITION = new Vector2(70, 650);
    private static final Vector2 ENEMY_WEAPON_LABEL_POSITION = new Vector2(1200, 500);

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


    private TextButton getButton(final int index) {
        final TextButton button = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(index).getName(),
                buttonSkin);
        button.addListener(new ClickListener() {

            @SuppressWarnings("unused")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fight.playerAttack(fight.getPlayer().getWeapon().getMoveList().get(index));
            }
        });
        return button;
    }

    private Label getLabel(final String text, final Vector2 position) {
        final Label label = new Label(text, buttonSkin, "title");
        label.setSize(100, 100);
        label.setPosition(position.x, position.y);
        return label;
    }

    private Label getMoveLabel(final int index) {
        return getLabel("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(index).getReloadTime(),
                Vector2.Zero);
    }

    private Label getWeaponLabel(final Character character, final Vector2 position) {
        return getLabel("Weapon : " + character.getWeapon().getName() + " Dmg: "
                + new DecimalFormat("###.#").format(character.getWeapon().getDamageMultiplier()), position);
    }

    public CombatGameScreen(PlayerCharacterView player, EnemyCharacterView enemy) {
        background = Toolbox.getTexture(BG_PATH);
        buttonAtlas = new TextureAtlas("skin/comic-ui.atlas");
        buttonSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"), buttonAtlas);

        this.playerSprite = player.getFightSprite();
        this.enemySprite = enemy.getFightSprite();

        fight = new Fight(player.getCharacter(), enemy.getCharacter());

        viewport = new FitViewport(BASE_WIDTH, BASE_HEIGHT);
        viewport.apply();
        stage = new Stage(viewport);
        playerWeaponTexture = player.getWeaponView().getWeaponTexture();
        enemyWeaponTexture = enemy.getWeaponView().getWeaponTexture();
        playerView = player;
        enemyView = enemy;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create buttons
        moveButton1 = getButton(0);
        moveButton2 = getButton(1);
        moveButton3 = getButton(2);
        moveButton4 = getButton(3);

        // Create Table
        Table mainTable = new Table();
        mainTable.setTransform(true);
        mainTable.setPosition(stage.getWidth() / 2, TABLE_POSITION_Y);
        mainTable.center();

        // Add buttons to table
        mainTable.row();
        mainTable.add(moveButton1);
        mainTable.add(getMoveLabel(0)).spaceRight(BUTTON_SPACE);
        mainTable.add(moveButton2);
        mainTable.add(getMoveLabel(1));

        mainTable.row().spaceTop(ROW_SPACE);
        mainTable.add(moveButton3);
        mainTable.add(getMoveLabel(2)).spaceRight(BUTTON_SPACE);
        mainTable.add(moveButton4);
        mainTable.add(getMoveLabel(3));

        // Add table to stage
        stage.addActor(mainTable);

        // Create label
        playerHpLabel = getLabel("", PLAYER_HP_LABEL_POSITION);
        enemyHpLabel = getLabel("", ENEMY_HP_LABEL_POSITION);

        Label playerWeaponLabel = getWeaponLabel(fight.getPlayer(), PLAYER_WEAPON_LABEL_POSITION);
        Label enemyWeaponLabel = getWeaponLabel(fight.getEnemy(), ENEMY_WEAPON_LABEL_POSITION);

        stage.addActor(playerHpLabel);
        stage.addActor(enemyHpLabel);
        stage.addActor(enemyWeaponLabel);
        stage.addActor(playerWeaponLabel);
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
        playerHpLabel.setText(fight.getPlayer().getName() + " HP: " + fight.getPlayer().getHp());
        enemyHpLabel.setText(fight.getEnemy().getName() + " HP: " + fight.getEnemy().getHp());
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
