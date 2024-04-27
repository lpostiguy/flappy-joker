import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HeroMelee extends Character implements Hero {
    private double positionX;
    private double positionY;
    private final ImageView imageView;

    // Constructor
    public HeroMelee() {
        Image meleeImage = new Image("/assets/Flash.png");
        this.imageView = new ImageView(meleeImage);
        imageView.setFitWidth(getRadius() * 2);
        imageView.setFitHeight(getRadius() * 2);
        this.positionX = 70 - getRadius();
        this.positionY = 200;
    }

    // Getters
    public int getAttackDamage() {
        return 100;
    }

    public int getCoinDropAmount() {
        return 5;
    }

    public int getCoinStealAmount() {
        return 0;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public ImageView getImageView() {
        return imageView;
    }

    // Setters
    public void setPositionX(double position) {
        this.positionX = position;
    }

    public void setPositionY(double position) {
        this.positionY = position;
    }


}
