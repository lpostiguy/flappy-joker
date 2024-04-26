import javafx.scene.image.ImageView;
import javafx.scene.paint.Color; // TODO : Delete
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class Enemy extends Character {
    // Attributes
    private int health = 100;
    private int coinCollected = 0;
    private int horizontalSpeed = 120;
    private int verticalSpeed = 0;
    private int gravityAcceleration = 500;
    private Color color = Color.rgb(255,0,0); // TODO : Delete
    //private final ImageView imageView; // ImageView to represent the image
    // of the coin

    // Constructor
    //public Enemy(){
    //    Image imageEnemy = new Image("/assets/.png");
    //}

    // Getters

    //TODO : Delete
    public Color getColor(){return color;}
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

    // The enemy's radius is a constant
    @Override
    public int getRadius() {return 30;}

    // Setters
    public void setHealth(int health) {
        this.health = health;
    }

    public void setCoinCollected(int coinCollected) {
        this.coinCollected = coinCollected;

        // Easter egg : if the enemy gets 21, 25, or 2125 coins (Robin teaches
        // the IFT2125 class), then a message is printed in the console.
        if(this.coinCollected == 21) {
            System.out.println("Merci");
        }
        if(this.coinCollected == 25) {
            System.out.println("Robin");
        }
        if(this.coinCollected == 2125) {
            System.out.println("MERCI ROBIN OMG SO GOOD!! THOU ART THE BEST");
        }
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed; //
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setGravityAcceleration(int gravityAcceleration) {
        this.gravityAcceleration = gravityAcceleration;
    }


    // Methods
    public void update(double deltaTime, double canvasHeight){
        verticalSpeed += gravityAcceleration * deltaTime;

        // Make sure the vertical speed is always less than 300
        if (verticalSpeed > 300){
            verticalSpeed = 300;
        }

        this.setPositionY(this.getPositionY() * deltaTime);

        // Make sure the enemy is always in the window
        if (this.getPositionX() > canvasHeight - this.getRadius()){
            this.setPositionX(canvasHeight - this.getRadius());
        }
    }
    public void ajustGravity() {
        this.gravityAcceleration = this.coinCollected * 15;
    }

    public void gravityEffect() {
        while (verticalSpeed > 0) {
            if (this.verticalSpeed - this.gravityAcceleration < 0) {
                this.verticalSpeed = 0;
            } else {
                this.verticalSpeed -= this.gravityAcceleration;
            }
        }
    }

    public void jump() {
        this.verticalSpeed = 300;
        gravityEffect();
    }

}
