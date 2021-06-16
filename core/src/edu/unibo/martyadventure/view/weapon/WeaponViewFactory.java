package edu.unibo.martyadventure.view.weapon;

import com.badlogic.gdx.graphics.Texture;

import edu.unibo.martyadventure.model.weapon.WeaponFactory;
import edu.unibo.martyadventure.view.MapManager;

public class WeaponViewFactory {
    
    public enum Weapons{
        BASEBALL_BAT ("Weapons/baseballBat.png", "Mazza da baseball"), 
        BRASS_KNUCKLES ("Weapons/BrassKnuckles.png", "Tirapugni"), 
        CROWBAR ("Weapons/crowbar.png", "Piede di porco"), 
        HAMMER ("Weapons/hammer.png", "Martello"), 
        KNIFE ("Weapons/knife.png", "Coltello"), 
        REVOLVER ("Weapons/Revolver.png", "Rivoltella");
        
        private final String texturePath;
        private final String weaponName;
        
        private Weapons(String path, String name) {
            texturePath = path;
            weaponName = name;
        }

        public String getTexturePath() {
            return texturePath;
        }

        public String getWeaponName() {
            return weaponName;
        }
    }
    
    private static Weapons getRandomWeapon() {
        return Weapons.values()[(int) (Math.random() * Weapons.values().length)];
    }
    
    public static WeaponView createRandomWeaponView(MapManager.Maps map) {
        Weapons w = getRandomWeapon();
        return new WeaponView(WeaponFactory.createRandomWeaponLevel(w.getWeaponName(), map), new Texture(w.getTexturePath()));
    }
    
    public static WeaponView createPlayerWeaponView() {
        Weapons w = Weapons.CROWBAR;
        return new WeaponView(WeaponFactory.createRandomWeaponLevel(w.getWeaponName(), MapManager.Maps.MAP1), new Texture(w.getTexturePath()));
    }

}
