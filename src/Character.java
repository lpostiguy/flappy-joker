import javafx.scene.image.ImageView;

public abstract class Character {
    // Attributes
    private boolean isAlive = true;
    private double positionX;
    private double positionY;
    private int radius;
    private int horizontalSpeed;
    private ImageView imageView;

    // Getters
    public int getAttackDamage() {
        return 0;
    }   // TODO

    public int getCoinDropAmount() {
        return 0;
    }

    public int getCoinStealAmount() {
        return 0;
    }   // TODO

    public boolean getIsAlive() {
        return isAlive;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public int getRadius() {
        return radius;
    }

    public int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getType() {
        return "Character";
    }

    // Setters
    public void setIsAlive(boolean value) {
        isAlive = value;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}