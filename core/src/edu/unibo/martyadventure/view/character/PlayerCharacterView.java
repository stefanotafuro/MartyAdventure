package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;

public class PlayerCharacterView extends CharacterView {
    
    private static PlayerCharacter player = new PlayerCharacter(Shoes.SLOW, "Marty", 100, null, null, 0f , null);
    private static final String PLAYER_PATH = "Characters/Marty/MartyMove (1).png";
    public static final int FRAME_WIDTH = 68;
    public static final int FRAME_HEIGHT = 104;

    
    
    
    public PlayerCharacterView(Vector2 initialPosition) throws InterruptedException, ExecutionException {
        super(initialPosition, 20f, 5f, 70f, loadSprite(initialPosition), loadTexture());
    }
    
    private static TextureRegion loadTexture() throws InterruptedException, ExecutionException {
        Texture texture = new Texture(PLAYER_PATH);
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }

    private static Sprite loadSprite(Vector2 initialPosition) throws InterruptedException, ExecutionException {
        Texture texture = new Texture(PLAYER_PATH);
        TextureRegion[][] textureFrames = TextureRegion.split(texture, FRAME_WIDTH, FRAME_HEIGHT);
        Sprite sprite = new Sprite(textureFrames[0][0].getTexture(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        sprite.setPosition(initialPosition.x, initialPosition.y);
        System.err.println(initialPosition);
        return sprite;
    }

    
    
    

}
