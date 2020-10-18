package paddles;

import factory.Paddle;
import player.SelectedPlayer;
import ball.Ball;
import utils.CanvasConstants;
import utils.Range;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DashedPaddle implements Paddle {

    public static final int WIDTH = 13; //how wide the paddle is
    public static final int HEIGHT = 120; //how tall the paddle is

    private int speed;
    private int dashCount;
    private List<Range> dashes = new ArrayList<>();
    private int xPosition, yPosition;
    private SelectedPlayer selectedPlayer;

    //constructor
    public DashedPaddle(SelectedPlayer player, int speed, int dashCount) {
        this.selectedPlayer = player;
        this.dashCount = dashCount;
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
        createDashes();
    }

    private void createDashes() {
        dashes.clear();
        int parts = dashCount * 2 - 1;
        int dashHeight = HEIGHT / parts;
        int dashYPosition = yPosition;
        for (int i = 0; i < parts; i += 2) {
            dashes.add(new Range(dashYPosition, dashHeight));
            dashYPosition += dashHeight * 2;
        }
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
        createDashes();
        for (Range dash : dashes) {
            g2.fillRect(xPosition, dash.getStart(), WIDTH, dash.getEnd());
        }
    }

    @Override
    public void doCollision(Ball ball) {
        int xPos = (selectedPlayer == SelectedPlayer.PLAYER1) ? xPosition : xPosition - WIDTH;
        for (Range dash : dashes) {
            for (int colY = dash.getStart(); colY < dash.getStart() + dash.getEnd(); colY++) {
                if (ball.getXPos() <= xPos && ball.getYPos() + Ball.RADIUS == colY) {
                    ball.onCollision();
                }
            }
        }
    }
}
