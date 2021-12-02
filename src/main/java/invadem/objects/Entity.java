package invadem.objects;

import processing.core.PApplet;

public abstract class Entity {
    //an abstract for entities to extends
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int[] velocity;

    public Entity(int x, int y, int width, int height, int[] velocity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
    }

    public abstract int[] getLocation();
    // get current location

    public abstract int[] getSize();
    // get size

    public abstract void draw(PApplet app);
    // draw entities on app
}
