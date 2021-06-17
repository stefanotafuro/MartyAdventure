package edu.unibo.martyadventure;

import com.badlogic.gdx.Game;

import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.screen.ScreenManager;

public class MartyAdventureGame extends Game {

    @Override
    public void create() {
        ScreenManager.loadMenuScreen();
    }

    @Override
    public void dispose() {

    }
}
