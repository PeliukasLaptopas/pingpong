package factory;

import java.awt.*;

public interface Paddle {
    void moveUp();
    void moveDown();
    int getYPosition();
    int getXPosition();
    void setYPosition(int newYPosition);
    void draw(Graphics2D g2);
}
