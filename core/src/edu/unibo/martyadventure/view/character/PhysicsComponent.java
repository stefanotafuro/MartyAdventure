package edu.unibo.martyadventure.view.character;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class PhysicsComponent {

    private final MapLayer collisionLayer;

    public PhysicsComponent(final MapLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    public boolean collide(final Rectangle box) {
        for (MapObject obj : this.collisionLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                if (box.overlaps(((RectangleMapObject)obj).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }
}
