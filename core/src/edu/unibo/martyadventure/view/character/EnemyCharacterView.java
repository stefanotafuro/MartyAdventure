package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.view.weapon.WeaponView;

public class EnemyCharacterView extends CharacterView {

    private static final float MAX_SPEED = 70f;
    private static final float ACCELERATION_FACTOR = 5f;
    private static final float MAX_ACCELERATION = 20f;
    private EnemyCharacter enemy;
    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;
    private WeaponView dropWeapon;

    public EnemyCharacterView(Vector2 initialPosition, String enemy_path, EnemyCharacter enemy, WeaponView weapon, WeaponView dropWeapon)
            throws InterruptedException, ExecutionException {
        super(initialPosition, MAX_ACCELERATION, ACCELERATION_FACTOR, MAX_SPEED, new TextureRegion(new Texture(enemy_path)), FRAME_WIDTH, FRAME_HEIGHT, weapon);
        this.enemy = enemy;
        this.dropWeapon = dropWeapon;
    }

    public EnemyCharacter getEnemy() {
        return enemy;
    }

    public WeaponView getDropWeapon() {
        return dropWeapon;
    }

}
