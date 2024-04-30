import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Represents a tank hero character in the game.
 * Extends the Character class.
 */
public class HeroTank extends Character implements Hero {

    // Attributes
    private double positionX; // The x-coordinate position of the tank hero
    private double positionY; // The y-coordinate position of the tank hero
    private int radius; // The radius of the tank hero
    private final ImageView imageView; // The image view representing the tank hero

    // ************************************************************************
    // Constructor
    public HeroTank() {
        super();
        this.setRadius(this.randomRadius()); // Make the radius random

        // Load the image for Arrow
        Image meleeImage = new Image("/assets/images/Superman.png");
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
     * Gets the attack damage dealt by the tank hero.
     *
     * @return The attack dealt by the tank hero.
     */
    @Override
    public int getAttackDamage() {
        return 50;
    }

    /**
     * Gets the amount of coins dropped by the tank hero upon defeat.
     *
     * @return The amount of coins dropped by the tank hero.
     */
    @Override
    public int getCoinDropAmount() {
        return 7;
    }

    /**
     * Gets the amount of coins stolen by the tank hero.
     *
     * @return The amount of coins stolen by the tank hero.
     */
    @Override
    public int getCoinStealAmount() {
        return 0;
    }

    /**
     * Gets the x-coordinate position of the tank hero.
     *
     * @return The x-coordinate position of the tank hero.
     */
    @Override
    public double getPositionX() {
        return this.positionX;
    }

    /**
     * Gets the y-coordinate position of the tank hero.
     *
     * @return The y-coordinate position of the tank hero.
     */
    @Override
    public double getPositionY() {
        return this.positionY;
    }

    /**
     * Gets the image view representing the tank hero.
     *
     * @return The image view representing the tank hero.
     */
    @Override
    public ImageView getImageView() {return imageView;}

    /**
     * Gets the type of the character.
     *
     * @return The type of the tank hero.
     */
    @Override
    public String getType() {
        return "tank";
    }

    /**
     * Gets the radius of the tank hero.
     *
     * @return The radius of the tank hero.
     */
    @Override
    public int getRadius() {
        return radius;
    }

    // ************************************************************************
    // Setters

    /**
     * Sets the x-coordinate position of the tank hero.
     *
     * @param position The x-coordinate position to set.
     */
    @Override
    public void setPositionX(double position) {
        this.positionX = position;
    }

    /**
     * Sets the y-coordinate position of the tank hero.
     *
     * @param position The y-coordinate position to set.
     */
    @Override
    public void setPositionY(double position) {
        this.positionY = position;
    }

    /**
     * Sets the radius of the tank hero.
     *
     * @param radius The radius to set.
     */
    private void setRadius(int radius) {
        this.radius = radius;
    }

    // ************************************************************************
    // Methods

    /**
     * Generates a random radius between 10 and 45 for the tank hero.
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
     * Sets a random Y position within the specified scene size and image radius for the tank hero.
     *
     * @param sceneSize    The size of the scene.
     * @param imageRadius  The radius of the image.
     * @return             The random Y position.
     */
    public int setRandomPosition(int sceneSize, int imageRadius) {
        Random random = new Random();

        int maxY = sceneSize - imageRadius;

        // Generate random position Y within the calculated range
        return random.nextInt(maxY - imageRadius + 1) + imageRadius;
    }

    /**
     * Updates the position of the tank hero with random values between -30 and 30 pixels
     * on the X and Y axes.
     */
    public void updatePosition() {
        // Calculate the new Y position using sinusoidal motion
        double newX = this.getPositionX() + Math.random() * 60 - 30; // Random x within range [-30, 30]
        double newY = this.getPositionY() + Math.random() * 60 - 30; // Random y within range [-30, 30]

        // Update the Y and X positions
        positionY = newY;
        positionX = newX;

        // Update the ImageView
        imageView.setTranslateY(positionY);
        imageView.setTranslateX(positionX);
    }
}
