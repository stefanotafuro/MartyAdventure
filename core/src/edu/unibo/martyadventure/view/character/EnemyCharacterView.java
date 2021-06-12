package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;

public class EnemyCharacterView extends CharacterView {
    
    private static EnemyCharacter enemy = new EnemyCharacter(WeaponFactory.createRandomMeleeWeapon("Mazza"), "Biff", 800, WeaponFactory.createRandomMeleeWeapon("Schiaffo") );
    private static final String ENEMY_PATH = "Characters/Biff/BiffMove (1).png";
    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;

    
    public EnemyCharacterView(Vector2 initialPosition) throws InterruptedException, ExecutionException {
        super(initialPosition, 20f, 5f, 70f, loadTexture(), FRAME_WIDTH, FRAME_HEIGHT);
    }

    private static TextureRegion loadTexture() throws InterruptedException, ExecutionException {
        Texture texture = new Texture(ENEMY_PATH);
        TextureRegion textureFrames = new TextureRegion(texture);
        return textureFrames;
    }

    public EnemyCharacter getEnemy() {
        return enemy;
    }

}
