package invadem;


import invadem.objects.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import ddf.minim.Minim;
import ddf.minim.AudioPlayer;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;

public class App extends PApplet {
    private Tank tank;  //first player
    private Tank tank2; //second player
    private Entity finished; //print next level or game over
    private List<Entity> entities; //store all entities and draw
    private List<Tank> tanks;
    private List<newBarrier> bar;
    private List<Invader> inv;
    private List<Projectile> fire;

    private  AudioPlayer bgm; //store sounds effect
    private  AudioPlayer shoot;
    private  AudioPlayer menuBGM;
    private  AudioPlayer scoreBGM;
    private  AudioPlayer EliteModeBGM;
    private  AudioPlayer damage;
    private  AudioPlayer hurt;
    private  AudioPlayer select;
    private  AudioPlayer enter;
    private  AudioPlayer levelUpBGM;
    private  AudioPlayer lose;
    private  PFont level; //store words type
    private  PFont score; //store words type
    private  PImage Projectile; //image for entities
    private  PImage powerProjectile;
    private  PImage gameOver;
    private  PImage nextLevel;
    private  PImage[] background;
    private  PImage[] invader;
    private  PImage[] armouredInvader;
    private  PImage[] powerInvader;
    private  PImage[] barriers;
    private  PImage[] tankImage;
    private  PImage[] tankImage2;
    private  AudioPlayer[] gameBGM;


    private static boolean twoPlayers = false; //store game mode
    private static boolean EliteMode = false; //change speed of invader shooting
    private static boolean star = false; //store if game star or not
    private static boolean spaceReleased = true;
    //store current space status to let tank 1 shoot only one time
    private static boolean fReleased = true;
    //store current F status to let tank 2 shoot once
    private static boolean completed = false; //control time to print next level or game over
    private static boolean levelUp = false; //next level or game over
    private static int delay = 180;
    //counting frame to let next level or game over stay for 3 seconds on screen
    private static int currentLevel = 1; //store relate data
    private static int currentScore = 0;
    private static int highestScore = 10000;


    public App() {
        tank = null;
        tank2 = null;
        finished = null;
        tanks = new ArrayList<>();
        entities = new ArrayList<>();
        bar = new ArrayList<>();
        inv = new ArrayList<>();
        fire = new ArrayList<>();
    }

    public void setup() {
        // loading all images, sounds effect, and text types in, setting frame rate
        frameRate(60);
        background(0);
        background = new PImage[] {loadImage("background.jpg"), loadImage("background2.jpg")};
        invader = new PImage[]{loadImage("invader1.png"), loadImage("invader2.png")};
        armouredInvader = new PImage[]{loadImage("invader1_armoured.png"), loadImage("invader2_armoured.png"),
                            loadImage("invader1_armoured_Red.png"), loadImage("invader2_armoured_Red.png")};
        powerInvader = new PImage[]{loadImage("invader1_power.png"), loadImage("invader2_power.png")};
        barriers = new PImage[]{loadImage("barrier_top1.png"), loadImage("barrier_solid1.png"),
                loadImage("barrier_left1.png"), loadImage("barrier_right1.png"),
                loadImage("barrier_top2.png"), loadImage("barrier_solid2.png"),
                loadImage("barrier_left2.png"), loadImage("barrier_right2.png"),
                loadImage("barrier_top3.png"), loadImage("barrier_solid3.png"),
                loadImage("barrier_left3.png"), loadImage("barrier_right3.png"),
                loadImage("empty.png")};
        tankImage = new PImage[] {loadImage("tank1.png"), loadImage("tankRed.png")};
        tankImage2 = new PImage[] {loadImage("tank2.png"), loadImage("tankRed.png")};
        Projectile = loadImage("projectile.png");
        powerProjectile = loadImage("projectile_lg.png");
        gameOver = loadImage("gameover.png");
        nextLevel = loadImage("nextlevel.png");
        score = createFont("PressStart2P-Regular.ttf",20);
        level = createFont("estensil.ttf", 30);
        Minim minim = new Minim (this);
        bgm = minim.loadFile("bgm.mp3");
        EliteModeBGM = minim.loadFile("elitemode.mp3");
        shoot = minim.loadFile("shoot.mp3");
        scoreBGM = minim.loadFile("hit.mp3");
        hurt = minim.loadFile("hurt.mp3");
        damage = minim.loadFile("damage.mp3");
        enter = minim.loadFile("enter.mp3");
        select = minim.loadFile("select.mp3");
        menuBGM = minim.loadFile("menu_bgm.mp3");
        levelUpBGM =  minim.loadFile("levelup.mp3");
        lose = minim.loadFile("lose.mp3");
        gameBGM = new AudioPlayer[]{shoot, scoreBGM, bgm, EliteModeBGM, hurt, damage, levelUpBGM, lose};
        //store all sounds effect in game into a array and stop them while player get back to menu
    }

