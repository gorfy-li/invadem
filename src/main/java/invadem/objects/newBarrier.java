package invadem.objects;

import invadem.App;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

public class newBarrier extends Entity {
    //a class for combine barrier component to a complete barrier
    private  List<Barrier>  barriers; // store barrier component
    private PImage[] top; //image for different part of component
    private PImage[] right;
    private PImage[] left;
    private PImage[] solid;

    public newBarrier (PImage[] img,int x, int y, int width, int height){
        super(x, y, width, height, new int[]{0,0});
        barriers = new ArrayList<>();
        top = new PImage[]{img[0], img[4], img[8], img[12]};
        right = new PImage[]{img[3], img[7], img[11], img[12]};
        left = new PImage[]{img[2], img[6], img[10], img[12]};
        solid = new PImage[]{img[1], img[5], img[9], img[12]};
    }

    public void build(){
        //combine barrier component to a complete barrier
        barriers.add(new Barrier(top, x, y, width, height));
        barriers.add( new Barrier(left, x-width, y, width, height));
        barriers.add(new Barrier(solid, x-width, y+height, width, height));
        barriers.add(new Barrier(solid, x-width, y+height*2, width, height));
        barriers.add(new Barrier(right, x+width, y, width, height));
        barriers.add(new Barrier(solid, x+width, y+height, width, height));
        barriers.add(new Barrier(solid, x+width, y+height*2, width, height));
    }


    public int[] getLocation(){return new int[]{x-width,y};}

    public int[] getSize(){return new int[]{width*3,height*3};}

    public boolean damage(Projectile p){
        //check if the projectile destroy a barrier component
        //return a boolean for if a projectile hit this barrier component
        for (Barrier b : barriers) {
            if (App.checkCollection(p, b)) {
                if (b.damage(p.harm())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public void draw(PApplet app) {
        // draw the complete barrier
        if (barriers.size()==0){
            build();
        }for (Barrier b : barriers){
            b.draw(app);
        }

    }
}
