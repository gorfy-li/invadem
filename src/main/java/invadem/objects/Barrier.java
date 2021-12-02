package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class Barrier extends Entity {
    private PImage[] img;
    private int life;
    private int aniIndex; //use for control animation
    private PImage ani;


    public Barrier(PImage[] img, int x, int y, int width, int height){
        super(x, y, width, height, new int[]{0,0}); //superclass construction
        this.img = img;
        life = 3;
        aniIndex = 0;
        ani = img[aniIndex];
    }

    public int[] getLocation(){return new int[]{x,y};}

    public int[] getSize(){return new int[]{width,height};}

    public boolean damage(int hurt){
        //reduce the life and control the animation by the harm of projectile
        //return a boolean of if a projectile hit this barrier
        if (life <= 0 && aniIndex == 3){
            return false;
        }else{
            life-=hurt;
            aniIndex = (aniIndex + hurt >= 3) ? 3 : aniIndex+hurt;
            ani = img[aniIndex];
            return true;
        }
    }

    public void draw(PApplet app) {
        app.image(ani, x, y, width, height);
    }
}