    public void settings() { //game window size
        size(640, 480);
    }

    public void loading(){
        // initialize variable
        // construct and store entities
        finished = null;
        entities = new ArrayList<>();
        tanks = new ArrayList<>();
        bar = new ArrayList<>();
        inv = new ArrayList<>();
        fire = new ArrayList<>();
        if (!twoPlayers) {
            tank = new Tank(tankImage, 320-22/2, 450, 22, 16);
            tank2 = null;
            tanks.add(tank);
            entities.addAll(tanks);
        }else{
            // create two tank in two player mode
            tank = new Tank(tankImage, 280-22/2, 450, 22, 16);
            tanks.add(tank);
            tank2 = new Tank(tankImage2, 380-22/2, 450, 22, 16);
            tanks.add(tank2);
            entities.addAll(tanks);
        }
        bar.add(new newBarrier(barriers, 317,410,8,8));
        bar.add(new newBarrier(barriers, 200,410,8,8));
        bar.add(new newBarrier(barriers, 440,410,8,8));
        entities.addAll(bar);
        for (int i = 0; i <4; i++){
            for (int j = 0; j < 10; j++){
                if (i == 0){
                    inv.add(new ArmouredInvader(armouredInvader, 184+j*30, 80, 16, 16));
                }else if (i == 1){
                    inv.add(new PowerInvader(powerInvader, 184+j*30, 80+i*34, 16, 16));
                }else {
                    inv.add(new Invader(invader, 184 + j * 30, 80 + i * 34, 16, 16));
                }
            }
        }

        entities.addAll(inv);
        frameCount = 0; //reset frame for control invader shooting
    }


    @Override
    public void keyPressed(){
        //identify which key was pressed and executive command
        if (keyCode == 37 && tank != null){
            //if player pressed left and did not pressed right at the same time
            //tank will move left
               tank.setLeft(!tank.getRight());
        }if (keyCode == 39 && tank != null){
            tank.setRight(!tank.getLeft());
        }
        if (keyCode == 32 && tank != null) {
            if (spaceReleased) {
                // player pressed space once and tank will fire once
                //before player released space and pressed again
                tank.isFire(true);
                spaceReleased = false;
            }
        }
        if (keyCode == 65 && tank2 != null){
            tank2.setLeft(!tank2.getRight());
        }
        if (keyCode == 68 && tank2 != null){
            tank2.setRight(!tank2.getLeft());
        }
        if (keyCode == 70 && tank2 != null) {
            if (fReleased) {
                tank2.isFire(true);
                fReleased = false;
            }
        }
        if (keyCode == 10 && !star){
            // enter and load the game
            //changing bgm
            star = true;
            stopBGM(menuBGM);
            enter.rewind(); //adding sounds effect
            enter.play();
            loading();
        }if (keyCode == 38 && !star){
            // choose mode
            twoPlayers = false;
            select.rewind();//adding sounds effect
            select.play();
        }if (keyCode == 40 && !star){
            twoPlayers = true;
            select.rewind();
            select.play();
        }if (keyCode == 8 && star){
            // get back to menu and reset
            //stop all sounds effect in games
            for (AudioPlayer a: gameBGM){
                stopBGM(a);
            }
            select.rewind();
            select.play();
            star = false; //reset
            EliteMode = false;
            completed = false;
            levelUp = false;
            delay = 180;
            currentLevel = 1;
            currentScore = 0;
        }if (keyCode == 83 && star){
            //enter elite mode or get back to normal
            EliteMode = !EliteMode;
            select.rewind();
            select.play();
        }
    }


