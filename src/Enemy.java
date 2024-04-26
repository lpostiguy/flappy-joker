public class Enemy extends Character {
    // Attributes
    private int health = 100;
    private int coinCollected = 0;
    private int horizontalSpeed = 120;
    private int verticalSpeed = 0;
    private int gravityAcceleration = 0;

    // Getters
    public int getHealth() {
        return health;
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
        return gravityAcceleration;
    }

    // Setters
    public void setHealth(int health) {
        this.health = health;
    }

    public void setCoinCollected(int coinCollected) {
        this.coinCollected = coinCollected;
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


    // Methods
    public void ajustGravity() {
        this.gravityAcceleration = this.coinCollected * 15;
    }
    public void gravityEffect() {
        while (verticalSpeed > 0) {
            if (this.verticalSpeed - this.gravityAcceleration < 0 ) {
                this.verticalSpeed = 0;
            }
            else {
                this.verticalSpeed -= this.gravityAcceleration;
            }
        }
    }
    public void jump() {
        this.verticalSpeed = 300;
        gravityEffect();
    }

    // Constructor
    public Enemy(int health, int coinCollected, int horizontalSpeed,
                 int verticalSpeed, int gravityAcceleration) {
        this.health = health;
        this.coinCollected = coinCollected;
        this.horizontalSpeed = horizontalSpeed;
        this.verticalSpeed = verticalSpeed;
        this.gravityAcceleration = gravityAcceleration;
    }
}
