import javafx.scene.image.ImageView;

/**
 * Abstract class representing a character in the game.
 * Used by Enemy, HeroTank, HeroFurtif and HeroMelee.
 */
public abstract class Character {
    // Attributes
    private boolean isAlive = true; // Flag indicating whether the character is alive
    private double positionX; // The x-coordinate position of the character
    private double positionY; // The y-coordinate position of the character
    private int radius; // The radius of the character
    private int horizontalSpeed; // The horizontal speed of the character
    private ImageView imageView; // The image view representing the character

    // ************************************************************************
    // Getters

    /**
     * Gets the attack damage of the character.
     *
     * @return The attack damage of the character.
     */
    public int getAttackDamage() {
        return 0;
    }

    /**
     * Gets the amount of coins dropped by the character upon defeat.
     *
     * @return The amount of coins dropped.
     */
    public int getCoinDropAmount() {
        return 0;
    }

    /**
     * Gets the amount of coins stolen by the character.
     *
     * @return The amount of coins stolen.
     */
    public int getCoinStealAmount() {
        return 0;
    }

    /**
     * Checks if the character is alive.
     *
     * @return True if the character is alive, false otherwise.
     */
    public boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Gets the x-coordinate position of the character.
     *
     * @return The x-coordinate position of the character.
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Gets the y-coordinate position of the character.
     *
     * @return The y-coordinate position of the character.
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Gets the radius of the character.
     *
     * @return The radius of the character.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Gets the horizontal speed of the character.
     *
     * @return The horizontal speed of the character.
     */
    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    /**
     * Gets the image view representing the character.
     *
     * @return The image view representing the character.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the type of the character.
     *
     * @return The type of the character.
     */
    public String getType() {
        return "Character";
    }

    // ************************************************************************
    // Setters

    /**
     * Sets whether the character is alive.
     *
     * @param value True if the character is alive, false otherwise.
     */
    public void setIsAlive(boolean value) {
        isAlive = value;
    }

    /**
     * Sets the x-coordinate position of the character.
     *
     * @param positionX The x-coordinate position to set.
     */
    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    /**
     * Sets the y-coordinate position of the character.
     *
     * @param positionY The y-coordinate position to set.
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}