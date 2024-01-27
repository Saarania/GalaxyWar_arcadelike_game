/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Jan
 */
public class KeyboardSettings {

    private static StackPane root = GalaxyWars.root;
    private static Rocket player = Board.player;
    private static Scene scene = GalaxyWars.scene;

    public static boolean firePressed = false;
    //pohyb
    public static boolean forwardPressed = false;
    public static boolean backwardPressed = false;
    public static boolean leftKeyPressed = false;
    public static boolean rightKeyPressed = false;

    public static void inicializate() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.S) {
                backwardPressed = true;
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                leftKeyPressed = true;
            }
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                rightKeyPressed = true;
            }
            /*if (event.getCode() == KeyCode.SHIFT) {
                player.shiftPressed = true;
            }*/
            if (event.getCode() == KeyCode.ESCAPE) {
                GalaxyWars.stage.close();
            }
            if (/*event.getCode() == KeyCode.W || */event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.NUMPAD4) {
                firePressed = true;
            }
            if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
                forwardPressed = true;
            }
            //handlary pro hlavni nabidku 
            if (event.getCode() == KeyCode.S && Board.menuActive) {
                startAction();
            }
            if (event.getCode() == KeyCode.I && Board.menuActive) {
                infoAction();
            }
            if (event.getCode() == KeyCode.K && Board.menuActive) {
                konecAction();
            }
        });

        scene.setOnKeyReleased((KeyEvent event) -> {
            if (/*event.getCode() == KeyCode.W || */event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.NUMPAD4) {
                firePressed = false;
            }
            if (event.getCode() == KeyCode.S) {
                backwardPressed = false;
            }
            if (event.getCode() == KeyCode.SHIFT) {
                player.shiftPressed = false;
            }
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                leftKeyPressed = false;
            }
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                rightKeyPressed = false;
            }
            if (event.getCode() == KeyCode.W  || event.getCode() == KeyCode.UP) {
                forwardPressed = false;
            }
        });
     

        Board.infoStartKonecBoard[0].setOnMouseClicked((Event event) -> {
            startAction();
        });
        Board.infoStartKonecBoard[1].setOnMouseClicked((Event event) -> {
            infoAction();
        });
        Board.infoStartKonecBoard[2].setOnMouseClicked((Event event) -> {
            konecAction();
        });
        //nastaveni tlacitek mysi
        scene.setOnMousePressed((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                firePressed = true;
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                forwardPressed = true;
            }
            
        });
        scene.setOnMouseReleased((MouseEvent event) -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                firePressed = false;
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                forwardPressed = false;
            }
        });
    }

    private static void startAction() {
        for (int i = 0; i < Board.infoStartKonecBoard.length; i++) {
            Board.infoStartKonecBoard[i].setVisible(false);
        }
        Board.menuActive = false;
        Board.progressBar.setVisible(true);
        Board.helthBar.setVisible(true);
        Board.helthBarRectangle.setVisible(true);
        Board.helthText.setVisible(true);
        //start theme to play
        AudioThemePlayer audioThemePlayer = new AudioThemePlayer();
        audioThemePlayer.playTheme();
        Settings.hraBezi = true;
    }

    private static void infoAction() {
        //doplnit
    }

    private static void konecAction() {
        GalaxyWars.stage.close();
    }
}
