/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import java.util.ArrayList;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 *
 * @author Jan
 */
public class Board {

    private static StackPane root = GalaxyWars.root;
    static Random random = new Random();
    public static Rocket player = new Rocket("images/rocketImage.png");
    public static ImageView[] backgrounds = new ImageView[4];
    //0 start 1 info 2 konec 3 mainmenuBackground
    public static ImageView[] infoStartKonecBoard = new ImageView[4];

    //nejake promenne
    //pro pocitani na vystrel
    private static int vystrel = 0;
    //pocitani pro nepritele
    private static int enemy = 0;
    //pocitani pro pohyb nepratel
    private static int enemyMove = 0;
    //pro kazdych 1.5 sekund pokud Settings.fps = 15;
    private static int highterPeriod = 0;
    //pro vytvareni bonusu, pricita se pouze pokud se pohybujeme vpred
    private static int bonus = 0;
    //pro nabijeni posunu do stran pro jeden z peti
    private static int nabijPobyb = 0;
    //kolikrat se pri zmacknuti a nebo d posune raketa do strany
    private static int siteIntMove = 0;
    //pouziva se na stit ve vlakne
    private static int stitDelka = 0;
    //progressbar na pouzivani rychleho pohybu do leva nebo do prava
    public static ImageView progressBar;
    public static Rectangle rectangleProgressBar;
    //ukazatele zdravi
    public static ImageView helthBar;
    public static Rectangle helthBarRectangle;
    public static Text helthText;

    //stit obrazek, ukaze se pouze nachvili pokud ho najdeme
    public static ImageView shieldImage;

    //staticky konstruktor, inicializace pri spusteni hry
    public static void inicializator() {
        for (int i = 0; i < backgrounds.length; i++) {
            backgrounds[i] = new ImageView("images/backgroundSpace.png");
            root.getChildren().add(backgrounds[i]);
        }
        backgrounds[1].setTranslateX(Settings.SCREEN_WIDTH);
        backgrounds[2].setTranslateY(Settings.SCREEN_HEIGHT);
        backgrounds[3].setTranslateX(Settings.SCREEN_WIDTH);
        backgrounds[3].setTranslateY(Settings.SCREEN_HEIGHT);
        root.getChildren().add(player);
        createMenu();
    }
    //je aktivni pokud je aktivni hlavni nabidka pouziva se pro handlary na nabidku
    public static boolean menuActive = true;

