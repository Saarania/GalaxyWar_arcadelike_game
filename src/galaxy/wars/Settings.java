/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.stage.Screen;

/**
 *
 * @author Jan
 */
public class Settings {
    //public static final int ROTATION_SPEED = 2;
    //public static final int FASTER_ROTATION_SPEED = 6;
    public static final int BORDER_OF_SHOTS = 500;
    public static final Image enemyDeath = new Image("images/enemyDeath.png");
    public static final int SCREEN_WIDTH = /*(int) Screen.getPrimary().getVisualBounds().getWidth()*/ 1366; //pocitame s tim ze je jako na mojem notebooku tudiz 1366
    public static final int SCREEN_HEIGHT =/* (int) Screen.getPrimary().getVisualBounds().getHeight()*/ 768; //639 nejak tak nepamatuju
    public static final int HEALTHBAR_WIDTH = 800;
    
    
    public static Special aktivniBonus = null;
    public static boolean naibtoPohyb = false;
    public static int health = 100; //100 zivotu
    public static boolean hraBezi = false; //nastavi se na true po zapnuti hry a ukonci pri prohre, uspi vlakno
    
    public static ArrayList<Bullet> allBullets = new ArrayList<>();
    public static ArrayList<Enemy> allEnemies = new ArrayList<>();
    public static ArrayList<Bonus> allBonuses = new ArrayList<>();
    
    //rychlost jak se maji pohybovat a celkove casovac opakovat
    public static final int fps = 21;
    //kazdy tento usek se pricita po 15ms (neboli fps) bonus doba se pricita pouze pri pohybu
    public static double movingSpeed = 9.1; //timto cislem nasobime velocity ve tride Rocket
    public static int vystrelDoba = 8;
    public static int enemySpawnDoba = 14;
    public static int enemyMoveDoba = 1;
    public static int nabijPohybDoba = 17;// jeden z peti kousku
    public static int bonusVytvoreniDoba = 20; //random int rozhoduje if(random.nextInt(bonusVytvoreniDoba) == 0
    public static int bonusTrvaniDoba = 200;
    public static int pocetPohybuDoStrany = 29;
    public static boolean nowInSideMove = false;
    
    public static final int RIGHT_LEFT_SPEED = 30; //right left speed * velocity
    public static final int BULLET_SPEED = 3;
    public static final int ENEMY_MOVESPEED = 3;
    
    
}
