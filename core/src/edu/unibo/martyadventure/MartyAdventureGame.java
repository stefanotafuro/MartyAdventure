package edu.unibo.martyadventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import edu.unibo.martyadventure.view.MovementGameScreen;
import edu.unibo.martyadventure.view.ScreenManager;

public class MartyAdventureGame extends Game {

    @Override
    public void create() {
        ScreenManager.loadMovementScreen();
    }

    

    @Override
    public void dispose() {
        
    }
}
