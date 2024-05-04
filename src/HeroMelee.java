import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Represents a melee hero character in the game. Extends the Character class.
 * This hero instantly kills the enemy
 */
public class HeroMelee extends Character implements Hero {

    // Attributes
    private double positionX;           // The x-coordinate position of the
    // melee hero
    private double positionY;           // The y-coordinate position of the
    // melee hero
    private int radius;                 // The radius of the melee hero
    private final ImageView imageView;  // The image view representing the
    // melee hero

    // ************************************************************************
    // Constructor
    public HeroMelee() {
        super();

        this.setRadius(this.randomRadius()); // Make the radius random

        // Load the image for Flash
        Image meleeImage = new Image("/assets/images/Flash.png");
        this.imageView = new ImageView(meleeImage);
        imageView.setFitWidth(this.getRadius() * 2);
        imageView.setFitHeight(this.getRadius() * 2);

        // Set initial position
        this.positionX = 640 + getRadius();
        // Y is randomized
        this.positionY = setRandomPosition(360, getRadius());
    }

    // ************************************************************************
    // Getters

    /**
     * Gets the attack damage dealt by the melee hero.
     *
     * @return The attack damage dealt by the melee hero.
     */
    @Override
    public int getAttackDamage() {
        return 100;
    }

    /**
     * Gets the amount of coins dropped by the melee hero upon defeat.
     *
     * @return The amount of coins dropped by the melee hero.
     */
    @Override
    public int getCoinDropAmount() {
        return 5;
    }

    /**
     * Gets the amount of coins stolen by the melee hero.
     *
     * @return The amount of coins stolen by the melee hero.
     */
    @Override
    public int getCoinStealAmount() {
        return 0;
    }

    /**
     * Gets the x-coordinate position of the melee hero.
     *
     * @return The x-coordinate position of the melee hero.
     */
    @Override
    public double getPositionX() {
        return positionX;
    }

    /**
     * Gets the y-coordinate position of the melee hero.
     *
     * @return The y-coordinate position of the melee hero.
     */
    @Override
    public double getPositionY() {
        return positionY;
    }

    /**
     * Gets the image view representing the melee hero.
     *
     * @return The image view representing the melee hero.
     */
    @Override
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the radius of the melee hero.
     *
     * @return The radius of the melee hero.
     */
    @Override
    public int getRadius() {
        return radius;
    }

    /**
     * Gets the type of the character.
     *
     * @return The type of the melee hero.
     */
    @Override
    public String getType() {
        return "melee";
    }

    // ************************************************************************
    // Setters

    /**
     * Sets the x-coordinate position of the melee hero.
     *
     * @param position The x-coordinate position to set.
     */
    @Override
    public void setPositionX(double position) {
        this.positionX = position;
    }

    /**
     * Sets the y-coordinate position of the melee hero.
     *
     * @param position The y-coordinate position to set.
     */
    @Override
    public void setPositionY(double position) {
        this.positionY = position;
    }

    /**
     * Sets the radius of the melee hero.
     *
     * @param radius The radius to set.
     */
    @Override
    public void setRadius(int radius) {
        this.radius = radius;
    }

    // ************************************************************************
    // Methods

    /**
     * Generates a random radius between 10 and 45 for the melee hero.
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
     * radius for the melee hero.
     *
     * @param sceneSize   The size of the scene.
     * @param imageRadius The radius of the image.
     * @return The random Y position.
     */
    public int setRandomPosition(int sceneSize, int imageRadius) {
        Random random = new Random();

        int maxY = sceneSize - imageRadius;

        // Generates a random position Y within the calculated range
        return random.nextInt(maxY - imageRadius + 1) + imageRadius;
    }
}
