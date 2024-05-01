/**
 * IFT 1025 - TP2 - Flappy Enemy
 * Authors :
 *      Louis-Philippe Ostiguy (20274034)
 *      Noah Tremblay Taillon (20190661)
 * Date : May 1, 2024
 *
 * This program is a game where you, the player, incarnate the Joker. The goal is
 * to collect as many coins as possible, while killing of avoiding the heroes
 * trying to defeat you. To do that, you have a gun to shoot the heroes, and you
 * can 'fly', flappy bird style.
 *
 */


public class FlappyEnemy {
    private Enemy enemy; // Creating an enemy with default values
    private WindowView view;

    /**
     * Constructs a FlappyEnemy instance with the specified Enemy and WindowView.
     *
     * @param e The enemy instance.
     * @param v The window view instance.
     */
    public FlappyEnemy(Enemy e, WindowView v) {
        this.enemy = e;
        this.view = v;
    }

    /**
     * The entry point of the FlappyEnemy application.
     */
    public static void main(String[] args) {

        // Controller of the application

        // Launch the JavaFX application
        WindowView.launch(WindowView.class, args);

    }
}