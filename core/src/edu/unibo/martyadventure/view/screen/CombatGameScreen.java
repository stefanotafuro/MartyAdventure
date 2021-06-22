package edu.unibo.martyadventure.view.screen;

import java.text.DecimalFormat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import edu.unibo.martyadventure.model.fight.Fight;
import edu.unibo.martyadventure.model.weapon.Weapon;
import edu.unibo.martyadventure.view.Toolbox;
import edu.unibo.martyadventure.view.character.CharacterView;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;
import edu.unibo.martyadventure.model.character.Character;
import edu.unibo.martyadventure.model.character.PlayerCharacter;

class CombatGameScreen extends StaticScreen {

    private static final int ZOOM = 100;
    private static final int TABLE_POSITION_Y = 190;
    private static final int ROW_SPACE = 100;
    private static final int BUTTON_SPACE = 30;
    private static final int TITLE_Y = 270;
    private static final int WEAPON_SELECTION_WEAPON_SPACE = 350;
    private static final int X_TABLE_SPACE = 330;

    private static final Vector2 PLAYER_POSITION = new Vector2(220, 470);
    private static final Vector2 ENEMY_POSITION = new Vector2(1360, 1100);

    private static final int SPRITE_DIMENSION = 300;
    private static final String BG_PATH = "Level/Fight/fight_map1.png";

    private static final float WEAPON_TEXTURE_SCALE = 2;
    private static final String WEAPON_SELECTION_PATH = "Level/Fight/WeaponSelection.png";

    private static final Vector2 PLAYER_HP_LABEL_POSITION = new Vector2(150, 900);
    private static final Vector2 ENEMY_HP_LABEL_POSITION = new Vector2(1400, 800);
    private static final Vector2 PLAYER_WEAPON_LABEL_POSITION = new Vector2(150, 1000);
    private static final Vector2 ENEMY_WEAPON_LABEL_POSITION = new Vector2(1400, 700);
    private static final Vector2 PLAYER_WEAPON_POSITION = new Vector2(250, 1150);
    private static final Vector2 ENEMY_WEAPON_POSITION = new Vector2(1150, 750);
    private static final Vector2 PLAYER_FAIL_LABEL_POSITION = new Vector2(150, 800);
    private static final Vector2 ENEMY_FAIL_LABEL_POSITION = new Vector2(1150, 1000);

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.#");

    private PlayerCharacterView playerView;
    private EnemyCharacterView enemyView;
    private Fight fight;
    private Label playerHpLabel;
    private Label enemyHpLabel;
    private TextButton moveButton1;
    private TextButton moveButton2;
    private TextButton moveButton3;
    private TextButton moveButton4;
    private Window weaponSelectionWindow;
    
    public CombatGameScreen(final ScreenManager manager, final PlayerCharacterView player,
            final EnemyCharacterView enemy) {
        super(manager, BG_PATH, ScreenManager.VIEWPORT.X_VIEWPORT * ZOOM, ScreenManager.VIEWPORT.Y_VIEWPORT * ZOOM);

        this.playerView = player;
        this.enemyView = enemy;

        fight = new Fight(player.getCharacter(), enemy.getCharacter());
    }

    private TextButton getIndexedButton(final int index) {
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
        return getLabel("Reload: " + fight.getPlayer().getWeapon().getMoveList().get(index).getReloadTime()
                + " Dmg: " + getFullDamage(index),
                Vector2.Zero);
    }
    
    private Label getFailLabel(final Character character, final Vector2 position) {
            if(fight.getLastFail(character)){
                return getLabel(character.getName() + " ha fallito l'attacco con " + fight.getEnemyLastMove(),
                        position);
            } else {
                return getLabel(character.getName() + " ha colpito con " + fight.getEnemyLastMove(),
                        position);
            }
    }
    private Label getWeaponLabel(final Weapon weapon, final Vector2 position) {
        return getLabel("Arma: " + weapon.getName() + " \nDanno: "
                + DECIMAL_FORMAT.format(weapon.getDamageMultiplier()),
                position);
    }

