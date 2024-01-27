/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import javafx.scene.image.ImageView;

/**
 *
 * @author Jan
 */
public class Bullet extends ImageView {

    double velocityX;
    double velocityY;
    //parametr forward je bud 1 nebo -1 kterou celkovou velocitu vynasobime, zpusobime mozne striledi dozadu
    public Bullet(String url, double rotation, int forward) {
        super(url);
        setRotate(rotation);
        velocityX = (Math.sin(Math.toRadians(rotation)) * 5)*forward * Settings.BULLET_SPEED;
        velocityY = (-1 * (Math.cos(Math.toRadians(rotation)) * 5))*forward * Settings.BULLET_SPEED;
    }

    public void delete() {
        setVisible(false);
        Settings.allBullets.remove(this);
      
    }

    public static void pohybVsechNaboju() {
        for (int i = 0; i < Settings.allBullets.size(); i++) {
            Bullet pomocne = Settings.allBullets.get(i);
            pomocne.setTranslateX(pomocne.getTranslateX() + pomocne.velocityX);
            pomocne.setTranslateY(pomocne.getTranslateY() + pomocne.velocityY);
            if (pomocne.getTranslateX() < -Settings.BORDER_OF_SHOTS || pomocne.getTranslateX() > Settings.BORDER_OF_SHOTS) {
                pomocne.delete();
            } else {
                for (int j = 0; j < Settings.allEnemies.size(); j++) {
                    //kontrola dotyku s nepritelem
                    if (Board.checkCollision(pomocne, Settings.allEnemies.get(j))) {
                        pomocne.delete();
                        Settings.allEnemies.get(j).delete(Settings.allEnemies.get(j));
                    }
                }
            }
        }
    }
}
