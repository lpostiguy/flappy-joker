import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoundPlayer {
    private static ConcurrentHashMap<String, AdvancedPlayer> players = new ConcurrentHashMap<>();

    public static void playSound(final String filePath) {
        stopSound(filePath); // Ensure previous sound is stopped before playing a new one
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

    public static void stopSound(String filePath) {
        AdvancedPlayer player = players.get(filePath);
        if (player != null) {
            try {
                player.close(); // This should effectively stop the music playback
            } catch (Exception e) {
                e.printStackTrace();
            }
            players.remove(filePath);
        }
    }
}


