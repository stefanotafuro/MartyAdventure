package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;
import edu.unibo.martyadventure.view.Toolbox;

public class EnemyCharacterView extends CharacterView<EnemyCharacter> {

    private static final float MAX_ACCELLERATION = 20f;
    private static final float ACCELLERATION_FACTOR = 5f;
    private static final float MAX_SPEED = 70f;


    public EnemyCharacterView(final Vector2 initialPosition, final String enemyPath, final EnemyCharacter enemy)
            throws InterruptedException, ExecutionException {
        super(enemy, initialPosition, MAX_ACCELLERATION, ACCELLERATION_FACTOR, MAX_SPEED,
                new TextureRegion(Toolbox.getTexture(enemyPath)));
    }

    @Override
    public Sprite getFightSprite() {
        return new Sprite(animations.getDownIdle());
    }
}
