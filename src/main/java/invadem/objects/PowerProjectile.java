package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class PowerProjectile extends Projectile {
    public PowerProjectile(PImage img, int x, int y, int width, int height) {
        //create a subclass of Projectile which has similar method to Projectile and has a higher harm
        super(img, x, y, width, height, new int[]{0,1}, false);
        hurt = 3;
    }

}
