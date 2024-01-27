/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Jan
 */
public class Rocket extends ImageView {

    boolean shiftPressed = false;
    double velocityX;
    double velocityY;

    public Rocket(String url) {
        super(url);
        //nastaveni aby se mohlo s lodi hned hybat
        velocityX = 5;
        velocityY = 5;
    }

    //parametr je normalne true, pokdu strilime zezadu je false
    public void attack(boolean normal) {
        Bullet bullet = new Bullet("images/bullet.png", getRotate(), normal == true ? 1 : -1);
        GalaxyWars.root.getChildren().add(bullet);
        Settings.allBullets.add(bullet);
    }

    public void forwardPressed() { //nastavuje pohyb raket pri pohybu vpred
        for (int i = 0; i < Settings.allEnemies.size(); i++) {
            Enemy pomoc = Settings.allEnemies.get(i);
            pomoc.setTranslateX(pomoc.getTranslateX() - velocityX);
            pomoc.setTranslateY(pomoc.getTranslateY() - velocityY);
        }
        for (int i = 0; i < Board.backgrounds.length; i++) {
            Board.backgrounds[i].setTranslateX(Board.backgrounds[i].getTranslateX() - velocityX);
            Board.backgrounds[i].setTranslateY(Board.backgrounds[i].getTranslateY() - velocityY);
        }
        if (Board.backgrounds[0].getTranslateX() < -Settings.SCREEN_WIDTH) {
            Board.backgrounds[0].setTranslateX(Settings.SCREEN_WIDTH + Board.backgrounds[1].getTranslateX());
        }
        if (Board.backgrounds[1].getTranslateX() < -Settings.SCREEN_WIDTH) {
            Board.backgrounds[1].setTranslateX(Settings.SCREEN_WIDTH + Board.backgrounds[0].getTranslateX());
        }
        if (Board.backgrounds[0].getTranslateX() > Settings.SCREEN_WIDTH) {
            Board.backgrounds[0].setTranslateX(-Settings.SCREEN_WIDTH + Board.backgrounds[1].getTranslateX());
        }
        if (Board.backgrounds[1].getTranslateX() > Settings.SCREEN_WIDTH) {
            Board.backgrounds[1].setTranslateX(-Settings.SCREEN_WIDTH + Board.backgrounds[0].getTranslateX());
        }

        if (Board.backgrounds[2].getTranslateX() < -Settings.SCREEN_WIDTH) {
            Board.backgrounds[2].setTranslateX(Settings.SCREEN_WIDTH + Board.backgrounds[3].getTranslateX());
        }
        if (Board.backgrounds[3].getTranslateX() < -Settings.SCREEN_WIDTH) {
            Board.backgrounds[3].setTranslateX(Settings.SCREEN_WIDTH + Board.backgrounds[2].getTranslateX());
        }
        if (Board.backgrounds[2].getTranslateX() > Settings.SCREEN_WIDTH) {
            Board.backgrounds[2].setTranslateX(-Settings.SCREEN_WIDTH + Board.backgrounds[3].getTranslateX());
        }
        if (Board.backgrounds[3].getTranslateX() > Settings.SCREEN_WIDTH) {
            Board.backgrounds[3].setTranslateX(-Settings.SCREEN_WIDTH + Board.backgrounds[2].getTranslateX());
        }

        if (Board.backgrounds[0].getTranslateY() < -Settings.SCREEN_HEIGHT) {
            Board.backgrounds[0].setTranslateY(Settings.SCREEN_HEIGHT + Board.backgrounds[2].getTranslateY());
        }
        if (Board.backgrounds[2].getTranslateY() < -Settings.SCREEN_HEIGHT) {
            Board.backgrounds[2].setTranslateY(Settings.SCREEN_HEIGHT + Board.backgrounds[0].getTranslateY());
        }
        if (Board.backgrounds[0].getTranslateY() > Settings.SCREEN_HEIGHT) {
            Board.backgrounds[0].setTranslateY(-Settings.SCREEN_HEIGHT + Board.backgrounds[2].getTranslateY());
        }
        if (Board.backgrounds[2].getTranslateY() > Settings.SCREEN_HEIGHT) {
            Board.backgrounds[2].setTranslateY(-Settings.SCREEN_HEIGHT + Board.backgrounds[0].getTranslateY());
        }

        if (Board.backgrounds[1].getTranslateY() < -Settings.SCREEN_HEIGHT) {
            Board.backgrounds[1].setTranslateY(Settings.SCREEN_HEIGHT + Board.backgrounds[3].getTranslateY());
        }
        if (Board.backgrounds[3].getTranslateY() < -Settings.SCREEN_HEIGHT) {
            Board.backgrounds[3].setTranslateY(Settings.SCREEN_HEIGHT + Board.backgrounds[1].getTranslateY());
        }
        if (Board.backgrounds[1].getTranslateY() > Settings.SCREEN_HEIGHT) {
            Board.backgrounds[1].setTranslateY(-Settings.SCREEN_HEIGHT + Board.backgrounds[3].getTranslateY());
        }
        if (Board.backgrounds[3].getTranslateY() > Settings.SCREEN_HEIGHT) {
            Board.backgrounds[3].setTranslateY(-Settings.SCREEN_HEIGHT + Board.backgrounds[1].getTranslateY());
        }
    }

