package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.PlayerChoiceScreen;
import edu.unibo.martyadventure.view.character.Player;

public class ScreenManager {

    public static class VIEWPORT {

        public static int X_VIEWPORT = 20;
        public static int Y_VIEWPORT = 15;
    }


    private static MovementGameScreen movementScreen;


    private static void loadScreen(final Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    private ScreenManager() {}

    public static void changeMap(final Maps map) {
        movementScreen = new MovementGameScreen(currentPlayer, map);
    }

    public static void changePlayer(final Player player) {
        currentPlayer = player;
    }

    public static void loadMovementScreen() {
        loadScreen(movementScreen);
    }

    public static void loadCombatScreen(CombatGameScreen screen) {
        loadScreen(screen);
    }

    /**
     * Setup the Viewport according due the screen dimensions
     *
     * @param width
     * @param height
     */
    public static void setupViewport(int width, int height) {
        // Make the viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

    public static void loadMenuScreen() {
        loadScreen(menu);
    }

    public static void loadChoiceScreen() {
        loadScreen(choice);
    }

    public static void loadCombatScreen(final CombatGameScreen screen) {
        loadScreen(screen);
    }
}
