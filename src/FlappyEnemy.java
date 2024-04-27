
public class FlappyEnemy {
    private Enemy enemy; // Creating an enemy with default values
    private WindowView view;

    public FlappyEnemy(Enemy e, WindowView v) {
        this.enemy = e;
        this.view = v;
    }

    public static void main(String[] args) {

        // Controller of the application

        // Launch the JavaFX application
        WindowView.launch(WindowView.class, args);

    }
}