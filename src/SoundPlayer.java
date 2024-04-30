import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for playing sound files.
 */
public class SoundPlayer {

    private static Map<String, Thread> soundThreads = new HashMap<>();

    /**
     * Plays a sound from the specified file path.
     *
     * @param filePath The path to the sound file.
     */
    public static void playSound(final String filePath) {
        if (soundThreads.containsKey(filePath)) {
            return; // If already playing, do nothing
        }

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
        soundThreads.put(filePath, soundThread);
    }

    /**
     * Stops the specified audio file.
     *
     * @param filePath The path to the sound file to stop.
     */
    public static void stopSound(String filePath) {
        Thread soundThread = soundThreads.get(filePath);
        if (soundThread != null && soundThread.isAlive()) {
            soundThread.interrupt(); // Interrupt the sound thread
            soundThreads.remove(filePath); // Remove the thread from the map
        }
    }
}

