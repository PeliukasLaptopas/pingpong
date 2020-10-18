package factory;

public interface Paddle {
    void moveUp();
    void moveDown();
    int getYPosition();
    int getXPosition();
    void setYPosition(int newYPosition);
}
