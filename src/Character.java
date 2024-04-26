public abstract class Character {
    // Attributes
    private boolean isAlive = true;
    private double positionX;
    private double positionY;
    private int radius;
    private int horizontalSpeed;

    // Getters
    public boolean getIsAlive() {
        return isAlive;
    }
    public double getPositionX() {return positionX;}
    public double getPositionY() {return positionY;}
    public int getRadius() {return radius;}
    public int getHorizontalSpeed() {return horizontalSpeed;}

    // Setters
    public void setIsAlive(boolean value) {
        isAlive = value;
    }
    public void setPositionX(double positionX){ this.positionX = positionX;}
    public void setPositionY(double positionY){ this.positionY = positionY;}
}