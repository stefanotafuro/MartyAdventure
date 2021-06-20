package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;
import edu.unibo.martyadventure.view.weapon.WeaponView;
import edu.unibo.martyadventure.view.weapon.WeaponViewFactory;

/**
 * A player character's base providing basic movement, interaction with given
 * the map and visual representation.
 */
public class PlayerCharacterView extends CharacterView<PlayerCharacter> {

    private static final float MAX_ACCELLERATION = 20.0f;
    private static final float ACCELLERATION_FACTOR = 10.0f;
    private static final float MAX_SPEED = 100.0f;

    private static final int PLAYER_HP = 9300;

    private static WeaponView playerWeapon = WeaponViewFactory.createPlayerWeaponView();


    public PlayerCharacterView(final String name, final Vector2 initialPosition, final TextureRegion textureRegion)
            throws InterruptedException, ExecutionException {
        super(new PlayerCharacter(Shoes.SLOW, name, PLAYER_HP, playerWeapon.getWeapon()), initialPosition, MAX_ACCELLERATION,
                ACCELLERATION_FACTOR, MAX_SPEED, textureRegion, playerWeapon);
    }

    /**
     * Set the player's current and default weapon.
     */
    public void setWeapon(final WeaponView weapon) {
        super.weaponView = weapon;
        super.character.setWeapon(weapon.getWeapon());
        playerWeapon = weapon;
    }

    @Override
    public Sprite getFightSprite() {
        return new Sprite(animations.getRightIdle());
    }
}
