import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * Represents an Enemy character in the game.
 * Extends the Character class.
 */
public class Enemy extends Character {

    // Attributes

    private int health = 100; // The health of the enemy
    private final int radius = 30; // The radius of the enemy
    private int coinCollected = 0; // The number of coins collected by the enemy
    private int horizontalSpeed = 120; // The horizontal speed of the enemy
    private int verticalSpeed = 0; // The vertical speed of the enemy
    private int gravityAcceleration = 170; // The gravity acceleration applied to the enemy
    private int startGravityAcceleration = 170; // The initial gravity acceleration
    private double positionX; // The x-coordinate position of the enemy
    private double positionY; // The y-coordinate position of the enemy
    private boolean canShoot = true; // Flag indicating whether the enemy can shoot
    private final ImageView imageView; // The image view representing the enemy
    private boolean isJumping = false; // Flag indicating whether the enemy is jumping
    private boolean canJump = true; // Flag to control jump initiation

    //Constructor
    public Enemy() {
        super();

        // Load the image for the Joker
        Image imageEnemy = new Image("/assets/images/Joker.png");
        this.imageView = new ImageView(imageEnemy);
        imageView.setFitWidth(getRadius() * 2);
        imageView.setFitHeight(getRadius() * 2);

        // Set initial position
        this.positionX = 320 - getRadius();
        this.positionY = 320;
    }

    // ************************************************************************
    // Getters

    /**
     * Gets the image view representing the enemy.
     *
     * @return The image view representing the enemy.
     */
    @Override
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the health of the enemy.
     *
     * @return The health of the enemy.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Gets the x-coordinate position of the enemy.
     *
     * @return The x-coordinate position of the enemy.
     */
    @Override
    public double getPositionX() {
        return positionX;
    }

    /**
     * Gets the y-coordinate position of the enemy.
     *
     * @return The y-coordinate position of the enemy.
     */
    @Override
    public double getPositionY() {
        return positionY;
    }

    /**
     * Gets the number of coins collected by the enemy.
     *
     * @return The number of coins collected by the enemy.
     */
    public int getCoinCollected() {
        return coinCollected;
    }

    /**
     * Gets the horizontal speed of the enemy.
     *
     * @return The horizontal speed of the enemy.
     */
    @Override
    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    /**
     * Gets the vertical speed of the enemy.
     *
     * @return The vertical speed of the enemy.
     */
    public int getVerticalSpeed() {
        return verticalSpeed;
    }

    /**
     * Gets the gravity acceleration applied to the enemy.
     *
     * @return The gravity acceleration applied to the enemy.
     */
    public int getGravityAcceleration() {
        return startGravityAcceleration;
    }

    /**
     * Gets the initial gravity acceleration of the enemy.
     *
     * @return The initial gravity acceleration of the enemy.
     */
    public int getStartGravityAcceleration() {
        return gravityAcceleration;
    }