    public static void createMenu() {
        menuActive = true;
        progressBar = new ImageView("images/edge.png");
        progressBar.setTranslateX(-Settings.SCREEN_WIDTH / 2 + 120);
        progressBar.setTranslateY(-Settings.SCREEN_HEIGHT / 2 + 40);
        progressBar.setVisible(false);
        root.getChildren().add(progressBar);
        rectangleProgressBar = new Rectangle();
        rectangleProgressBar.setTranslateX(progressBar.getTranslateX() - 80);
        rectangleProgressBar.setTranslateY(progressBar.getTranslateY());
        rectangleProgressBar.setFill(Color.GREEN);
        rectangleProgressBar.setWidth(0);
        rectangleProgressBar.setHeight(10);
        root.getChildren().add(rectangleProgressBar);

        shieldImage = new ImageView("images/shield.png");
        shieldImage.setVisible(false);
        root.getChildren().add(shieldImage);

        infoStartKonecBoard[3] = new ImageView("images/background.png");
        infoStartKonecBoard[3].setTranslateX(50);
        root.getChildren().add(infoStartKonecBoard[3]);

        infoStartKonecBoard[0] = new ImageView("images/start.png");
        root.getChildren().add(infoStartKonecBoard[0]);
        infoStartKonecBoard[0].setTranslateY(-200);
        infoStartKonecBoard[0].setTranslateX(-370);

        infoStartKonecBoard[1] = new ImageView("images/info.png");
        root.getChildren().add(infoStartKonecBoard[1]);
        infoStartKonecBoard[1].setTranslateX(-370);

        infoStartKonecBoard[2] = new ImageView("images/konec.png");
        root.getChildren().add(infoStartKonecBoard[2]);
        infoStartKonecBoard[2].setTranslateY(200);
        infoStartKonecBoard[2].setTranslateX(-370);

        helthBar = new ImageView("images/helthBar.png");
        helthBar.setTranslateY(300);
        helthBar.setVisible(false);
        root.getChildren().add(helthBar);
        helthBarRectangle = new Rectangle(760, 50, Color.GREENYELLOW);
        helthBarRectangle.setTranslateY(300);
        helthBarRectangle.setVisible(false);
        root.getChildren().add(helthBarRectangle);
        helthText = new Text(Settings.health + "");
        helthText.setFont(Font.font(30));
        helthText.setTranslateY(helthBar.getTranslateY());
        helthText.setFill(Color.GREEN);
        helthText.setVisible(false);
        root.getChildren().add(helthText);
    }
    //promenne pouzivane pro otaceni rakety
    private static com.sun.glass.ui.Robot robot = com.sun.glass.ui.Application.GetApplication().createRobot();
    private static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    //metoda, ktera se zavola kdyz se zmeni cas
    public static void timedChanged() {
        //otoceni rakety
        double mouseXCoordinates = robot.getMouseX() - primaryScreenBounds.getWidth() / 2;
        double mouseYCoordinates = robot.getMouseY() - primaryScreenBounds.getHeight() / 2;
        if (mouseXCoordinates > 0) {
            player.rotate(Math.toDegrees(Math.atan(mouseYCoordinates / mouseXCoordinates)) + 90);
        } else {
            player.rotate(Math.toDegrees(Math.atan(mouseYCoordinates / mouseXCoordinates)) - 90);
        }

        //kazdy delsi usek ted 1.5
        if (++highterPeriod >= 100) {
            highterPeriod = 0;
            Enemy.deleteAllDestroyedEnemies();
        }
        //casovac co se bude dit
        if (KeyboardSettings.leftKeyPressed) {
            player.siteKeyPressed(-1);
        }
        if (KeyboardSettings.rightKeyPressed) {
            player.siteKeyPressed(1);
        }
        if (KeyboardSettings.firePressed) {
            if (++vystrel >= Settings.vystrelDoba) {
                vystrel = 0;
                player.attack(true);
            }
        }
        if (KeyboardSettings.forwardPressed) {
            player.forwardPressed();
        }
        if (KeyboardSettings.backwardPressed) {
            //player.backwardPressed();
            if (++vystrel >= Settings.vystrelDoba) {
                vystrel = 0;
                player.attack(false);
            }
        }
        //stit vybijeni
        if (Settings.aktivniBonus != null && ++stitDelka >= Settings.bonusTrvaniDoba) {
            shieldImage.setVisible(false);//vypne stit
            stitDelka = 0;
            Settings.movingSpeed = Settings.aktivniBonus == Special.SHIELD ? Settings.movingSpeed/2 : Settings.movingSpeed;

            player.setImage(new Image("images/rocketImage.png")); //vypne kulomet
            Settings.vystrelDoba = Settings.aktivniBonus == Special.MACHINE_GUN ? Settings.vystrelDoba*2 : Settings.vystrelDoba;

            helthText.setText(Settings.health + "");

            Settings.aktivniBonus = null;
        }
        //nabijeni pohybu do stran
        if (++nabijPobyb >= Settings.nabijPohybDoba) {
            if (rectangleProgressBar.getWidth() >= 160) {
                Settings.naibtoPohyb = true;
                rectangleProgressBar.setHeight(40);
            } else {
                nabijPobyb = 0;
                rectangleProgressBar.setWidth(rectangleProgressBar.getWidth() + 160 / 5);
                rectangleProgressBar.setTranslateX(rectangleProgressBar.getTranslateX() + 80 / 5);
            }

        }
        //pohyb do stran ANIMACE
        if (Settings.nowInSideMove && ++siteIntMove < Settings.pocetPohybuDoStrany) {
            player.movingAnimation();
        } else {
            siteIntMove = 0;
            Settings.nowInSideMove = false;
        }
        //vytvareni nepritele
        if (++enemy >= Settings.enemySpawnDoba) {
            enemy = 0;
            Enemy newEnemy = new Enemy("images/enemy.png");
            if (random.nextInt(Settings.bonusVytvoreniDoba) == 0) {
                newEnemy.setSpecial(Special.values()[random.nextInt(Special.values().length)]);
            }
        }
        //pohyb naboje ------------------------------------------------
        Bullet.pohybVsechNaboju();
        //pohyb nepratel------------------------------------------------
        if (++enemyMove >= Settings.enemyMoveDoba) {
            enemyMove = 0;
            Enemy.moveAllEnemies();
        }
    }

