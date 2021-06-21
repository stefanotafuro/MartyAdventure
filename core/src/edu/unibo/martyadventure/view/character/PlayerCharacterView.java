package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.PlayerCharacter;
import edu.unibo.martyadventure.model.character.Shoes;
import edu.unibo.martyadventure.view.Toolbox;
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

    public static final int PLAYER_HP = 300;
    public static final int MAP1_PLAYER_HP_MULTIPLIER = 2;
    public static final int MAP2_PLAYER_HP_MULTIPLIER = 3;
    private static WeaponView playerWeapon = WeaponViewFactory.createPlayerWeaponView();
    private static PlayerCharacter player = new PlayerCharacter(Shoes.SLOW, "", PLAYER_HP, playerWeapon.getWeapon());

    private final String texturePath;
    private boolean disposed;

    public PlayerCharacterView(final String name, final Vector2 initialPosition, final String texturePath)
            throws InterruptedException, ExecutionException {
        super(player, initialPosition, MAX_ACCELLERATION,
                ACCELLERATION_FACTOR, MAX_SPEED, new TextureRegion(Toolbox.getTexture(texturePath)), playerWeapon);
        player.setName(name);
        this.texturePath = texturePath;
        this.disposed = false;
    }

    /**
     * Set the player's current and default weapon.
     */
    public void setWeapon(final WeaponView weapon) {
        super.weaponView = weapon;
        super.character.setWeapon(weapon.getWeapon());
        playerWeapon = weapon;
    }

    /**
     * Dispose of the player's texture
     */
    public void dispose() {
        if (!this.disposed) {
            Toolbox.unloadAsset(this.texturePath);
            this.disposed = true;
        }
    }

    @Override
    public Sprite getFightSprite() {
        return new Sprite(animations.getRightIdle());
    }

    public PlayerCharacter getPlayer() {
        return player;
    }
}