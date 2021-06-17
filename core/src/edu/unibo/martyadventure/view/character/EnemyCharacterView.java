package edu.unibo.martyadventure.view.character;

import java.util.concurrent.ExecutionException;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import edu.unibo.martyadventure.model.character.EnemyCharacter;
import edu.unibo.martyadventure.view.Toolbox;

public class EnemyCharacterView extends CharacterView {

    public static final int FRAME_WIDTH = 140;
    public static final int FRAME_HEIGHT = 148;

    private EnemyCharacter enemy;

    public EnemyCharacterView(Vector2 initialPosition, String enemy_path, EnemyCharacter enemy)
            throws InterruptedException, ExecutionException {
        super(initialPosition, 20f, 5f, 70f, new TextureRegion(Toolbox.getTexture(enemy_path)), FRAME_WIDTH, FRAME_HEIGHT);
        this.enemy = enemy;
    }

    public EnemyCharacter getEnemy() {
        return enemy;
    }
}
