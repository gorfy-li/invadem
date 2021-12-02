package invadem.objects;

import processing.core.PApplet;
import processing.core.PImage;

public class ArmouredInvader extends Invader {
    //create a subclass of Invader which has similar and addition function to Invader
    private int delay; //counting the frame use  for animation
    private boolean harm; //check to change picture
    public ArmouredInvader(PImage[] img, int x, int y, int width, int height){
        super(img, x, y, width, height); //superclass construction
         life = 3; //ArmouredInvader life
         delay = 5;
         harm = false;
    }
    @Override
    public boolean damage(int hit){
        // reduce life of ArmouredInvader and
        //return if ArmouredInvader alive or not
        life -= hit;
        if (life <= 0){
            return true;
        }else{
            harm = true;
            return false;
        }
    }
    @Override
    public void draw(PApplet app){
        //draw ArmouredInvader and changing image
        if (volIndex == 1) {
            if (harm) { //changing image when ArmouredInvader get hurt
                app.image(img[3], x, y, width, height);
                delay--;
                harm = (delay != 0);
                delay = (delay == 0) ? 5 : delay;
            } else {
                app.image(img[1], x, y, width, height);
            }
        }else{
            if (harm) {
                app.image(img[2], x, y, width, height);
                delay--;
                harm = (delay != 0);
                delay = (delay == 0) ? 5 : delay;
            } else {
                app.image(img[0], x, y, width, height);
            }
        }
    }
}
