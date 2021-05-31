package edu.unibo.martyadventure.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import edu.unibo.martyadventure.MartyAdventureGame;

public class DesktopLauncher {

    /**
     * Handle vertical and ultrawide displays by selecting the smallest dimension.
     */
    private static int getBaseScale() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        // Get the smallest dimension and scale it at 80%. Use doubles for better
        // precision.
        final double base = Math.min(screen.width, screen.width) / 5.0 * 4.0;
        return (int) Math.round(base);
    }

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Marty's Adventure");
        // Set the game window to the scaled ratio of 640x480.
        final int base = getBaseScale();
        config.setWindowedMode(base, (int) (base * 0.75));

        config.setForegroundFPS(60);
        new Lwjgl3Application(new MartyAdventureGame(), config);
    }
}
