package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.view.weapon.WeaponView;

public class EnemyCharacterView extends CharacterView {

    private EnemyCharacter enemy;
    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;
    private WeaponView dropWeapon;

    public EnemyCharacterView(Vector2 initialPosition, String enemy_path, EnemyCharacter enemy, WeaponView weapon, WeaponView dropWeapon)
            throws InterruptedException, ExecutionException {
        super(initialPosition, 20f, 5f, 70f, new TextureRegion(new Texture(enemy_path)), FRAME_WIDTH, FRAME_HEIGHT, weapon);
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
