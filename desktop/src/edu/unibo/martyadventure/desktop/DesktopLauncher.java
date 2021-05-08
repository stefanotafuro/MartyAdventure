package edu.unibo.martyadventure.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import edu.unibo.martyadventure.MartyAdventureGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        
        Lwjgl3Application app = new Lwjgl3Application(new MartyAdventureGame(), config);
        Gdx.app = app;
    }
}
