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
    private SelectedPlayer selectedPlayer;

    //constructor
    public SimplePaddle(SelectedPlayer player, int speed) {
        this.selectedPlayer = player;
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
        yPosition = Math.min(yPosition + speed, CanvasConstants.WINDOW_HEIGHT - HEIGHT);
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
        g2.fillRect(xPosition, yPosition, WIDTH, HEIGHT);
    }

    @Override
    public void doCollision(Ball ball) {
        int xPos = (selectedPlayer == SelectedPlayer.PLAYER1) ? xPosition : xPosition - WIDTH;
        for (int colY = yPosition; colY < yPosition + HEIGHT; colY++) {
            if (ball.getXPos() == xPos && ball.getYPos() + Ball.RADIUS == colY) {
                ball.reverseXVelocity();
                ball.setYVelocity(0);
            }
        }
    }
}