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
    
    private static final String BIFF_PATH_1 = "Characters/Biff/BiffMove (1).png";
    private static final String BIFF_PATH_2 = "Characters/Biff/BiffMove (2).png";
    private static final String BIFF_PATH_3 = "Characters/Biff/BiffMove (3).png";
    
    private static final String DOC_PATH_1 = "Characters/Doc/DocMove (1).png";
    private static final String DOC_PATH_2 = "Characters/Doc/DocMove (2).png";
    private static final String DOC_PATH_3 = "Characters/Doc/DocMove (3).png";

    private Map<MapManager.Maps, String> martyTextureMapPath;
    private Map<MapManager.Maps, String> docTextureMapPath;
    private Map<MapManager.Maps, String> biffTextureMapPath;
    private Map<Player, Map<MapManager.Maps, String>> playerMap;
    
    public PlayerViewFactory() {
        martyTextureMapPath = new HashMap<>();
        martyTextureMapPath.put(Maps.MAP1, MARTY_PATH_1);
        martyTextureMapPath.put(Maps.MAP2, MARTY_PATH_2);
        martyTextureMapPath.put(Maps.MAP3, MARTY_PATH_3);
        
        biffTextureMapPath = new HashMap<>();
        biffTextureMapPath.put(Maps.MAP1, BIFF_PATH_1);
        biffTextureMapPath.put(Maps.MAP2, BIFF_PATH_2);
        biffTextureMapPath.put(Maps.MAP3, BIFF_PATH_3);
        
        docTextureMapPath = new HashMap<>();
        docTextureMapPath.put(Maps.MAP1, DOC_PATH_1);
        docTextureMapPath.put(Maps.MAP2, DOC_PATH_2);
        docTextureMapPath.put(Maps.MAP3, DOC_PATH_3);
        
        playerMap = new HashMap<>();
        playerMap.put(Player.MARTY, martyTextureMapPath);
        playerMap.put(Player.DOC, docTextureMapPath);
        playerMap.put(Player.BIFF, biffTextureMapPath);
    }
    
    public PlayerCharacterView createPlayer(Player player, Vector2 initialPosition, Maps map) throws InterruptedException, ExecutionException {
        return new PlayerCharacterView(player.getName(),initialPosition, loadTexture(player,map));
    }
    
    private TextureRegion loadTexture(Player player, Maps map) throws InterruptedException, ExecutionException {
        Texture texture = new Texture(playerMap.get(player).get(map));
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }

}
