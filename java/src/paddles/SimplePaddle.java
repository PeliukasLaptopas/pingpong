package paddles;

import bridge.White;
import player.SelectedPlayer;
import ball.Ball;
import utils.CanvasConstants;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import bridge.ColorBridge;

public class SimplePaddle implements Paddle {

    public static final int WIDTH = 13; //how wide the paddle is
    public static final int HEIGHT = 70; //how tall the paddle is

    private int speed;
    private int xPosition, yPosition;
    private Shape paddleShape;
    private ColorBridge color;

    //constructor
    public SimplePaddle(SelectedPlayer player, int speed, ColorBridge color) {
        this.color = color;
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

    public void setColor(ColorBridge c) {
        this.color = c;
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
        paddleShape = new Rectangle2D.Double(xPosition, yPosition, WIDTH, HEIGHT);
        g2.setColor(color.color());
        g2.fill(paddleShape);
        g2.setColor(new White().color());
    }

    @Override
    public void doCollision(Ball ball) {
        if(paddleShape != null && ball.ballShape != null && paddleShape.intersects(ball.ballShape.getBounds2D())) {
            ball.onCollision();
        }
    }
}