    /**
     * Gets whether of not the enemy is alive based on its health.
     *
     * @return True if the enemy is alive, false otherwise.
     */
    @Override
    public boolean getIsAlive() {
        if (this.health > 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets the radius of the enemy
     *
     * @return The radius of the enemy
     */
    @Override
    public int getRadius() {
        return radius;
    }

    /**
     * Checks if the enemy can shoot.
     *
     * @return True if the enemy can shoot, false otherwise.
     */
    public boolean getCanShoot() {
        return canShoot;
    }

    /**
     * Gets the type of the character.
     *
     * @return The type of the character -> Enemy.
     */
    @Override
    public String getType(){
        return "Enemy";
    }

    // ************************************************************************
    // Setters

    /**
     * Sets whether the enemy can shoot.
     *
     * @param canShoot True if the enemy can shoot, false otherwise.
     */
    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    /**
     * Sets the health of the enemy.
     * If the health becomes lower than 0, sets the heath at 0.
     *
     * @param health The health value to set.
     */
    public void setHealth(int health) {
        this.health += health;
        if (this.getHealth() <= 0) {
            this.health = 0;
        }
    }

    /**
     * Sets the number of coins collected by the enemy.
     * If the amount of coins collected are lower than 0, sets it to 0.
     *
     * @param coinCollected The number of coins collected.
     */
    public void setCoinCollected(int coinCollected) {
        this.coinCollected += coinCollected;

        if (coinCollected < 0) {
            // When removing coins. The speed and gravity not changed.
            if (this.getCoinCollected() <= 0) {
                this.coinCollected = 0;
            }
        } else {
            SoundPlayer.playSound("src/assets/soundEffects/collectCoin.mp3");
            ajustGravity();
            this.horizontalSpeed += 10;
            System.out.println(this.coinCollected);

            // Easter egg : if the enemy gets 21, 25, or 2125 coins (Robin teaches
            // the IFT2125 class), then a message is printed in the console.
            if (this.coinCollected == 21) {
                System.out.println("Merci");
            }
            if (this.coinCollected == 25) {
                System.out.println("Robin\nMERCI FULL ROBIN");
            }
            if (this.coinCollected == 2125) {
                System.out.println("MERCI ROBIN OMG SO GOOD!! THOU ART THE BEST");
            }
        }
    }

    /**
     * Sets the horizontal speed of the enemy.
     *
     * @param horizontalSpeed The horizontal speed to set.
     */
    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    /**
     * Sets the vertical speed of the enemy.
     *
     * @param verticalSpeed The vertical speed to set.
     */
    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    /**
     * Sets the gravity acceleration applied to the enemy.
     *
     * @param gravityAcceleration The gravity acceleration to set.
     */
    public void setGravityAcceleration(int gravityAcceleration) {
        this.gravityAcceleration = gravityAcceleration;
    }

    /**
     * Sets the y-coordinate position of the enemy.
     *
     * @param position The y-coordinate position to set.
     */
    @Override
    public void setPositionY(double position) {
        this.positionY = position;
    }

    /**
     * Sets whether the enemy is jumping.
     *
     * @param isJumping True if the enemy is jumping, false otherwise.
     */
    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    // ************************************************************************
    // Methods

    /**
     * Checks if the enemy is jumping.
     *
     * @return True if the enemy is jumping, false otherwise.
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * Makes the enemy jump.
     */
    public void jump() {
        if (canJump && isJumping) {
            this.verticalSpeed = -220;  // Jump strength
            isJumping = false;          // Reset jumping flag
        }
    }

    /**
     * Adjusts the gravity acceleration based on the number of coins collected.
     */
    public void ajustGravity() {
        this.gravityAcceleration =
                this.startGravityAcceleration + this.coinCollected * 15;
    }

    /**
     * Applies gravity to the enemy.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    public void applyGravity(double deltaTime) {
        // Apply gravity continuously
        this.verticalSpeed += gravityAcceleration * deltaTime;

        // If the enemy is on the ground
        this.positionY += this.verticalSpeed * deltaTime;
        if (this.positionY > 320) {
            this.positionY = 320;
            this.verticalSpeed = -100;
        }

        // If the enemy is on the ceiling
        if (this.positionY < 0) {
            this.positionY = 0;
            this.verticalSpeed = 100;
        }
    }

    /**
     * Makes the sound for the enemy attack.
     */
    public void attack() {
        SoundPlayer.playSound("src/assets/soundEffects/laserGunShotSound.mp3");
    }

    /**
     * Resets the stats of the enemy to default values.
     */
    public void resetEnemyStats() {
        this.health = 100;
        this.coinCollected = 0;
        this.horizontalSpeed = 120;
        this.verticalSpeed = 0;
        this.gravityAcceleration = 170;
        this.positionX = 320 - getRadius();
        this.positionY = 320;
        this.isJumping = false;
        this.canJump = true;
        this.canShoot = true;
    }
}