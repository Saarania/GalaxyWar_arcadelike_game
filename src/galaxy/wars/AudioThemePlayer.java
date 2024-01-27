package galaxy.wars;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;

public class AudioThemePlayer {
    public void playTheme() {

        // Get an AudioInputStream from the provided file
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(AudioThemePlayer.class.getClassLoader().getResourceAsStream("sounds/theme.wav")));
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get a Clip from the AudioSystem
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

        // Open the AudioInputStream with the Clip
        try {
            clip.open(audioInputStream);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set the clip to loop indefinitely
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        // Start playing
        clip.start();
    }
}
