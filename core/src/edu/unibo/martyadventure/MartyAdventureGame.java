package edu.unibo.martyadventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import edu.unibo.martyadventure.view.MovementGameScreen;

public class MartyAdventureGame extends Game {
    public static final MovementGameScreen SCREEN = new MovementGameScreen();

    @Override
    public void create() {
        setScreen(SCREEN);
    }

    

    @Override
    public void dispose() {
        SCREEN.dispose();
    }
}
