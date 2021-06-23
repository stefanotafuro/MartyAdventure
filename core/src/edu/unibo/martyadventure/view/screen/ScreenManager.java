package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.CharacterViewFactory;
import edu.unibo.martyadventure.view.character.EnemyCharacterView;
import edu.unibo.martyadventure.view.character.Player;
import edu.unibo.martyadventure.view.character.PlayerCharacterView;

/**
 * Manage the display of the screens
 */
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

    /**
     * Show the given screen
     * 
     * @param s
     */
    private void loadScreen(final Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    public ScreenManager() {
        this.menu = new MenuScreen(this);
        this.choice = new PlayerChoiceScreen(this);
        this.characterFactory = new CharacterViewFactory();
    }

    /**
     * Clean the MovementGameScreen
     */
    public void cleanMovementScreen() {
        if (this.gameScreen != null) {
            this.gameScreen.dispose();
            this.gameScreen = null;
        }
    }

    /**
     * Change the movement screen based on the given map
     * 
     * @param map
     */
    public void changeMovementScreen(final Maps map) {
        clearDynamicScreen();
        cleanMovementScreen();
        this.gameScreen = new MovementGameScreen(this, this.characterFactory, this.currentPlayer, map);
    }

    /**
     * Change the current player
     * 
     * @param player
     */
    public void changePlayer(final Player player) {
        this.currentPlayer = player;
    }

    /**
     * Show the current movement screen
     */
    public void loadMovementScreen() {
        clearDynamicScreen();
        loadScreen(this.gameScreen);
    }

    /**
     * Show the menu screen
     */
    public void loadMenuScreen() {
        clearDynamicScreen();
        loadScreen(this.menu);
    }

    /**
     * Show the screen that allow you to choose the character to play
     */
    public void loadChoiceScreen() {
        clearDynamicScreen();
        loadScreen(this.choice);
    }

    /**
     * Load a new combat screen
     * 
     * @param player
     * @param enemy
     * @param displayGameOver
     */
    public void loadCombatScreen(final PlayerCharacterView player, final EnemyCharacterView enemy,
            final boolean displayGameOver) {
        clearDynamicScreen();
        this.dynamicScreen = new CombatGameScreen(this, player, enemy, displayGameOver);
        loadScreen(this.dynamicScreen);
    }

    /**
     * Load the gameOver screen
     * 
     * @param playerWon if win or lose
     */
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
