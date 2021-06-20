package edu.unibo.martyadventure;

import com.badlogic.gdx.Game;

import edu.unibo.martyadventure.view.screen.ScreenManager;

public class MartyAdventureGame extends Game {

    private ScreenManager manager;

    @Override
    public void create() {
        manager = new ScreenManager();
        manager.loadMenuScreen();
    }

    @Override
    public void dispose() {
        manager.dispose();
    }
}
