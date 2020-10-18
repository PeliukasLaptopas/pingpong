package pong;

import utils.CanvasConstants;

import java.util.Timer;
import java.util.TimerTask;

public class Ball {
    public static final int RADIUS = 10; //size of the Ball

    private int directionVector = 2;
    private int xVelocity, yVelocity;
    private BallPosition position;
    //Collision handling
    private Timer lastCollisionTimer = new Timer();
    private TimerTask onCollisionTask = new TimerTask() {
        @Override
        public void run() {
            isCollisionEnabled = true;
        }
    };
    private boolean isCollisionEnabled = true;
    private boolean destroyable = false; //is this field necessary?

    public Ball(int xPos, int yPos, double angle) {
        //in case a crazy person tries to create a Ball with values outside of the Game screen
        if ((yPos >= (CanvasConstants.WINDOW_HEIGHT - (6 * RADIUS)))
                || (yPos <= 0)
                || (xPos == (CanvasConstants.WINDOW_WIDTH - (4 * RADIUS)))
                || (xPos == 0)) {
            //then set ball x and y to be middle of the screen
            int x = CanvasConstants.WINDOW_WIDTH / 2;
            int y = CanvasConstants.WINDOW_HEIGHT / 2;
            position = new BallPosition(x, y);
        } else {
            position = new BallPosition(xPos, yPos);
        }

        //init the velocity in both directions
        xVelocity = (int) (Math.cos(angle) * (double) directionVector);
        if (xVelocity == 0) {
            xVelocity = 1;
        }
        yVelocity = (int) (Math.sin(angle) * (double) directionVector);
        destroyable = false;
        System.out.println(angle);
    }

    public void onCollision() {
        if(isCollisionEnabled) {
            isCollisionEnabled = false;
            lastCollisionTimer.schedule(onCollisionTask, 100);
            reverseXVelocity();
            setYVelocity(0);
        }
    }

    //update position
    public void updateBall() {
        position.setX(position.getX() + xVelocity);
        position.setY(position.getY() + yVelocity);
        //System.out.println(angle);
        //right bound checking
        if (position.getX() > CanvasConstants.WINDOW_WIDTH - (4 * RADIUS)) {
            reverseXVelocity();
            destroyable = true;
        }
        //left bound checking
        if (position.getX() < 0) {
            reverseXVelocity();
            destroyable = true;
        }
        //down bound checking
        if (position.getY() > CanvasConstants.WINDOW_HEIGHT - (6 * RADIUS)) {
            reverseYVelocity();

        }
        //upper bound checking
        if (position.getY() < 0) {
            reverseYVelocity();
        }
    }

    public void setBallPosition(BallPosition position) {
        this.position = position;
    }

    //getters
    public int getXPos() {
        return position.getX();
    }

    public int getYPos() {
        return position.getY();
    }

    public BallPosition getPosition() {
        return position;
    }

    //for swapping directions
    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    //setter
    public void setYVelocity(int y) {
        yVelocity = y;
    }

}
