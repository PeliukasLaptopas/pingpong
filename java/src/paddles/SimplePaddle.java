package paddles;

import factory.Paddle;
import player.SelectedPlayer;
import ball.Ball;
import utils.CanvasConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SimplePaddle implements Paddle {

    public static final int WIDTH = 13; //how wide the paddle is
    public static final int HEIGHT = 70; //how tall the paddle is

    private int speed;
    private int xPosition, yPosition;
    private Shape rect;

    //constructor
    public SimplePaddle(SelectedPlayer player, int speed) {
        this.speed = speed;
        int xPos;
        int yPos = CanvasConstants.WINDOW_HEIGHT / 2;
        if (player == SelectedPlayer.PLAYER1) {
            xPos = WIDTH;
        } else {
            xPos = CanvasConstants.WINDOW_WIDTH - WIDTH * 3;
        }
        this.xPosition = xPos;
        this.yPosition = yPos;
    }

    @Override
    public void setYPosition(int newYPosition) {
        this.yPosition = newYPosition;
    }

    @Override
    public void moveUp() {
        yPosition = Math.max(yPosition - speed, 0);
    }

    @Override
    public void moveDown() {
        yPosition = Math.min(yPosition + speed, CanvasConstants.WINDOW_HEIGHT - HEIGHT - 40);
    }

    @Override
    public int getXPosition() {
        return xPosition;
    }

    @Override
    public int getYPosition() {
        return yPosition;
    }

    @Override
    public void draw(Graphics2D g2) {
        rect = new Rectangle2D.Double(xPosition, yPosition, WIDTH, HEIGHT);
        g2.fill(rect);
    }

    @Override
    public void doCollision(Ball ball) {
        if(rect != null && rect.intersects(ball.getXPos(), ball.getYPos(), ball.getSize(), ball.getSize())) {
            ball.onCollision();
        }
    }
}