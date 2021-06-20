package edu.unibo.martyadventure.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.character.Player;

public class ScreenManager {

    public static class VIEWPORT {
        public static int X_VIEWPORT = 20;
        public static int Y_VIEWPORT = 15;
    }

    private static MovementGameScreen movementScreen;
    private static MenuScreen menu = new MenuScreen();
    private static PlayerChoiceScreen choice = new PlayerChoiceScreen();
    private static Player currentPlayer;

    private ScreenManager() {

    }

    public static void changeMap(MapManager.Maps map) {
        movementScreen = new MovementGameScreen(currentPlayer, map);
    }

    public static void changePlayer(Player player) {
        currentPlayer = player;
    }

    public static void loadMovementScreen() {
        loadScreen(movementScreen);
    }

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
