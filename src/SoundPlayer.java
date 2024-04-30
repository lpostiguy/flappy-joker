import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Utility class for playing sound files.
 */
public class SoundPlayer {

    /**
     * Plays a sound from the specified file path.
     *
     * @param filePath The path to the sound file.
     */
    public static void playSound(final String filePath) {

        // Create a new thread to play the sound
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Create input stream from the sound file
                    InputStream is = new FileInputStream(filePath);

                    // Create an AdvancedPlayer object to play the sound
                    AdvancedPlayer player = new AdvancedPlayer(is);

                    // Start playing the sound
                    player.play();
                } catch (FileNotFoundException e) {
                    // Handle file not found exception
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    // Handle JavaLayer exception
                    e.printStackTrace();
                }
            }
        });
        // Start the sound
        soundThread.start();
    }
}