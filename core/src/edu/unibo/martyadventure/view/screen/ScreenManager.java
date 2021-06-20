package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.Player;

public class ScreenManager {

    public static class VIEWPORT {

        public static int X_VIEWPORT = 20;
        public static int Y_VIEWPORT = 15;
    }

    private final MenuScreen menu;
    private final PlayerChoiceScreen choice;

    private MovementGameScreen movementScreen;
    private Player currentPlayer;

    private void loadScreen(final Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    public ScreenManager() {
        menu = new MenuScreen(this);
        choice = new PlayerChoiceScreen(this);
    }

    public void changeMap(final Maps map) {
        if (movementScreen != null) {
            movementScreen.dispose();
        }
        movementScreen = new MovementGameScreen(this, currentPlayer, map);
    }

    public void changePlayer(final Player player) {
        currentPlayer = player;
    }

    public void loadMovementScreen() {
        loadScreen(movementScreen);
    }

    public void loadMenuScreen() {
        loadScreen(menu);
    }

    public void loadChoiceScreen() {
        loadScreen(choice);
    }

    public void loadCombatScreen(final CombatGameScreen screen) {
        loadScreen(screen);
    }

    public void dispose() {
        if (movementScreen != null) {
            movementScreen.dispose();
        }
        menu.dispose();
        choice.dispose();
    }
}