    //metoda pro pocitani kolizi vraci true pokud se dotykaji
    public static boolean checkCollision(ImageView image1, ImageView image2) {
        if (image1.getBoundsInParent().intersects(image2.getBoundsInParent())) {
            return true;
        }
        return false;
    }

    //metoda pro nepratele dostat se do stredu
    public static boolean mitCollision(ImageView image) { // rada to zakomentovane je hotove rucne udelane urcovani kolizi

        final double vzdalenostDotyku = 100; //na kolik pixelu musi byt velka vzdalenost nepritele od stredu aby se nedotykal
        /*if ((image.getTranslateX() + image.getImage().getWidth() > player.getTranslateX() && player.getTranslateX() + player.getImage().getWidth() > player.getTranslateX()) &&
            (image.getTranslateY() + image.getImage().getHeight() > player.getTranslateY() && player.getTranslateY() + player.getImage().getHeight()> player.getTranslateY())) {
            return true;
        }*/
        if (image.getTranslateX() < vzdalenostDotyku && image.getTranslateX() > -vzdalenostDotyku && image.getTranslateY() < vzdalenostDotyku && image.getTranslateY() > -vzdalenostDotyku) {
            return true;
        }
        return false;
    }

    //hra konci
    public static void gameOver() {
        inicializateSettingsValues();
        for (int i = 0; i < Settings.allEnemies.size(); i++) {
            Settings.allEnemies.get(i).setVisible(false);
        }
        Settings.allEnemies = new ArrayList<>();
        for (int i = 0; i < Settings.allBonuses.size(); i++) {
            Settings.allBonuses.get(i).setVisible(false);
        }
        Settings.allBonuses = new ArrayList<>();
        ImageView gameOverImageView = new ImageView("images/gameOver.png");
        root.getChildren().add(gameOverImageView);
        Settings.hraBezi = false;

        gameOverImageView.setOnMouseClicked((MouseEvent event) -> {
            gameOverImageView.setVisible(false);
            createMenu();
        });
    }

    //inicializate settings values
    public static void inicializateSettingsValues() {
        Settings.aktivniBonus = null;
        Settings.naibtoPohyb = false;
        Settings.health = 100; //100 zivotu

        //kazdy tento usek se pricita po 15ms (neboli fps) bonus doba se pricita pouze pri pohybu
        Settings.movingSpeed = 1.3; //timto cislem nasobime velocity ve tride Rocket
        Settings.vystrelDoba = 55;
        Settings.enemySpawnDoba = 100;
        Settings.enemyMoveDoba = 3;
        Settings.nabijPohybDoba = 120;// jeden z peti kousku
        Settings.bonusVytvoreniDoba = 10; //random int rozhoduje if(random.nextInt(bonusVytvoreniDoba) == 0
        Settings.bonusTrvaniDoba = 1400;
        Settings.pocetPohybuDoStrany = 200;
        Settings.nowInSideMove = false;
    }
}
