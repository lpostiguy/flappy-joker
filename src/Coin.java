import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Coin {
    private double positionX;
    private double positionY;
    private boolean visible;
    // ImageView to represent the image
    // of the coin
    private final ImageView imageView;

    // Constructor
    public Coin() {
        // Load the image for the coin
        Image coinImage = new Image("/assets/coin.png");
        imageView = new ImageView(coinImage);
        imageView.setFitWidth(getCoinSize());
        imageView.setFitHeight(getCoinSize());
        setRandomPositionY(400, getCoinSize());
        // Set the position of the Coin image
        this.positionX = 640 + getCoinSize();
    }

    // Getters

    // Getters for ImageView
    public ImageView getImageView() {
        return imageView;
    }

    // Radius is a constant for coins
    public int getRadius() {
        return 25;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getCoinSize() {
        return 25;
    }

    // Setters
    public void setPositionY(double position) {
        this.positionY = position;
    }

    public void setPositionX(double position) {
        this.positionX = position;
    }

    // Set a random YPosition
    public void setRandomPositionY(int sceneSize,
                                   int imageRadius) {
        Random random = new Random();
        this.positionY = random.nextInt(sceneSize - imageRadius);
    }

}
