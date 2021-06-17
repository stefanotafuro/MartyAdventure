package edu.unibo.martyadventure.view.character;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.view.MapManager;
import edu.unibo.martyadventure.view.MapManager.Maps;

public class PlayerViewFactory {
    
    private static final String MARTY_PATH_1 = "Characters/Marty/MartyMove (1).png";
    private static final String MARTY_PATH_2 = "Characters/Marty/MartyMove (2).png";
    private static final String MARTY_PATH_3 = "Characters/Marty/MartyMove (3).png";


    private Map<MapManager.Maps, String> textureMapPath;
    
    public PlayerViewFactory() {
        textureMapPath = new HashMap<>();
        textureMapPath.put(Maps.MAP1, MARTY_PATH_1);
        textureMapPath.put(Maps.MAP2, MARTY_PATH_2);
        textureMapPath.put(Maps.MAP3, MARTY_PATH_3);
    }
    
    public PlayerCharacterView createPlayer(Vector2 initialPosition, Maps map) throws InterruptedException, ExecutionException {
        return new PlayerCharacterView(initialPosition, loadTexture(map));
    }
    
    private TextureRegion loadTexture(Maps map) throws InterruptedException, ExecutionException {
        Texture texture = new Texture(textureMapPath.get(map));
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }

}
