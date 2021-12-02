/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package invadem;

import invadem.objects.*;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import ddf.minim.Minim;
import ddf.minim.AudioPlayer;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import java.lang.reflect.Field;

public class AppTest extends App{
//    public static App app = new App();
//    public static PImage Projectile = (PImage) setField("Projectile").get(app);
//    public static PImage powerProjectile = (PImage) setField("powerProjectile").get(app);
//    public static PImage gameOver = (PImage) setField("gameOver").get(app);
//    public static PImage nextLevel = (PImage) setField("nextLevel").get(app);
//    public static PImage[] background = (PImage[]) setField("background").get(app);
//    public static PImage[] invader = (PImage[]) setField("invader").get(app);
//    public static PImage[] armouredInvader = (PImage[]) setField("armouredInvader").get(app);
//    public static PImage[] powerInvader = (PImage[]) setField("powerInvader").get(app);
//    public static PImage[] barriers = (PImage[]) setField("barriers").get(app);
//    public static PImage[] tankImage = (PImage[]) setField("tankImage").get(app);
//    public static PImage[] tankImage2 = (PImage[]) setField("tankImage2").get(app);

//    @Before
    public static Field setField(String variable) throws NoSuchFieldException {
        App app = new App();
        Class c = app.getClass();
        Field field = c.getDeclaredField(variable);
        field.setAccessible(true);
        return field;
    }

    public App app = new App();

    @Test
    public void testApp() {
        App app = new App();
        assertNotNull(app);
    }

//    @Test
//    public void testSetup() throws NoSuchFieldException, IllegalAccessException,NullPointerException {
//        App app = new App();
//        AudioPlayer bgm = (AudioPlayer) setField("bgm").get(app);
//        AudioPlayer shoot = (AudioPlayer) setField("shoot").get(app);
//        AudioPlayer menuBGM = (AudioPlayer) setField("menuBGM").get(app);
//        AudioPlayer scoreBGM = (AudioPlayer) setField("scoreBGM").get(app);
//        AudioPlayer EliteModeBGM = (AudioPlayer) setField("EliteModeBGM").get(app);
//        AudioPlayer damage = (AudioPlayer) setField("damage").get(app);
//        AudioPlayer hurt = (AudioPlayer) setField("hurt").get(app);
//        AudioPlayer select = (AudioPlayer) setField("select").get(app);
//        AudioPlayer enter = (AudioPlayer) setField("enter").get(app);
//        AudioPlayer levelUpBGM = (AudioPlayer) setField("levelUpBGM").get(app);
//        AudioPlayer lose = (AudioPlayer) setField("lose").get(app);
//        PFont level = (PFont) setField("level").get(app);
//        PFont score = (PFont) setField("score").get(app);
//
//        try {
//            app.setup();
//
//        }catch(NullPointerException e){
//            System.out.println("Error: "+e);
//        }
//        assertNotNull(bgm);
//
//
//
//
//
//        PImage p = (PImage) setField("powerProjectile").get(app);
//        Assert.assertNotNull(p);
//        PImage[] p = null;
//        p = (PImage[]) setField("background").get(app);
//        for(PImage i : p){
//            assertNotNull(i);
//        }
//
//            assertNotNull(setField("invader").get(app));
//    }


//    @Test
//    public void testInvaderFire() throws NoSuchFieldException, IllegalAccessException, IndexOutOfBoundsException {
//
//        app.loading();
//        ArrayList projectile = new ArrayList();
//        app.invaderFire(1);
//        assert projectile.size() != 0;
//
//    }

    @Test
    public void testAppSettings() {
        App app = new App();
        try{
            app.settings();
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
    }

    @Test
    public void testCheckCollection() throws NoSuchFieldException, IllegalAccessException {
        App app = new App();
        PImage p = (PImage) setField("powerProjectile").get(app);
        PowerProjectile a = new PowerProjectile(p, 100, 200, 2, 6);
        PowerProjectile b = new PowerProjectile(p, 101, 203, 2, 6);
        PowerProjectile pp = new PowerProjectile(p, 50, 100, 2, 6);

        assertTrue(App.checkCollection(a, b));
        assertFalse(App.checkCollection(a, pp));
    }


}
