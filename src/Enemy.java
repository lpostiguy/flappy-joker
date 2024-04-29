import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


public class Enemy extends Character {
    // Attributes
    private int health = 100;
    private int coinCollected = 0;
    private int horizontalSpeed = 120;
    private int verticalSpeed = 0;
    private int gravityAcceleration = 170;
    private int startGravityAcceleration = 170;
    private double positionX;
    private double positionY;
    private boolean canShoot = true;
    private final ImageView imageView;


    //Constructor
    public Enemy() {
        // Load the image for the Joker
        super();
        Image imageEnemy = new Image("/assets/images/Joker.png");
        this.imageView = new ImageView(imageEnemy);
        imageView.setFitWidth(getRadius() * 2);
        imageView.setFitHeight(getRadius() * 2);
        this.positionX = 320 - getRadius();
        this.positionY = 320;
    }


    // Getters
    public ImageView getImageView() {
        return imageView;
    }

    public int getHealth() {
        return this.health;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getCoinCollected() {
        return coinCollected;
    }

    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public int getVerticalSpeed() {
        return verticalSpeed;
    }

    public int getGravityAcceleration() {
        return startGravityAcceleration;
    }

    public int getStartGravityAcceleration() {
        return gravityAcceleration;
    }

    @Override
    public boolean getIsAlive() {
        if (this.health > 0) {
            return true;
        }
        return false;
    }

    private boolean isJumping = false;
    private boolean canJump = true; // Flag to control jump initiation


    // The enemy's radius is a constant
    @Override
    public int getRadius() {
        return 30;
    }

    public boolean getCanShoot() {
        return canShoot;
    }


    // Setters

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public void setHealth(int health) {
        this.health += health;
        if (this.getHealth() <= 0) {
            this.health = 0;
        }
    }

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

            // Easter egg : if the enemy gets 21, 25, or 2125 coins (Robin
            // teaches
            // the IFT2125 class), then a message is printed in the console.
            if (this.coinCollected == 21) {
                System.out.println("Merci");
            }
            if (this.coinCollected == 25) {
                System.out.println("Robin\nMERCI FULL ROBIN");
            }
            if (this.coinCollected == 2125) {
                System.out.println("MERCI ROBIN OMG SO GOOD!! THOU ART THE " +
                        "BEST");
            }
        }
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setGravityAcceleration(int gravityAcceleration) {
        this.gravityAcceleration = gravityAcceleration;
    }

    public void setPositionY(double position) {
        this.positionY = position;
    }

    public void setJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }


    // Methods
    public boolean isJumping() {
        return isJumping;
    }

    // Jump method revised to allow re-jumping when descending
    public void jump() {
        if (canJump && isJumping) {
            this.verticalSpeed = -220; // Jump strength
            isJumping = false;  // Reset jumping flag
        }
    }

    public void ajustGravity() {
        this.gravityAcceleration =
                this.startGravityAcceleration + this.coinCollected * 15;
    }

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

    public void attack() {
        SoundPlayer.playSound("src/assets/soundEffects/laserGunShotSound.mp3");
    }

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