package edu.unibo.martyadventure;

import com.badlogic.gdx.Game;

import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.MovementGameScreen;
import edu.unibo.martyadventure.view.ScreenManager;

public class MartyAdventureGame extends Game {

    @Override
    public void create() {
        ScreenManager.changeMap(MapManager.Maps.MAP1);
        ScreenManager.loadMovementScreen();
    }

    

    @Override
    public void dispose() {
        
    }
}
