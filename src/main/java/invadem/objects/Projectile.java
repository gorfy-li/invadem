package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class Projectile extends Entity{
    protected PImage img;
    protected boolean projectileFromTank; //storing the projectile shoot by tank or invader
    protected int hurt; //harm to tank, invader, and barrier

    public Projectile(PImage img, int x, int y, int width, int height,int[] velocity, boolean projectileFromTank){
        super(x, y, width, height, velocity);
        this.img = img;
        this.projectileFromTank = projectileFromTank;
        hurt = 1;
    }

    public int[] getLocation(){return new int[]{x,y};}

    public int[] getSize(){return new int[]{width,height};}

    public int harm(){return hurt;}

    public boolean launchByTank(){return projectileFromTank;}
    // get  the projectile shoot by tank or invader


    public void tick() { //control shooting up or down
        this.x += velocity[0];
        this.y += velocity[1];
    }

    public void draw(PApplet app) {
        app.image(img, x, y, width, height);
        tick();
    }
}
