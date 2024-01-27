/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package galaxy.wars;

import java.util.Random;
import java.util.Timer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Sara Praksova
 *
 * 5
 *
 * Hra po hre Marshal a Spion dlohou dobu nedokoncena: Musi se dokoncit vsehcny
 * obrazky malovane, chtelo by je nahradit nejtezsi bylo nastavit pohyb lode
 * (lod se nehybe, hybe se vsechno okolo) a pohyb strel (Fakt tezko se delalo
 * musela jsem pouzit pythagorovu vetu, sin a cos) cely projekt je zmateny ale
 * zato je vsechno pouze v jednom dalsim vlakne
 */
public class GalaxyWars extends Application {

    public static StackPane root = new StackPane();
    public static Scene scene = new Scene(root, 1366, 768);
    public static Stage stage;
    private static Rocket player = Board.player;
    static Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        Board.inicializator();
        KeyboardSettings.inicializate();
        startTimer();

        primaryStage.setTitle("Galaxy War");
        primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void startTimer() {
        Thread timer = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(Settings.fps);
                    javafx.application.Platform.runLater(() -> {
                        if (Settings.hraBezi) {
                            Board.timedChanged();
                        }
                    });

                } catch (InterruptedException ex) {
                    System.out.println("nejde spat");
                    return;
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

}
