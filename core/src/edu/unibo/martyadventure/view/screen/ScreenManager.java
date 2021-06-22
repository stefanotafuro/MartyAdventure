package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.CharacterViewFactory;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.Player;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;

public class ScreenManager {

    public static class VIEWPORT {

        public static int X_VIEWPORT = 20;
        public static int Y_VIEWPORT = 15;
    }


    private final MenuScreen menu;
    private final PlayerChoiceScreen choice;
    private final CharacterViewFactory characterFactory;

    // Keep track of the dynamically instantiated screens.
    private MovementGameScreen gameScreen;
    private Screen dynamicScreen;
    private Player currentPlayer;


    /**
     * Disposes the current dynamic screen, if any
     */
    private void clearDynamicScreen() {
        if (this.dynamicScreen != null) {
            this.dynamicScreen.dispose();
            this.dynamicScreen = null;
        }
    }

    private void loadScreen(final Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    public ScreenManager() {
        this.menu = new MenuScreen(this);
        this.choice = new PlayerChoiceScreen(this);
        this.characterFactory = new CharacterViewFactory();
    }

    public void cleanMovementScreen() {
        if (this.gameScreen != null) {
            this.gameScreen.dispose();
            this.gameScreen = null;
        }
    }

    public void changeMovementScreen(final Maps map) {
        clearDynamicScreen();
        cleanMovementScreen();
        this.gameScreen = new MovementGameScreen(this, this.characterFactory, this.currentPlayer, map);
    }

    public void changePlayer(final Player player) {
        this.currentPlayer = player;
    }

    public void loadMovementScreen() {
        clearDynamicScreen();
        loadScreen(this.gameScreen);
    }

    public void loadMenuScreen() {
        clearDynamicScreen();
        loadScreen(this.menu);
    }

    public void loadChoiceScreen() {
        clearDynamicScreen();
        loadScreen(this.choice);
    }

    public void loadCombatScreen(final PlayerCharacterView player, final EnemyCharacterView enemy, final boolean displayGameOver) {
        clearDynamicScreen();
        this.dynamicScreen = new CombatGameScreen(this, player, enemy, displayGameOver);
        loadScreen(this.dynamicScreen);
    }

    public void loadGameOverScreen(final boolean playerWon) {
        clearDynamicScreen();
        this.dynamicScreen = new GameOverScreen(this, playerWon);
        loadScreen(this.dynamicScreen);
    }

    public void dispose() {
        clearDynamicScreen();
        cleanMovementScreen();
        this.menu.dispose();
        this.choice.dispose();
        this.characterFactory.dispose();
    }
}
