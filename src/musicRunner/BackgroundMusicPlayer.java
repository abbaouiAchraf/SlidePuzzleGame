package musicRunner;
import java.io.*;
import javax.sound.sampled.*;

public class BackgroundMusicPlayer {

    private Clip clip;

    public BackgroundMusicPlayer(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
