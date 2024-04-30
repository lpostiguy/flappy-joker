import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Represents a stealthy hero character in the game. Extends the Character
 * class.
 */
public class HeroFurtif extends Character implements Hero {

    // Attributes
    private double positionX; // The x-coordinate position of the stealthy hero
    private double positionY; // The y-coordinate position of the stealthy hero
    private int radius; // The radius of the stealthy hero
    private final ImageView imageView; // The image view representing the
    // stealthy hero

    // ************************************************************************
    // Constructor
    public HeroFurtif() {
        super();

        this.setRadius(this.randomRadius()); // Make the radius random

        // Load the image for Arrow
        Image meleeImage = new Image("/assets/images/Arrow.png");
        this.imageView = new ImageView(meleeImage);
        imageView.setFitWidth(this.getRadius() * 2);
        imageView.setFitHeight(this.getRadius() * 2);

        // Set initial position
        this.positionX = 640 + getRadius();
        this.positionY = setRandomPosition(360, getRadius());
    }

    // ************************************************************************
    // Getters

    /**
     * Gets the attack damage dealt by the stealthy hero.
     *
     * @return The attack dealt by the stealthy hero.
     */
    @Override
    public int getAttackDamage() {
        return 0;
    }

    /**
     * Gets the amount of coins dropped by the stealthy hero upon defeat.
     *
     * @return The amount of coins dropped by the stealthy hero.
     */
    @Override
    public int getCoinDropAmount() {
        return 8;
    }

    /**
     * Gets the amount of coins stolen by the stealthy hero.
     *
     * @return The amount of coins stolen by the stealthy hero.
     */
    @Override
    public int getCoinStealAmount() {
        return 10;
    }

    /**
     * Gets the x-coordinate position of the stealthy hero.
     *
     * @return The x-coordinate position of the stealthy hero.
     */
    @Override
    public double getPositionX() {
        return positionX;
    }

    /**
     * Gets the y-coordinate position of the stealthy hero.
     *
     * @return The y-coordinate position of the stealthy hero.
     */
    @Override
    public double getPositionY() {
        return positionY;
    }

    /**
     * Gets the image view representing the stealthy hero.
     *
     * @return The image view representing the stealthy hero.
     */
    @Override
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the radius of the stealthy hero.
     *
     * @return The radius of the stealthy hero.
     */
    @Override
    public int getRadius() {
        return radius;
    }

    /**
     * Gets the type of the stealthy hero.
     *
     * @return The type of the stealthy hero.
     */
    @Override
    public String getType() {
        return "furtif";
    }

    // ************************************************************************
    // Setters

    // Setters

    /**
     * Sets the x-coordinate position of the stealthy hero.
     *
     * @param position The x-coordinate position to set.
     */
    @Override
    public void setPositionX(double position) {
        this.positionX = position;
    }

    /**
     * Sets the y-coordinate position of the stealthy hero.
     *
     * @param position The y-coordinate position to set.
     */
    @Override
    public void setPositionY(double position) {
        this.positionY = position;
    }

    /**
     * Sets the radius of the stealthy hero.
     *
     * @param radius The radius to set.
     */
    private void setRadius(int radius) {
        this.radius = radius;
    }

    // ************************************************************************
    // Methods

    /**
     * Generates a random radius between 10 and 45 for the stealthy hero.
     *
     * @return The random radius.
     */
    public int randomRadius() {
        int i = 10; // Lowest possible value of the radius

        // Add a random number between 0 and 35 (10 + 35 = 45)
        i += (int) (Math.floor(Math.random() * 35));
        return i;
    }

    /**
     * Sets a random Y position within the specified scene size and image
     * radius for the stealthy hero.
     *
     * @param sceneSize   The size of the scene.
     * @param imageRadius The radius of the image.
     * @return The random Y position.
     */
    public int setRandomPosition(int sceneSize, int imageRadius) {
        Random random = new Random();

        int maxY = sceneSize - imageRadius;

        // Generate random position Y within the calculated range
        return random.nextInt(maxY - imageRadius + 1) + imageRadius;
    }

    /**
     * Updates the position of the stealthy hero using sinusoidal motion.
     *
     * @param now The current time in nanoseconds.
     */
    public void updatePosition(double now) {
        // Calculate the new Y position using sinusoidal motion
        double yOffset = 0.5 * Math.sin(now * 1e-9); // Adjust frequency and
        // amplitude as needed
        double newY = positionY + yOffset;

        // Update the Y position and the ImageView's translation
        positionY = newY;
        imageView.setTranslateY(positionY);
    }
}