    private Window getWeaponSelection() {
        Window weaponSelection = new Window("", super.uiSkin);
        weaponSelection.setBackground(new TextureRegionDrawable(Toolbox.getTexture(WEAPON_SELECTION_PATH)));
        weaponSelection.setSize(stage.getWidth() / 2, stage.getHeight() / 4);
        weaponSelection.setPosition(stage.getWidth() / 4, stage.getHeight() / 3);
        weaponSelection.setVisible(false);

        TextButton weapon1Button = new TextButton(fight.getPlayer().getWeapon().getName(), super.uiSkin);
        TextButton weapon2Button = new TextButton(fight.getEnemy().getDropitem().getName(), super.uiSkin);
        weapon1Button.setPosition(weaponSelection.getWidth() / 4 - weapon1Button.getWidth() / 2, 35);
        weapon2Button.setPosition((weaponSelection.getWidth() / 4) * 3 - weapon2Button.getWidth() / 2, 35);

        Image weapon1Image = new Image(playerView.getWeaponView().getWeaponTexture());
        Image weapon2Image = new Image(enemyView.getDropWeapon().getWeaponTexture());

        Label titleLabel = new Label("Scegli che arma equipaggiare", super.uiSkin, "title");
        Label playerDropLabel = new Label(
                "La tua arma :\nDanno: " + DECIMAL_FORMAT.format(fight.getPlayer().getWeapon().getDamageMultiplier()),
                super.uiSkin, "title");
        Label enemyDropLabel = new Label(
                "Drop di " + fight.getEnemy().getName() + ":\n" + "Danno: "
                        + DECIMAL_FORMAT.format(fight.getEnemy().getDropitem().getDamageMultiplier()),
                super.uiSkin, "title");

        titleLabel.setPosition(weaponSelection.getWidth() / 2 - titleLabel.getWidth() / 2, TITLE_Y);
        weaponSelection.addActor(weapon1Button);
        weaponSelection.addActor(weapon2Button);
        weaponSelection.addActor(titleLabel);

        weaponSelection.row();
        weaponSelection.add(weapon1Image).spaceRight(WEAPON_SELECTION_WEAPON_SPACE).center();
        weaponSelection.add(weapon2Image);

        weaponSelection.row();
        weaponSelection.add(playerDropLabel).left();
        weaponSelection.add(enemyDropLabel);

        weapon1Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.loadMovementScreen();
            }
        });

        weapon2Button.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                playerView.setWeapon(enemyView.getDropWeapon());
                screenManager.loadMovementScreen();
            }
        });

        return weaponSelection;
    }

    private void updateLabel() {
        playerHpLabel.setText(fight.getPlayer().getName() + " HP: " + fight.getPlayer().getHp());
        enemyHpLabel.setText(fight.getEnemy().getName() + " HP: " + fight.getEnemy().getHp());
    }

    private void checkButton(final TextButton button, final int moveNumber) {
        final PlayerCharacter player = fight.getPlayer();
        if (fight.isMoveUsable(player, player.getWeapon().getMoveList().get(moveNumber))) {
            button.setTouchable(Touchable.enabled);
            button.setDisabled(false);
        } else {
            button.setTouchable(Touchable.disabled);
            button.setDisabled(true);
        }
    }

    private <C extends Character> void drawCharacter(final CharacterView<C> characterView,
            final Vector2 characterPosition, final Vector2 weaponPosition, final Batch batch) {
        final Texture weapon = characterView.getWeaponView().getWeaponTexture();
        batch.draw(characterView.getFightSprite(), characterPosition.x, characterPosition.y, SPRITE_DIMENSION,
                SPRITE_DIMENSION);
        batch.draw(weapon, weaponPosition.x, weaponPosition.y, weapon.getWidth() * WEAPON_TEXTURE_SCALE,
                weapon.getHeight() * WEAPON_TEXTURE_SCALE);
    }

    private void weaponSelectionMode() {
        this.weaponSelectionWindow.setVisible(true);
        this.moveButton1.setTouchable(Touchable.disabled);
        this.moveButton1.setDisabled(true);
        this.moveButton2.setTouchable(Touchable.disabled);
        this.moveButton2.setDisabled(true);
        this.moveButton3.setTouchable(Touchable.disabled);
        this.moveButton3.setDisabled(true);
        this.moveButton4.setTouchable(Touchable.disabled);
        this.moveButton4.setDisabled(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Create buttons
        moveButton1 = getIndexedButton(0);
        moveButton2 = getIndexedButton(1);
        moveButton3 = getIndexedButton(2);
        moveButton4 = getIndexedButton(3);

        // Create Table
        Table mainTable = new Table();
        mainTable.setTransform(true);
        mainTable.setPosition(stage.getWidth() / 2, TABLE_POSITION_Y);
        mainTable.center();

        // Add buttons to table
        mainTable.row();
        mainTable.add(moveButton1).center().spaceRight(BUTTON_SPACE);
        mainTable.add(getMoveLabel(0)).right().spaceRight(X_TABLE_SPACE);
        mainTable.add(moveButton2).center().spaceRight(BUTTON_SPACE);
        mainTable.add(getMoveLabel(1)).right();

        mainTable.row().spaceTop(ROW_SPACE);
        mainTable.add(moveButton3).center().spaceRight(BUTTON_SPACE);
        mainTable.add(getMoveLabel(2)).right().spaceRight(X_TABLE_SPACE);
        mainTable.add(moveButton4).center().spaceRight(BUTTON_SPACE);
        mainTable.add(getMoveLabel(3)).right();

        // Add table to stage
        stage.addActor(mainTable);

        // Create label
        playerHpLabel = getLabel("", PLAYER_HP_LABEL_POSITION);
        enemyHpLabel = getLabel("", ENEMY_HP_LABEL_POSITION);

        Label playerWeaponLabel = getWeaponLabel(fight.getPlayer().getWeapon(), PLAYER_WEAPON_LABEL_POSITION);
        Label enemyWeaponLabel = getWeaponLabel(fight.getEnemy().getWeapon(), ENEMY_WEAPON_LABEL_POSITION);
        Label playerFailLabel = getFailLabel(fight.getPlayer(), PLAYER_FAIL_LABEL_POSITION);
        Label enemyFailLabel = getFailLabel(fight.getEnemy(), ENEMY_FAIL_LABEL_POSITION);

        stage.addActor(playerHpLabel);
        stage.addActor(enemyHpLabel);
        stage.addActor(enemyWeaponLabel);
        stage.addActor(playerWeaponLabel);
        stage.addActor(playerFailLabel);
        stage.addActor(enemyFailLabel);

        this.weaponSelectionWindow = getWeaponSelection();
        stage.addActor(this.weaponSelectionWindow);
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
                weaponSelectionMode();
            } else {
                // TODO lose screen
                System.err.println("GAME OVER");
                Gdx.app.exit();
            }
        }

        stage.act();
        final Batch batch = stage.getBatch();
        batch.begin();
        batch.draw(background, 0, 0, stage.getWidth(), stage.getHeight());
        drawCharacter(this.playerView, PLAYER_POSITION, PLAYER_WEAPON_POSITION, batch);
        drawCharacter(this.enemyView, ENEMY_POSITION, ENEMY_WEAPON_POSITION, batch);
        batch.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        Toolbox.unloadAsset(WEAPON_SELECTION_PATH);
        super.dispose();
    }
    
    private int getFullDamage(int moveNumber) {
        return (int) Math.round(fight.getPlayer().getWeapon().getMoveList().get(moveNumber).getDamage()*fight.getPlayer().getWeapon().getDamageMultiplier());
    }
}
