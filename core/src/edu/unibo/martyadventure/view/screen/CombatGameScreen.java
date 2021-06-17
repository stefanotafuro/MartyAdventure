package edu.unibo.martyadventure.view.screen;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.model.character.Character;

public class CombatGameScreen extends StaticScreen {

    private static final int BASE_HEIGHT = 1080;
    private static final int BASE_WIDTH = 1920;
    private static final int TABLE_POSITION_Y = 135;
    private static final int ROW_SPACE = 15;
    private static final int BUTTON_SPACE = 220;

    private static final Vector2 PLAYER_POSITION = new Vector2(220, 320);
    private static final Vector2 ENEMY_POSITION = new Vector2(1360, 780);

    private static final int SPRITE_DIMENSION = 300;
    private static final String BG_PATH = "Level/Fight/fight_map1.png";

    private static final Vector2 PLAYER_HP_LABEL_POSITION = new Vector2(70, 700);
    private static final Vector2 ENEMY_HP_LABEL_POSITION = new Vector2(1200, 450);
    private static final Vector2 PLAYER_WEAPON_LABEL_POSITION = new Vector2(70, 650);
    private static final Vector2 ENEMY_WEAPON_LABEL_POSITION = new Vector2(1200, 500);

    private Sprite playerSprite;
    private Sprite enemySprite;
    private Fight fight;
    private Label playerHpLabel;
    private Label enemyHpLabel;
    private TextButton moveButton1;
    private TextButton moveButton2;
    private TextButton moveButton3;
    private TextButton moveButton4;


    private TextButton getButton(final int index) {
        final TextButton button = new TextButton(fight.getPlayer().getWeapon().getMoveList().get(index).getName(),
                uiSkin);
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
        final Label label = new Label(text, uiSkin, "title");
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
        super(BG_PATH, BASE_WIDTH, BASE_HEIGHT);

        this.playerSprite = player.getFightSprite();
        this.enemySprite = enemy.getFightSprite();

        fight = new Fight(player.getCharacter(), enemy.getCharacter());
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
                fight.getPlayer().setWeapon(fight.getEnemy().getDropitem());
                ScreenManager.loadMovementScreen();
            } else {
                // TODO lose sreen
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
}
