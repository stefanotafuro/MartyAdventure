package edu.unibo.martyadventure.view.weapon;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;

import edu.unibo.martyadventure.model.weapon.Weapon;
import edu.unibo.martyadventure.model.weapon.WeaponFactory;
import edu.unibo.martyadventure.view.MapManager;

public class WeaponViewFactory {

    public enum Weapons {

        BASEBALL_BAT("Weapons/baseballBat.png", "Mazza da baseball", Weapon.WeaponType.MELEE),
        BRASS_KNUCKLES("Weapons/BrassKnuckles.png", "Tirapugni", Weapon.WeaponType.MELEE),
        CROWBAR("Weapons/crowbar.png", "Piede di porco", Weapon.WeaponType.MELEE),
        HAMMER("Weapons/hammer.png", "Martello", Weapon.WeaponType.MELEE),
        KNIFE("Weapons/knife.png", "Coltello", Weapon.WeaponType.MELEE),
        PUNCH("Weapons/punch.png", "Pugno", Weapon.WeaponType.MELEE),
        REVOLVER("Weapons/Revolver.png", "Rivoltella", Weapon.WeaponType.RANGED);


        private final String texturePath;
        private final String weaponName;
        private final Weapon.WeaponType type;


        private Weapons(String path, String name, Weapon.WeaponType type) {
            texturePath = path;
            weaponName = name;
            this.type = type;
        }

        public String getTexturePath() {
            return texturePath;
        }

        public String getWeaponName() {
            return weaponName;
        }

        public Weapon.WeaponType getType() {
            return type;
        }
    }


    private static Weapons getRandomWeapon() {
        return Weapons.values()[ThreadLocalRandom.current().nextInt(Weapons.values().length)];
    }

    public static WeaponView createRandomWeaponView(MapManager.Maps map) {
        Weapons w = getRandomWeapon();
        return new WeaponView(WeaponFactory.createRandomWeaponLevel(w.getWeaponName(), map, w.getType()),
                new Texture(w.getTexturePath()));
    }

    public static WeaponView createPlayerWeaponView() {
        Weapons w = Weapons.PUNCH;
        return new WeaponView(
                WeaponFactory.createRandomWeaponLevel(w.getWeaponName(), MapManager.Maps.MAP1, w.getType()),
                new Texture(w.getTexturePath()));
    }
}