    @Override
    public void keyReleased(){
        //identify which key was released and executive command
        if (keyCode == 37 && tank != null){
            // stop moving left
            tank.setLeft(false);
        }if (keyCode == 39 && tank != null){
            tank.setRight(false);
        }if (keyCode == 32 && tank != null){
            // reset space status, press space can fire again
            spaceReleased = true;
        }if (keyCode == 65 && tank2 != null){
            tank2.setLeft(false);
        }if (keyCode == 68 && tank2 != null){
            tank2.setRight(false);
        }if (keyCode == 70 && tank2 != null){
            fReleased = true;
        }
    }

    public void moveTank(){
        // move the tank
        for (Tank t : tanks){
            t.move();
        }
    }

    public void tankFired(){
        //construct a projectile at tank current location if tank can shoot
        for (Tank t : tanks) {
            if (t.canFire()) {
                Projectile p = new Projectile(Projectile, t.getLocation()[0] + t.getSize()[0] / 2,
                        t.getLocation()[1], 1, 3, new int[]{0, -1}, true);
                fire.add(p);
                entities.add(p);
                shoot.rewind(); //adding sound effect
                shoot.play();
                t.isFire(false);
            }
        }
    }

    public void invaderFire(int level){
        //randomly choose a invader and construct a projectile at its current location
        //in normal level, invader shoot one projectile per 5 seconds at star
        // which will decrease 1 second per level and up to 1 projectile per second
        //in elite mode invader shoot 6 projectile per second
        boolean shoot;
        if (EliteMode){
            shoot = (frameCount % 10 == 0 || frameCount == 1);
            //shoot 6 projectile per second
        }else{
            int time = (60 *(5 - level + 1) >= 60) ? 60 *(5 - level + 1) : 60;
            shoot = (frameCount % time == 0 || frameCount == 1);
            //invader shoot one projectile per 5 seconds at star, level 5 will reach 1 shoot per second
        }
        if (shoot){
            int shooter = (int) random(0, inv.size()); //randomly choose a invader
            if (inv.get(shooter) instanceof PowerInvader){
                PowerProjectile p = new PowerProjectile(powerProjectile, inv.get(shooter).getLocation()[0] + inv.get(shooter).getSize()[0] / 2,
                        inv.get(shooter).getLocation()[1], 2, 5);
                fire.add(p);
                entities.add(p);
            }else {
                Projectile p = new Projectile(Projectile, inv.get(shooter).getLocation()[0] + inv.get(shooter).getSize()[0] / 2,
                        inv.get(shooter).getLocation()[1], 1, 3, new int[]{0, 1}, false);
                fire.add(p);
                entities.add(p);
            }

        }
    }

    public void projectileExtinct(){
        // projectile will extinct when it move out of the game
        List<Projectile> extinctedProjectile = new ArrayList<>();
        for(Projectile p :fire) {
            int y = p.getLocation()[1];
            if (y<20 && p.launchByTank()){
                extinctedProjectile.add(p);
            }else if (y>460 && !p.launchByTank()){
                extinctedProjectile.add(p);
            }
        }fire.removeAll(extinctedProjectile);
        entities.removeAll(extinctedProjectile);
    }

    public void invAnimation(){
        // let invader move one step per 2 frame
        if(frameCount%2==0){
            for (Invader i : inv){
                i.tick();
            }
        }
    }

