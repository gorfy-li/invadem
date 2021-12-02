package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class Invader extends Entity{
    //protected for special invader to extends
    protected PImage[] img;
    protected int[][] vol; //using to store velocity
    protected int life;
    protected int stepCount; //control when to chang direction
    protected int volIndex;//choosing velocity in "vol" to change direction

    public Invader(PImage[] img, int x, int y, int width, int height){
        super(x, y, width, height, new int[]{1,0});
        this.img = img;
        vol = new int[][]{{1,0}, {0,1}, {-1,0}};
        life = 1;
        stepCount = 0;
        volIndex = 0;

    }

    public int[] getLocation(){return new int[]{x,y};}

    public int[] getSize(){return new int[]{width,height};}

    public boolean damage(int hit){
        // reduce life of Invader and
        //return if Invader alive or not
        life -= hit;
        return life <= 0;
    }

    public void tick() {
        // control how invader move
        if ((stepCount >= 30 && stepCount < 38 )||(stepCount >= 68 && stepCount < 75)){
            volIndex = 1; //moving down
            if (stepCount == 74) { stepCount = 0; }
        }else if (stepCount >= 38 && stepCount < 68 ){
            volIndex = 2; //moving left
        }else {
            volIndex = 0; //moving right
        }
        velocity = vol[volIndex];
        this.x += velocity[0];
        this.y += velocity[1];
        stepCount++;
    }


    public void draw(PApplet app) {
        // draw and control animation
        if (volIndex == 1) {
            app.image(img[1], x, y, width, height);
        }else{
            app.image(img[0], x, y, width, height);
        }
    }
}
