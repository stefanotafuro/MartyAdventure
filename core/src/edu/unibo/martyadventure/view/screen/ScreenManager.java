package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;

public class ScreenManager {

    public static class VIEWPORT {

        public static float viewportWidth;
        public static float viewportHeight;
        public static float virtualWidth;
        public static float virtualHeight;
        public static float physicalWidth;
        public static float physicalHeight;
        public static float aspectRatio;
        public static int ZOOM = 75;
    }


    private static MovementGameScreen movementScreen;


    private ScreenManager() {}

    private static void loadScreen(Screen s) {
        final Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    public static void changeMap(Maps map) {
        movementScreen = new MovementGameScreen(map);
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

    public static void loadChoicecreen() {
        loadScreen(choice);
    }

    public static void loadCombatScreen(CombatGameScreen screen) {
        loadScreen(screen);
    }

    private static void loadScreen(Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }
}
