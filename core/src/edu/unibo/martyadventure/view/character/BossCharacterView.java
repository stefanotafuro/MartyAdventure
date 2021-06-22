package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.view.Toolbox;
import edu.unibo.martyadventure.view.weapon.WeaponView;

/**
 * Represents a boss character. In contrast to generic enemies, bosses manage
 * their own textures.
 */
public class BossCharacterView extends EnemyCharacterView implements Disposable {

    private final String texturePath;
    private boolean disposed;


    BossCharacterView(final EnemyCharacter character, final Vector2 initialPosition, final String texturePath,
            final WeaponView weapon, final WeaponView dropWeapon) throws InterruptedException, ExecutionException {
        super(character, initialPosition, Toolbox.getTexture(texturePath), weapon, dropWeapon);
        this.texturePath = texturePath;
        this.disposed = false;
    }

    /**
     * Dispose of the boss's texture
     */
    @Override
    public void dispose() {
        if (!this.disposed) {
            Toolbox.unloadAsset(this.texturePath);
            this.disposed = true;
        }
    }
}
