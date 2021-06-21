package edu.unibo.martyadventure.view.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import edu.unibo.martyadventure.view.MapManager.Maps;
import edu.unibo.martyadventure.view.character.CharacterViewFactory;
import edu.unibo.martyadventure.view.character.Player;

public class ScreenManager {

    public static class VIEWPORT {

        public static int X_VIEWPORT = 20;
        public static int Y_VIEWPORT = 15;
    }


    private final MenuScreen menu;
    private final PlayerChoiceScreen choice;
    private final CharacterViewFactory characterFactory;

    private MovementGameScreen movementScreen;
    private Player currentPlayer;


    private void disposeMovementScreen() {
        if (this.movementScreen != null) {
            this.movementScreen.dispose();
            this.movementScreen = null;
        }
    }

    private void loadScreen(final Screen s) {
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(s);
    }

    public ScreenManager() {
        menu = new MenuScreen(this);
        choice = new PlayerChoiceScreen(this);
        characterFactory = new CharacterViewFactory();
    }

    public void changeMap(final Maps map) {
        disposeMovementScreen();
        this.movementScreen = new MovementGameScreen(this, this.characterFactory, currentPlayer, map);
    }

    public void changePlayer(final Player player) {
        this.currentPlayer = player;
    }

    public void loadMovementScreen() {
        loadScreen(this.movementScreen);
    }

    public void loadMenuScreen() {
        disposeMovementScreen();
        loadScreen(this.menu);
    }

    public void loadChoiceScreen() {
        disposeMovementScreen();
        loadScreen(this.choice);
    }

    public void loadCombatScreen(final CombatGameScreen screen) {
        loadScreen(screen);
    }

    public void dispose() {
        disposeMovementScreen();
        this.menu.dispose();
        this.choice.dispose();
        this.characterFactory.dispose();
    }
}
