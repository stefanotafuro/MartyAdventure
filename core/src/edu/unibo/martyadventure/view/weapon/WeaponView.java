package edu.unibo.martyadventure.view.weapon;

import com.badlogic.gdx.graphics.Texture;

import edu.unibo.martyadventure.model.weapon.Weapon;
/**
 * Encapsulate the model weapon into his view representation
 */
public class WeaponView {
    
    private Weapon weapon;
    private Texture weaponTexture;
    
    public WeaponView(Weapon weapon, Texture texture){
        weaponTexture = texture;
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }


    public Texture getWeaponTexture() {
        return weaponTexture;
    }

}
