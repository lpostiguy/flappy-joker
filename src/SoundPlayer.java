import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for playing sound effects in the game.
 */
public class SoundPlayer {
    private static final ConcurrentHashMap<String, AdvancedPlayer> players =
            new ConcurrentHashMap<>();

    /**
     * Plays the sound effects located at the specified file path. If a
     * sound is
     * already playing from the same file path, it stops the previous sound and
     * plays the new one.
     *
     * @param filePath The file path of the sound effect to be played.
     */
    public static void playSound(final String filePath) {
        stopSound(filePath); // Ensure previous sound is stopped before
        // playing a new one
        try {
            InputStream is = new FileInputStream(filePath);
            AdvancedPlayer player = new AdvancedPlayer(is);
            players.put(filePath, player);

            Thread soundThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    if (!Thread.currentThread().isInterrupted()) {
                        e.printStackTrace();
                    }
                } finally {
                    players.remove(filePath);
                }
            });
            soundThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the sound effect that is currently playing from the specified file
     * path.
     *
     * @param filePath The file path of the sound effect to be stopped.
     */
    public static void stopSound(String filePath) {
        AdvancedPlayer player = players.get(filePath);
        if (player != null) {
            try {
                player.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            players.remove(filePath);
        }
    }
}