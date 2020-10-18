package paddles;

import factory.Paddle;
import player.SelectedPlayer;
import pong.Ball;
import utils.CanvasConstants;

import java.awt.*;

public class SimplePaddle implements Paddle {

    public static final int WIDTH = 13; //how wide the paddle is
    public static final int HEIGHT = 70; //how tall the paddle is

    private int speed;
    private int xPosition, yPosition;

    //constructor
    public SimplePaddle(SelectedPlayer player, int speed) {
        this.speed = speed;
        int xPos;
        int yPos = CanvasConstants.WINDOW_HEIGHT / 2;
        if (player == SelectedPlayer.PLAYER1) {
            xPos = SimplePaddle.WIDTH;
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
        yPosition = Math.min(yPosition + speed, CanvasConstants.WINDOW_HEIGHT - 110);
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
        g2.fillRect(xPosition, yPosition, SimplePaddle.WIDTH, SimplePaddle.HEIGHT);
    }

    @Override
    public void doCollision(Ball ball) {
        for (int colY = yPosition; colY < yPosition + SimplePaddle.HEIGHT; colY++) {
            if (ball.getXPos() == xPosition && ball.getYPos() + Ball.RADIUS == colY) {
                ball.reverseXVelocity();
                ball.setYVelocity(0);
            }
        }
    }
}
