package paddles;

import factory.Paddle;
import player.SelectedPlayer;
import pong.Ball;
import utils.CanvasConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class AngledPaddle implements Paddle {

    public static final int WIDTH = 13; //how wide the paddle is
    public static final int HEIGHT = 90; //how tall the paddle is

    private int speed;
    private int angle;
    private int xPosition, yPosition;
    private SelectedPlayer selectedPlayer;

    //constructor
    public AngledPaddle(SelectedPlayer player, int speed, int angle) {
        // Angle must be between 0 - 90
        this.angle = Math.min(90, Math.max(0, angle));
        this.selectedPlayer = player;
        this.speed = speed;
        int xPos;
        int yPos = CanvasConstants.WINDOW_HEIGHT / 2;
        if (player == SelectedPlayer.PLAYER1) {
            xPos = WIDTH;
            this.angle = -this.angle;
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
        int yPos = yPosition + speed;
        int maxYPos = (int)(CanvasConstants.WINDOW_HEIGHT - getAngledHeight() - 40);
        yPosition = Math.min(yPos, maxYPos);
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

        Rectangle2D rect = new Rectangle2D.Double(xPosition, yPosition, WIDTH, HEIGHT);
        AffineTransform aff = new AffineTransform();
        aff.rotate(Math.toRadians(angle), xPosition, yPosition);
        Shape newRect = aff.createTransformedShape(rect);
        g2.fill(newRect);
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

    private double getAngledHeight() {
        if(angle == 90) {
            return WIDTH;
        }
        return HEIGHT * Math.sin(90 - angle);
    }

    private double getAngledWidth() {
        return HEIGHT * Math.sin(angle);
    }
}