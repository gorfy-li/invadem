package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class Tank extends Entity{
    private PImage[] img;
    private int life;
    private int aniIndex; //use for control animation
    private int delay; //counting the frame use for animation
    private boolean fire; //can shoot or not
    private boolean right; //moving right or not
    private boolean left; //moving left or not

    public Tank (PImage[] img, int x, int y, int width, int height){
        super(x, y, width, height, new int[]{0,0});
        this.img = img;
        life = 3;
        aniIndex = 0;
        delay = 5;
        fire = false;
        left = false;
        right = false;
    }

    // getter and setter of left and right
    public boolean getLeft(){return left;}

    public boolean getRight(){return right;}

    public void setLeft(boolean left){this.left = left;}

    public void setRight(boolean right){this.right = right;}

    public int[] getLocation(){return new int[]{x,y};}

    public int[] getSize(){return new int[]{width,height};}

    public int getLife(){return life;} // getter of life

    public boolean canFire(){return fire;} //get should tank fire or not

    public void isFire(boolean fire){this.fire = fire;} // set tank fire or not

    public void move(){ //control direction of tank moving
        if (right){
            velocity = new int[]{1, 0};
        }else if (left){
            velocity = new int[]{-1, 0};

        }else {
            velocity = new int[]{0, 0};
        }
    }

    public boolean damage(int hurt){
        //reduce the life and control the animation by the harm of projectile
        //return a boolean of if tank still alive or not
        life-=hurt;
        aniIndex = 1;
        if(life <= 0){
            life = 0;
            return true;
        }
        return false;
    }

    public void tick() {
        // control moving
        this.x += velocity[0];
        if (x>460) {
            this.x = 460;
        }if (x<180){
            this.x = 180;
        }
    }

    public void draw(PApplet app) {
        // draw and control animation
        app.image(img[aniIndex], x, y, width, height);
        if (aniIndex == 1){
            delay--;
            aniIndex = (delay == 0) ? 0 : 1;
            delay = (delay == 0) ? 5 : delay;
        }
        tick();
    }

}
