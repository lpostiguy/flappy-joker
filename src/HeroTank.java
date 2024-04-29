import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class HeroTank extends Character implements Hero {

    private double positionX;
    private double positionY;
    private int radius;
    private final ImageView imageView;

    public HeroTank() {
        super();
        this.setRadius(this.randomRadius()); // Make the radius random
        Image meleeImage = new Image("/assets/images/Superman.png");
        this.imageView = new ImageView(meleeImage);
        imageView.setFitWidth(this.getRadius() * 2);
        imageView.setFitHeight(this.getRadius() * 2);
        this.positionX = 640 + getRadius();
        this.positionY = setRandomPosition(360, getRadius());
    }

    // Getters
    public int getAttackDamage() {
        return 50;
    }

    public int getCoinDropAmount() {
        return 7;
    }

    public int getCoinStealAmount() {
        return 0;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public double getPositionY() {
        return this.positionY;
    }

    public ImageView getImageView() {return imageView;}

    public String getType() {
        return "tank";
    }
    public int getRadius() {
        return radius;
    }

    // Setters
    public void setPositionX(double position) {
        this.positionX = position;
    }

    public void setPositionY(double position) {
        this.positionY = position;
    }

    private void setRadius(int radius) {
        this.radius = radius;
    }


    // Methods

    // randomRadius method return a random between 10 and 45
    public int randomRadius() {
        int i = 10; // Lowest possible value of the radius

        // Add a random number between 0 and 35 (10 + 35 = 45)
        i += (int) (Math.floor(Math.random() * 35));
        return i;
    }

    // Set a random YPosition
    public int setRandomPosition(int sceneSize, int imageRadius) {
        Random random = new Random();

        int maxY = sceneSize - imageRadius;

        // Generate random position Y within the calculated range
        return random.nextInt(maxY - imageRadius + 1) + imageRadius;
    }

    public void updatePosition() {
        // Calculate the new Y position using sinusoidal motion
        double newX = this.getPositionX() + Math.random() * 60 - 30; // Random x within range [-30, 30]
        double newY = this.getPositionY() + Math.random() * 60 - 30; // Random y within range [-30, 30]

        // Update the Y and X positions and the ImageView
        positionY = newY;
        positionX = newX;
        imageView.setTranslateY(positionY);
        imageView.setTranslateX(positionX);
    }
}
