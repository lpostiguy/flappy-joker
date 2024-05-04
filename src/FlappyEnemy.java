import javafx.application.Application;

/**
 * IFT 1025 - TP2 - Flappy Enemy Authors : Louis-Philippe Ostiguy (20274034)
 * Noah Tremblay Taillon (20190661) Date : May 3, 2024
 * <p>
 * This program is a game where you, the player, incarnate the Joker. The goal
 * is to collect as many coins as possible, while killing of avoiding the
 * heroes trying to defeat you. To do that, you have a gun to shoot the heroes,
 * and you can 'jump fly', flappy bird style.
 * <p>
 * Add the JLayer1.0.1 library to the project to run the program and have
 * music. In Intelijii: 1. File -> Project Structure 2. Library -> Add library
 * -> jl1.0.1.jar -> Apply library 3. Make sure the SoundPlayer file is
 * recognizing the library.
 * <p>
 * Sound effects, shooting animation, game over popup and Home Screen were
 * added for the game as bonuses.
 * <p>
 * We also added an easter egg for the "Merci Robin", which is outlined in the
 * enemy class.
 */

public class FlappyEnemy {
    public static void main(String[] args) {
        Application.launch(WindowView.class, args);
    }
}