    public void invCollision(){
        //if tank shoot invader
        List<Projectile> extinctedProjectile = new ArrayList<>();
        List<Invader> extinctedInvader = new ArrayList<>();
        for (Projectile p : fire){
            for (Invader i : inv){
                if (checkCollection(p, i) && p.launchByTank()){
                    //check if projectile overlap with invader and if the projectile shoot by tank
                    extinctedProjectile.add(p);
                    if (i.damage(p.harm())) {
                        extinctedInvader.add(i);
                        scoreBGM.rewind(); //adding sounds effect
                        scoreBGM.play();
                    }else{
                        hurt.rewind(); // adding sounds effect
                        hurt.play();
                    }
                }
            }
        }fire.removeAll(extinctedProjectile);
        entities.removeAll(extinctedProjectile);
        List<Invader> delSame = new ArrayList<>();
        //deleting the repeating invaders in list in order to solve
        //the extra scores when two projectiles shoot on one invader
        for (Invader i : extinctedInvader){
            if (!delSame.contains(i)){
                delSame.add(i);
            }
        }
        for (Invader i : delSame){
            currentScore += 100;
            currentScore = (i instanceof PowerInvader || i instanceof ArmouredInvader) ?
                    currentScore + 150 : currentScore;
            // extra score for special invader
        }
        inv.removeAll(delSame);
        entities.removeAll(delSame);
    }

    public void barCollision(){
        //if tank or invader shoot barrier
        List<Projectile> extinctedProjectile = new ArrayList<>();
        for (Projectile p : fire){
            for (newBarrier b : bar){
                if (checkCollection(p, b) && b.damage(p)){
                    // check if projectile overlap with barrier and barrier is exist
                    extinctedProjectile.add(p);
                }
            }
        }fire.removeAll(extinctedProjectile);
        entities.removeAll(extinctedProjectile);
    }

    public void setFinished(PImage image){
        //helping method for construct next level and game over
        finished = new Entity(320 - 200 / 2, 240 - 60 / 2, 200, 60, new int[]{0, 0}) {
            public int[] getLocation() {return new int[]{x, y};}
            public int[] getSize() {return new int[]{width, height};}
            public void draw(PApplet app) {app.image(image, x, y, width, height);}
        };
        completed = true;
    }

    public void tankCollision(){
        //if invader shoot tank
        List<Projectile> extinctedProjectile = new ArrayList<>();
        List<Tank> extinctedTank = new ArrayList<>();
        for (Projectile p : fire){
            for (Tank t : tanks){
                if (!p.launchByTank() && checkCollection(p, t)) {
                    //check if projectile overlap with tank and if the projectile shoot by invader
                    extinctedProjectile.add(p);
                    damage.rewind(); // adding sounds effect
                    damage.play();
                    if (t.damage(p.harm())) {
                        //check if tank still alive
                        extinctedTank.add(t);
                    }
                }
            }
        }tanks.removeAll(extinctedTank);
        entities.removeAll(extinctedTank);
        fire.removeAll(extinctedProjectile);
        entities.removeAll(extinctedProjectile);
        if (tanks.size() == 0) {
            //if all tanks extinct, game over
            setFinished(gameOver);
            lose.rewind(); //adding sounds effect
            lose.play();
        }
    }

    public void gameCompleted(){
        if (inv.size()==0){
            //if all invader extinct, next live
            setFinished(nextLevel);
            levelUp = true;
            levelUpBGM.rewind(); //adding sounds effect
            levelUpBGM.play();
        }else{ //if invader reach barrier, game over
            for (Invader i : inv){
                int y = i.getLocation()[1];
                int height = i.getSize()[1];
                int barLocation = bar.get(0).getLocation()[1];
                if (barLocation <= y + height + 10){
                    setFinished(gameOver);
                    lose.rewind(); //adding sounds effect
                    lose.play();
                }
            }
        }
    }
    public void starGame(){
        // text and image in menu and choose mode
        textFont(level, 60);
        text("INVADEM", 170, 150);
        String player = twoPlayers ? "P1     P2":"P1";
        text(player, 150, 400);
        textFont(score, 8);
        text("(press UP and Down to choose mode, press Enter to star)", 90,320);
        PImage t = this.loadImage("tank1.png");
        t.resize(66, 48);
        image(t, 60, 140);
        PImage i = this.loadImage("invader1_power.png");
        i.resize(64, 64);
        image(i, 520, 140);
        PImage a = this.loadImage("invader1_armoured.png");
        a.resize(64, 64);
        image(a, 520, 300);
        if (!twoPlayers){
            textFont(level, 30);
            text("One Player", 230, 230);
            textFont(score, 20);
            text("Two Players", 230, 280);
        }else{
            textFont(score, 20);
            text("One Player", 230, 230);
            textFont(level, 30);
            text("Two Players", 230, 280);
        }
    }

