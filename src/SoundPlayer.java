import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SoundPlayer {

    public static void playSound(final String filePath) {
        Thread soundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream is = new FileInputStream(filePath);
                    AdvancedPlayer player = new AdvancedPlayer(is);
                    player.play();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        soundThread.start();
    }
}