    public void backwardPressed() {
        for (int i = 0; i < Settings.allEnemies.size(); i++) {
            if (velocityX > 0) {
                Settings.allEnemies.get(i).setTranslateX(Settings.allEnemies.get(i).getTranslateX() + velocityX);
            }
            if (velocityX < 0) {
                Settings.allEnemies.get(i).setTranslateX(Settings.allEnemies.get(i).getTranslateX() - velocityX);
            }
        }
    }

    //volat pri otaceni sipkama volano pouze z metod right a left pressed
    public void rotate(double rotation) {
        setRotate(rotation);
        setVelocity();
    }

    //nastavi velocity pri otaceni
    private void setVelocity() {
        velocityX = Math.sin(Math.toRadians(getRotate()))/* * 0.75*/;
        velocityY = -1 * (Math.cos(Math.toRadians(getRotate()))/* * 0.75*/);
        //System.out.println("X = " + velocityX + ",Y = " + velocityY);
        velocityX *= Settings.movingSpeed;
        velocityY *= Settings.movingSpeed;
    }

    //nastavi velocity pri otaceni
    //Do parametru se nastavi o jaky uhel se ma otocit raketa.velocity (aby se letelo do strany)
    private void setVelocity(int otoceni) {
        velocityX = Math.sin(Math.toRadians(getRotate() + otoceni));
        velocityY = -1 * (Math.cos(Math.toRadians(getRotate() + otoceni))/* * 0.75*/);
        //System.out.println("X = " + velocityX + ",Y = " + velocityY);
    }

    //parametr je jedna kdyz doprava, minus jedna kdyz pohyb doleva
    private int leftOrRight = 0; //kdyz je jedna je doprava, minus jedna je doleva
    public void siteKeyPressed(int site) {
        /*if (shiftPressed) {
            rotate(getRotate() - Settings.FASTER_ROTATION_SPEED);
        } else {
            rotate(getRotate() - Settings.ROTATION_SPEED);
        }
        if (getRotate() < -360) {
            rotate(getRotate() + 360);
        }*/
        if (Settings.naibtoPohyb) {
            Settings.nowInSideMove = true;
            Board.rectangleProgressBar.setHeight(10);
            Board.rectangleProgressBar.setWidth(0);
            Settings.naibtoPohyb = false;
            Board.rectangleProgressBar.setTranslateX(Board.progressBar.getTranslateX() - 80);
            leftOrRight = site;
        }

    }

    public void movingAnimation() {
        setVelocity(90 * leftOrRight);
        velocityX *= Settings.RIGHT_LEFT_SPEED;
        velocityY *= Settings.RIGHT_LEFT_SPEED;
        forwardPressed();
        velocityX /= Settings.RIGHT_LEFT_SPEED;
        velocityY /= Settings.RIGHT_LEFT_SPEED;
    }

}
