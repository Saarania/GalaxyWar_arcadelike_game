/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Pocitac
 */
public class Bonus extends ImageView {

    private Special special;

    public Bonus(Special special, Image image) {
        super(image);
        this.special = special;
        GalaxyWars.root.getChildren().add(this);
        Settings.allBonuses.add(this);
    }

    public Bonus(Special special, String url) {
        super(url);
        this.special = special;
        GalaxyWars.root.getChildren().add(this);
        //VYtvareni Bonusu
        if (Board.player.getRotate() <= -135 && Board.player.getRotate() >= 135) {  //YYYYYYY
            setTranslateX(Settings.SCREEN_WIDTH / 2 / Math.tan(Board.player.getRotate()) * Board.player.getRotate()>=0 ? 1:-1);
            setTranslateY(Settings.SCREEN_HEIGHT / 2);
        } else if (Board.player.getRotate() >= -45 && Board.player.getRotate() <= 45) {
            setTranslateX(Settings.SCREEN_WIDTH / 2 / Math.tan(Board.player.getRotate()) * Board.player.getRotate()>=0 ? 1:-1);
            setTranslateY(Settings.SCREEN_HEIGHT / -2);
            //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        } else if ((Board.player.getRotate() <-45 && Board.player.getRotate() >0) ||(Board.player.getRotate() >45 && Board.player.getRotate() < 180)) { 
            setTranslateX(Settings.SCREEN_WIDTH / 2 * Board.player.getRotate()>=0 ? 1:-1);
            setTranslateY(Settings.SCREEN_HEIGHT / -2 * Math.tan(Board.player.getRotate()));
        }else {
             setTranslateX(Settings.SCREEN_WIDTH / 2 * Board.player.getRotate()>=0 ? 1:-1);
            setTranslateY(Settings.SCREEN_HEIGHT / 2 * Math.tan(Board.player.getRotate()));
        }

        System.out.println("Bonuns spawned X = " + getTranslateX() + ", Y = " + getTranslateY());
        Settings.allBonuses.add(this);
    }

    public Special getSpecial() {
        return special;
    }

    public void setSpecial(Special special) {
        this.special = special;
    }

}
