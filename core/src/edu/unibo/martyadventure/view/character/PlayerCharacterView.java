package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;

/**
 * A player character's base providing basic movement, interaction with given the map
 * and visual representation.
 */
public class PlayerCharacterView extends CharacterView {

    private static PlayerCharacter player = new PlayerCharacter(Shoes.SLOW, "Marty", 100, WeaponFactory.createRandomMeleeWeapon("Pugno"));
    private static final String PLAYER_PATH = "Characters/Marty/MartyMove (1).png";
    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;

    
    public PlayerCharacterView(Vector2 initialPosition) throws InterruptedException, ExecutionException {
        super(initialPosition, 20f, 5f, 70f, loadTexture(), FRAME_WIDTH, FRAME_HEIGHT);
    }

    private static TextureRegion loadTexture() throws InterruptedException, ExecutionException {
        Texture texture = new Texture(PLAYER_PATH);
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }

}