    public void data(){
        // data shows in game
        textFont(level, 30);
        if (EliteMode){
            text("Elite Mode", 200,30);
        }else {
            text("LEVEL " + currentLevel, 250, 30);
        }
        textFont(level, 18);
        text("P1 HEALTH: "+ tank.getLife(), 10,450);
        textFont(score, 8);
        text("P1\npress Left/Right to move\npress Space to shoot",10, 400);
        textFont(score, 9);
        text("press S enter EliteMode\npress again get back",10, 60);
        text("press BACKSPACE\nreturn to menu",500, 60);
        if (twoPlayers){
            textFont(level, 18);
            text("P2 HEALTH: "+ tank2.getLife(), 480,450);
            textFont(score, 8);
            text("P2\npress A/D to move\npress F to shoot",480, 400);
        }
        textFont(score, 12);
        highestScore = Math.max(currentScore, highestScore);
        text("Current Score:\n"+currentScore, 10,20);
        text("Highest Score:\n        "+highestScore, 470,20);
        textFont(level, 15);
        DecimalFormat df = new DecimalFormat(".00");
        String f = df.format(frameRate);
        text("FPS: "+f,10, 120);
    }


    public void draw() {
        // draw all needed entities and text on screen and run the game
        if (!star){
            background(background[1]);
            playBGM(menuBGM, 360); //adding sounds effect
            starGame();
            return;
        }
        background(background[0]);
        if (completed){
            stopBGM(bgm); //stop bgm
            stopBGM(EliteModeBGM);
            finished.draw(this);
            delay--; //show game over or next level for three seconds
            if (delay == 0) {
                if (levelUp){ //star next level
                    currentLevel++;
                    levelUp = false;
                }else{ //reset to level 1
                    currentLevel = 1;
                    currentScore = 0;
                }
                loading();
                completed = false;
                delay = 180;
            }return;
        }
        if (EliteMode){
            //changing bgm
            stopBGM(bgm);
            playBGM(EliteModeBGM, 396);
        }else {
            stopBGM(EliteModeBGM);
            playBGM(bgm, 577);
        }
        data();
        for(Entity e : entities) { //draw all needed entities
            e.draw(this);
        }
        moveTank();
        invAnimation();
        tankFired();
        invaderFire(currentLevel);
        projectileExtinct();
        invCollision();
        barCollision();
        tankCollision();
        gameCompleted();

    }

    public static boolean checkCollection(Entity a, Entity b){
        // helping method to check overlap of two entities and return a boolean
        int aX = a.getLocation()[0];
        int aY = a.getLocation()[1];
        int bX = b.getLocation()[0];
        int bY = b.getLocation()[1];
        int aWidth = a.getSize()[0];
        int aHeight = a.getSize()[1];
        int bWidth = b.getSize()[0];
        int bHeight = b.getSize()[1];
        if ((aX < (bX + bWidth)) && (bX < (aX + aWidth)) &&
                (aY < (bY + bHeight)) && (bY < (aY + aHeight))) {
            return true;
        }
        return false;
    }

    public static void playBGM(AudioPlayer music, int time){
        // helping method for keep playing music
        music.play();
        if (music.position() == (music.length()-time)){
            music.rewind();
        }
    }

    public static void stopBGM(AudioPlayer music){
        // helping method for stop music
        if (music.isPlaying()){
            music.pause();
            music.rewind();
        }
    }

    public static void main(String[] args) {
        PApplet.main("invadem.App");
    }

}
