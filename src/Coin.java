import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

/**
 * Represents a coin object in the game. Each coin has a position, visibility
 * status, and an image.
 */
public class Coin {
    private double positionX;
    private double positionY;
    private boolean visible;
    // ImageView to represent the image
    // of the coin
    private final ImageView imageView;

    /**
     * Constructs a new Coin object. Initializes the image of the coin and sets
     * its initial position.
     */
    public Coin() {
        // Load the image for the coin
        Image coinImage = new Image("/assets/images/Coin.png");
        this.imageView = new ImageView(coinImage);
        imageView.setFitWidth(getCoinSize());
        imageView.setFitHeight(getCoinSize());
        setRandomPositionY(400, getCoinSize());
        // Set the position of the Coin image
        this.positionX = 640 + getCoinSize();
    }

    // Getters

    /**
     * Retrieves the ImageView representing the coin image.
     *
     * @return the ImageView of the coin
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Retrieves the radius of the coin.
     *
     * @return the radius of the coin
     */
    public int getRadius() {
        return 25;
    }

    /**
     * Retrieves the X position of the coin.
     *
     * @return the X position of the coin
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * Retrieves the Y position of the coin.
     *
     * @return the Y position of the coin
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * Retrieves the size of the coin.
     *
     * @return the size of the coin
     */
    public int getCoinSize() {
        return 25;
    }

    // Setters

    /**
     * Sets the Y position of the coin.
     *
     * @param position the new Y position of the coin
     */
    public void setPositionY(double position) {
        this.positionY = position;
    }

    /**
     * Sets the X position of the coin.
     *
     * @param position the new X position of the coin
     */
    public void setPositionX(double position) {
        this.positionX = position;
    }

    /**
     * Sets a random Y position for the coin within the specified range.
     *
     * @param sceneSize   the size of the scene (maximum Y position)
     * @param imageRadius the radius of the coin image
     */
    public void setRandomPositionY(int sceneSize, int imageRadius) {
        Random random = new Random();
        this.positionY = random.nextInt(imageRadius, sceneSize - imageRadius);
    }

}
