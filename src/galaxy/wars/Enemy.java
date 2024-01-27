/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import static galaxy.wars.GalaxyWars.random;
import static galaxy.wars.GalaxyWars.root;
import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Jan
 */
public class Enemy extends ImageView {

    double velocityX;
    double velocityY;
    int i; //pokud se i == 5 nepritel zmizi, i se pricita pravidelne jak nepritel umre
    private Special special; //typ bonusu ktery ti pridaji vetsinou bude null

    public Enemy(String url) {
        super(url);
        i = 0;
        Settings.allEnemies.add(this);
        root.getChildren().add(this);
        //nastavovani souradnic
        setTranslateX(random.nextInt(800));
        setTranslateY(random.nextInt(600) - getTranslateX());
        if (getTranslateX() + getTranslateY() < 1500) {
            setTranslateX(getTranslateX() + 240);
            setTranslateY(getTranslateY() + 80);
        }
        if (random.nextInt(2) == 0) {
            setTranslateX(getTranslateX() * -1);
        }
        if (random.nextInt(2) == 0) {
            setTranslateY(getTranslateY() * -1);
        }
        ///////
        setVelocityX(getTranslateX());
        setVelocityY(getTranslateY());
        setRotation();
    }

    public static void moveAllEnemies() {
        for (int i = 0; i < Settings.allEnemies.size(); i++) {
            Enemy pomocne = Settings.allEnemies.get(i);
            pomocne.setTranslateX(pomocne.getTranslateX() + pomocne.velocityX * Settings.ENEMY_MOVESPEED);
            pomocne.setTranslateY(pomocne.getTranslateY() + pomocne.velocityY * Settings.ENEMY_MOVESPEED);
            if (Board.checkCollision(pomocne, Board.player)) { //pokud se nepritel dostane ke hraci
                //pomocne.delete(pomocne);
                pomocne.die();
            }
            pomocne.setDirection();
        }
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
        setImage(new Image("images/bonus.png"));
    }

    public void increaseI() {
        if (++i >= 5) {
            delete(this);
        }
    }

    public void setDirection() {
        setVelocityX(getTranslateX());
        setVelocityY(getTranslateY());
        setRotation();
    }

    //kdyz se enemy dostane ke hraci
    public void die() {
        delete(this);
        if (getSpecial() == null && Settings.aktivniBonus != Special.SHIELD) {
            setImage(Settings.enemyDeath);
            int kolikUbere = 20 + Board.random.nextInt(8);
            Settings.health -= kolikUbere;
            Board.helthBarRectangle.setWidth(Board.helthBarRectangle.getWidth() - (Settings.HEALTHBAR_WIDTH / 100) * kolikUbere);
            Board.helthBarRectangle.setTranslateX(Board.helthBarRectangle.getTranslateX() - ((Settings.HEALTHBAR_WIDTH / 100) / 2) * kolikUbere);
            if (Settings.health > 75) {
                Board.helthBarRectangle.setFill(Color.rgb(40, 255, 40));
            } else {
                if (Settings.health > 50) {
                    Board.helthBarRectangle.setFill(Color.rgb(50, 200, 50));
                } else {
                    if (Settings.health > 20) {
                        Board.helthBarRectangle.setFill(Color.rgb(200, 50, 50));
                    } else {
                        Board.helthBarRectangle.setFill(Color.rgb(255, 40, 40));
                    }
                }
            }
            Board.helthText.setText(Settings.health + "");
            if (Settings.health <= 0) {
                Board.gameOver();
            }
        }
    }

    public void setVelocityX(double x) {
        velocityX = -x / (Settings.fps * 10);
    }

    public void setVelocityY(double y) {
        velocityY = -y / (Settings.fps * 10);
    }

    public void setRotation() {
        if (getTranslateX() < 0) {
            setRotate(Math.toDegrees(Math.atan(getTranslateY() / getTranslateX())) + 90);
        } else {
            setRotate(Math.toDegrees(Math.atan(getTranslateY() / getTranslateX())) - 90);
        }
    }

    public static void deleteAllDestroyedEnemies() {
        //projede vsechny nepratele a pokud je nejaky zniceny dlouho tak ho smaze
        for (int i = 0; i < Settings.allEnemies.size(); i++) {
            if (Settings.allEnemies.get(i).getImage() == Settings.enemyDeath) {
                Settings.allEnemies.get(i).delete(Settings.allEnemies.get(i));
            }
        }
    }

    public void delete(Enemy thisEnemy) {
        if (special != null) {
            Settings.aktivniBonus = special != Special.HEAL ? special : null;
            switch (special) {
                case MACHINE_GUN:
                    Board.player.setImage(new Image("images/machinegun.png"));
                    Settings.vystrelDoba = Settings.vystrelDoba / 2;
                    break;
                case HEAL:
                    int heal = 20 + Board.random.nextInt(10);
                    Board.helthText.setText(Settings.health  + " + " + heal);
                    if (Settings.health + heal > 100) {
                        Settings.health = 100;
                    }
                    Settings.health += heal;
                    Board.helthBarRectangle.setWidth(Board.helthBarRectangle.getWidth() + (Settings.HEALTHBAR_WIDTH/100)*heal);
                    Board.helthBarRectangle.setTranslateX((Board.helthBarRectangle.getTranslateX() + (Settings.HEALTHBAR_WIDTH/100)*(heal/2)));
                    if (Board.helthBarRectangle.getWidth() > Settings.HEALTHBAR_WIDTH) {
                        Board.helthBarRectangle.setWidth(Settings.HEALTHBAR_WIDTH);
                        Settings.health = 100;
                        Board.helthBarRectangle.setTranslateX(0);
                    }
                    break;
                case SHIELD:
                    Board.shieldImage.setVisible(true);
                    Settings.movingSpeed = Settings.movingSpeed * 2;
                    break;
            }
        }
        setVisible(false);
        Settings.allEnemies.remove(thisEnemy);
        thisEnemy = null;
    }

}
