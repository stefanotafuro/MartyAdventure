package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;
import edu.unibo.martyadventure.view.Toolbox;

/**
 * A player character's base providing basic movement, interaction with given
 * the map and visual representation.
 */
public class PlayerCharacterView extends CharacterView<PlayerCharacter> {

    private static PlayerCharacter player = new PlayerCharacter(Shoes.SLOW, "Marty", 9300,
            WeaponFactory.createRandomMeleeWeapon("Pugno", 1.9));
    private static final String PLAYER_PATH = "Characters/Marty/MartyMove (1).png";
    private static final float MAX_ACCELLERATION = 20f;
    private static final float ACCELLERATION_FACTOR = 10f;
    private static final float MAX_SPEED = 100f;


    public PlayerCharacterView(final Vector2 initialPosition) throws InterruptedException, ExecutionException {
        super(player, initialPosition, MAX_ACCELLERATION, ACCELLERATION_FACTOR, MAX_SPEED,
                new TextureRegion(Toolbox.getTexture(PLAYER_PATH)));
    }

    @Override
    public Sprite getFightSprite() {
        return new Sprite(animations.getRightIdle());
    }
}
